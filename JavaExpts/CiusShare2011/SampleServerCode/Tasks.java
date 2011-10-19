// import java.util.concurrent.atomic;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.*;


class Tasks<T extends Serializable, R extends Serializable> {

  // from Bloch's Effective Java 2nd edition
  private static final AtomicLong nextSerialNum = new AtomicLong();
  public static long generateSerialNumber() {
    return nextSerialNum.getAndIncrement();
  }

  private ArrayList<T> tasks = new ArrayList<T>();
  public ArrayList<T> getTasks() { return tasks; }

  // TODO: not actually used yet
  static final int timeout = 10; // seconds
  // TODO: make timeout dependent on Cpu
  static int getTimeOut( Cpu cpu ) { return timeout; }

  private static final Tasks instance = new Tasks();
  // ensure singleton
  // from Bloch's Effective Java 2nd edition
  private Tasks() {}

  public static <T,R> Tasks getInstance() { return instance; }

  private static Object taskMutex = new Object();

  public void add( T task ) { 
    synchronized ( taskMutex ) {
      tasks.add( task ); 
    }
  }

  // TODO: have tasks selected a function of client
  // note: failure to use mutex for whole block led to
  // null race between if and remove
  public T getTask( Cpu c ) { 
    synchronized ( taskMutex ) {
      ArrayList<T> taskList = this.tasks;
      if (this.tasks.size() == 0) {
        return null;
      }
      T aTask = taskList.remove( 0 );
      Controller.getLogger().note("Got task: " + aTask.toString() );
      return aTask;
    }
  }

  // TODO: result may be only partial solution to dispatched tasks
  // so need to potentially add remaining tasks
  public void updateTasks( R results ) { } 

  public boolean tasksFinished() { 
    boolean result = (tasks.size() == 0); 
    return result;
  }

  public boolean tasksNotFinished() {
     return !tasksFinished();
  }
}
