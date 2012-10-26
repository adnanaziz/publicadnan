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
    
    Integer [][][] cache = new Integer[1+machine.getMips()][][];
    Boolean [][][] keepcache = new Boolean[1+machine.getMips()][][];
    for ( int i = 0 ; i < 1+machine.getMips(); i++ ) {
      cache[i] = new Integer[1+machine.getRam()][];
      keepcache[i] = new Boolean[1+machine.getRam()][];
    }
    for ( int i = 0 ; i < 1+machine.getMips(); i++ ) {
      for ( int j = 0 ; j < 1+machine.getRam(); j++ ) {
        cache[i][j] = new Integer[tasks.size()];
        keepcache[i][j] = new Boolean[tasks.size()];
        // not needed, since intialized to null
        // for ( int k = 0 ; k < tasks.size(); k++ ) {
        //   cache[i][j][k] = null;
        // }
      }
    }
    

    int mostValue = solve( machine.getMips(), machine.getRam(), cache, keepcache, tasks, tasks.size() - 1 );

    System.out.println("mostValue = " + mostValue );
    List<Task> result = computeKeeps( machine.getMips(), machine.getRam(), keepcache, tasks, tasks.size() - 1 );

    return result;
  }

  static List<Task> computeKeeps( int mipsCapacity, int ramCapacity, Boolean[][][] keepcache, List<Task> tasks, int n) {
    List<Task> result = new ArrayList<Task>();
    int tmpMips = mipsCapacity;
    int tmpRam = ramCapacity;
    for ( int i = n-1 ; i >=0; i--) {
      Boolean [][] foo = keepcache[tmpMips];
      Boolean [] bar = keepcache[tmpMips][tmpRam];
      if ( (null != keepcache[tmpMips][tmpRam][i] && ( keepcache[tmpMips][tmpRam][i]) ) )  {
        result.add( tasks.get(i) );
        tmpMips -= tasks.get(i).getMips();
        tmpRam -= tasks.get(i).getRam();
      }
    }
    return result;
  }

  static int solve( int mipsCapacity, int ramCapacity,
                    Integer [][][] cache, Boolean [][][] keepcache, List<Task> tasks, int n) {
    System.out.println("solve call with : " + mipsCapacity + "," + ramCapacity + "," + n);
    if ( n == -1 ) {
      return 0;
    }
    Task task = tasks.get(n);
    if ( task.getMips() > mipsCapacity || task.getRam() > ramCapacity ) {
      return 0;
    }
    if (cache[mipsCapacity][ramCapacity][n] != null) {
      return cache[mipsCapacity][ramCapacity][n];
    }


    int bestWithout = solve( mipsCapacity, ramCapacity, cache, keepcache, tasks, n-1);
    int bestWith = task.getPrice() + solve( mipsCapacity -task.getMips(), ramCapacity - task.getRam(), cache, keepcache, tasks, n-1);
    int best;
    if ( bestWith > bestWithout) {
       best = bestWith;
       keepcache[mipsCapacity][ramCapacity][n] = true;
    } else {
      best = bestWithout;
      keepcache[mipsCapacity][ramCapacity][n] = false;
    }
    System.out.println("Nonbase return: " + best );
    cache[mipsCapacity][ramCapacity][n] = best;
    return best;
  }
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
