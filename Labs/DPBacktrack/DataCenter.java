import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.util.ArrayList;

@Target(TYPE)
@Retention(RUNTIME)
//TODO(EE422C): update these to your name and eid
@interface Author {
  public String name() default  "John Snow" ;
  public String uteid() default  "js123" ;
}
@Author(name="Gary Sobers", uteid="gs365")
public class DataCenter {
  // solve takes machine and a list of tasks
  // and returns a list of tasks that satisifies
  // the constraint that the aggregate mips and ram 
  // does not exceed those of the machine
  public static List<Task> solve(Machine machine, List<Task> tasks) {
  //TODO(EE422C): implement this function
    List<Task> result = new ArrayList<Task>();
    result.add( tasks.get(0) );
    return result;
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
  int price;

  public Task( int mips, int ram, int price ) {
    this.mips = mips;
    this.ram = ram;
    this.price = price;
  }

  @Override
  public boolean equals( Object o ) {
    if ( (o == null) || !(o instanceof Task) ) {
      return false;
    }
    Task ot = (Task) o;
    return ( (this.mips == ot.mips) 
                && (this.ram == ot.ram) 
                && (this.price == ot.price) );
  }
  @Override
  public int hashCode() {
    return 137*(mips*137+ price) + ram;
  }
}
