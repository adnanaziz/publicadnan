// import java.util.concurrent.atomic;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.*;


public class Controller<T extends Serializable, R extends Serializable> {
  private static final Controller instance = new Controller();

  // ensure singleton, a la Bloch
  private Controller() {}
  public static <T,R> Controller getInstance() { 
    return instance; 
  }

  private static List<Cpu> cpus = new ArrayList<Cpu>();
  public Tasks tasks = Tasks.getInstance();

  // share with CpuThread
  static Object cpuMutex = new Object();

  private static List<Cpu> availableCpus = new ArrayList<Cpu>();
  public static List<Cpu> getAvailableCpus() { return availableCpus; }

  private static Logger logger = new Logger("Controller", Logger.Level.Note);

  public static Logger getLogger() { return logger; }

  private static volatile int numTasksOutStanding = 0;
  static Object numTasksMutex = new Object();

  public static int readNumOutStanding() { 
    return numTasksOutStanding; 
  }

  public static void incrementNumOutStanding() { 
    synchronized ( numTasksMutex ) {
      numTasksOutStanding++; 
    }
  }

  public static void decrementNumOutStanding() { 
    synchronized ( numTasksMutex ) {
      numTasksOutStanding--; 
    }
  }

  // TODO: figure out if there's a race here
  public static boolean OutstandingTasksRemain() { 
    if ( readNumOutStanding() > 0 ) {
      return true;
    }
    return false;
  }

  public static Cpu getCpu() { 
    synchronized (cpuMutex) {
      int size = getAvailableCpus().size();
      if ( 0 == size ) {
        return null;
      }
      Cpu result = getAvailableCpus().remove( 0 );
      if (result == null) {
        logger.alert("null cpu, size was " + size );
        throw new IllegalArgumentException();
      }
      return result;
    }
  }  

  public void initController( List<Cpu> CpuList, List<T> tList ) {
    for ( Cpu cpu : CpuList ) {
      cpus.add( cpu );
      getAvailableCpus().add( cpu );
    }
    for ( T task : tList ) {
      tasks.add( task );
      assert false;
    }
    logger.note("initController: there are " + cpus.size() + " cpus and " 
		+ tasks.getInstance().getTasks().size() + " tasks" );
  }

  public synchronized boolean cpusAvailable() { 
    return ( this.getAvailableCpus().size() > 0 ); 
  }

  public void runController() {
    logger.note("running controller");
    while ( Tasks.getInstance().tasksNotFinished() || Controller.getInstance().OutstandingTasksRemain() ) {
      logger.warn("found task");
      while( !this.cpusAvailable() ) {
	 // TODO: don't busy wait
      }
      logger.note("found cpu");
      Cpu cpu = Controller.getCpu();
      logger.note("got cpu");
      logger.note("getting task");
      Tasks tasks = Tasks.getInstance();
      logger.note("got task");
      Controller.getInstance().incrementNumOutStanding();
      if ( tasks.getInstance().getTasks().size() > 0 ) {
        T job = (T) tasks.getTask( cpu );
        CpuThread t = new CpuThread( cpu, job );
        logger.warn("starting cpu thread with job " + job);
        t.start( );
        logger.note("cputhread done");
      }
    }
    logger.warn("done with runController");
    logger.warn("tasksFinished() = " + Tasks.getInstance().tasksFinished());
    logger.warn("readNumOutStanding() = " + Controller.getInstance().readNumOutStanding() );
  }
}

class CpuThread<T extends Serializable, R extends Serializable> extends Thread {

  Cpu cpu;
  T task;

  CpuThread( Cpu cpu, T task ) { 
    this.cpu = cpu; 
    this.task = task; 
    if ( cpu == null ) {
      throw new IllegalArgumentException();
    }
  }   

  public void run( ) {
    Controller controller = Controller.getInstance();
    Logger logger = Controller.getInstance().getLogger();
    logger.warn("cpu.execute with " + task );
    R result = null;
    try {
      if ( cpu == null ) {
        logger.alert("cpu is null");
      }
      result = (R) cpu.execute( task );
      logger.alert("cpu thread return value from run is " + result.toString() );
    } catch (Exception e) { e.printStackTrace(); }
    Tasks.getInstance().updateTasks( (R) result );
    synchronized (Controller.cpuMutex) {
      controller.getAvailableCpus().add( cpu );
    }
    controller.decrementNumOutStanding();
    logger.warn("cpu thread run method finished, " + 
    		" numOutStanding = " + 
		Controller.getInstance().readNumOutStanding() + 
		"number of available cpus = " + 
		Controller.getInstance().getAvailableCpus().size() );
  }
}
