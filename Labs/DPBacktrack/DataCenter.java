import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.util.Map;

@Target(TYPE)
@Retention(RUNTIME)
//TODO(EE422C): update these to your name and eid
@interface Author {
  public String name() default  "John Snow" ;
  public String uteid() default  "js123" ;
}
@Author(name="Gary Sobers", uteid="gs365")
public class DataCenter {

/**
 * Takes a list of machines and a list of tasks and returns a mapping of tasks to machines.
 * The the sum of mips and rams of each machine's tasks can not exceed that machine's total mips and rams
 * @param machines
 * 		a list of available machines
 * @param tasks
 * 		a list of tasks 
 * @return
 * 		a Map specifying the list of tasks that is assigned to each machine
 */
  public static Map<Machine,List<Task>> solve(List<Machine> machines, List<Task> tasks) {
  //TODO(EE422C): implement this function
    return null;
  }
}

class Machine {
  int mips;
  int ram;

  public Machine( int mips, int ram ) {
    this.mips = mips;
    this.ram = ram;
  }
}

class Task {
  int mips;
  int ram;

  public Task( int mips, int ram ) {
    this.mips = mips;
    this.ram = ram;
  }
}
