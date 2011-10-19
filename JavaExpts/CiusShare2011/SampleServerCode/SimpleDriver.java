import java.util.*;

public class SimpleDriver {
  public static void main( String [] args ) {
    String numIter = "10";
    if ( args.length > 0 ) {
       numIter = args[0];
    }
    Logger logger = new Logger("SimpleDriver 16667", Logger.Level.Alert );
    Cpu cpu0 = new Cpu( "192.168.1.101", 16667 );
    // Cpu cpu1 = new Cpu( "192.168.1.101", 16668 );
    List<Cpu> cpuList = new ArrayList<Cpu>();
    cpuList.add( cpu0 );
    // cpuList.add( cpu1 );
    List<Integer> taskList = new ArrayList<Integer>();
    int i;
    for( i = 0 ; i < Integer.parseInt( numIter ); i++ ) {
      taskList.add( i );
    }
    taskList.add( 333 );
    taskList.add( 444 );
    logger.note("Calling initController");
    Controller.getInstance().initController( cpuList, taskList );
    logger.note("Calling runController");
    Controller.getInstance().runController();
    while ( !( Tasks.getInstance().tasksFinished( ) ) || ( Controller.getInstance().readNumOutStanding(
    ) > 0) ) {
      // logger.note("tasksFinished: " + Tasks.getInstance().tasksFinished() );
      // logger.note("readNumOutStanding: " + Controller.getInstance().readNumOutStanding() );
    }
    logger.note("---all done");
    logger.note("tasksFinished: " + Tasks.getInstance().tasksFinished() );
    logger.note("readNumOutStanding: " + Controller.getInstance().readNumOutStanding() );
    System.out.println("---all done");
  }
}
