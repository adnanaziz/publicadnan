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
  // solve takes a list of machines and a list of tasks
  // and returns a mapping of tasks to machines which satisfies
  // the constraint that no machine is assigned a collection
  // of tasks whose aggregate mips and ram exceeds those of the machine
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
