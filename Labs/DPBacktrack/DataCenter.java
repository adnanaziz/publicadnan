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
	//TODOBEGIN(EE422C)
    return null;
	//TODOEND(EE422C)
  }
	//TODOBEGIN(EE422C)

	//TODOEND(EE422C)
}

class Machine {
  private int mips;
  private int ram;

  public Machine( int mips, int ram ) {
    this.mips = mips;
    this.ram = ram;
  }

  public int getRam() { return ram; }
  public int getMips() { return mips; }

  
}

class Task {
  private final int mips;
  private final int ram;
  private final int price;

  public int getMips() { return mips; }
  public int getRam() { return ram; }
  public int getPrice() { return price; }

  static class TaskBuilder {

    private int mips;
    private int ram;
    private int price;

    public TaskBuilder() {
      return;
    }

    public TaskBuilder setMips( int mips ) {
      this.mips = mips;
      return this;
    }

    public TaskBuilder setRam( int ram ) {
      this.ram = ram;
      return this;
    }

    public TaskBuilder setPrice( int price ) {
      this.price = price;
      return this;
    }

    public Task build() {
      return new Task( mips, ram, price );
    }

  }


  private Task( int mips, int ram, int price ) {
    this.mips = mips;
    this.ram = ram;
    this.price = price;
  }

  @Override
  public String toString() {
    return "" + mips + "," + ram + "," + price;
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
