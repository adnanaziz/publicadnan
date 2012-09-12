import java.util.Stack;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

class Student {
  public double GPA;
  public String name;
  public int id;
  public String school;

  public Student( double GPA, String name, int id, String school) {
    this.GPA = GPA;
    this.name = name;
    this.id = id;
    this.school = school;
  }
  @Override
  public String toString() {
    return name + "," + GPA + "," + id + "," + school;
  }

  public static Student [] createTestArray() {
    Student s0 = new Student( 3.8d, "Adnan Aziz", 123, "UT Austin");
    Student s1 = new Student( 3.6d, "Imran Aziz", 543, "MIT");
    Student s2 = new Student( 3.5d, "Aardvark Smith", 459, "Berkeley");
    Student s3 = new Student( 2.9d, "Thomas Jefferson", 453, "UT Austin");
    Student s4 = new Student( 3.3d, "Matt Biondi", 3383, "Berkeley");
    Student [] testarray = {s0, s1, s2, s3, s4};
    return testarray;
 }

  // TODO(EE422C): implement equals so that 
  // 1. nothing equals null
  // 2. a Student object never equals an
  //    object that's not of Student type
  // 3. Student objects are equal if 
  //    they agree on all their fields
  @Override
  public boolean equals(Object o) {
     //@exclude
     if ( o == null ) {
       return false;
     }
     if ( !( o instanceof Student ) ) {
       return false;
     }
     Student os = (Student) o;
     return (os.GPA == GPA) && os.name.equals(name) && (os.id == id) && (os.school.equals(school));
     //@include
  }
}

// TODO(EE422C): add fields and a constructor for this enum type
// the arguments are the sport's name, the average weight of that
// type of player, and the average GPA for that type of player

enum SportType {
  Baseball("baseball", 180,3.9d), Basketball("basketball", 175,3.7d), 
  Football("football", 240,3.6d), Soccer("soccer", 165,3.9d);
  //@exclude
  private final String sportname;
  private final int weight;
  private final double avgGPA;
  SportType(String sportname, int weight, double avgGPA) {
    this.sportname = sportname;
    this.weight = weight;
    this.avgGPA = avgGPA;
  }
  @Override 
  public String toString() { return sportname + ":" + weight + ":" + avgGPA; }
  //@include
  public String sportname() { return sportname; }
  public int weight() { return weight; }
  public double avgGPA() { return avgGPA; }
};

class StudentAthelete extends Student {
  SportType sport;

  public StudentAthelete( double GPA, String name, int id, String school, SportType sport) {
    super(GPA, name, id, school);
    this.sport = sport;
  }


  // TODO(EE422C): implement equals so that 
  // 1. nothing equals null
  // 2. a StudentAthelete object never equals an
  //    object that's not of Student type
  // 3. a StudentAthelete is equal to a Student
  //    object that's not of StudentAthelete type
  //    if they are equal as Student objects
  // 4. a StudentAthelete if equal to a StudentAthelete
  //    if they agree on their inherited fields, and their sport
  @Override
  public boolean equals(Object o) {
    //@exclude
    if ( o == null ) {
      return false;
    }
    if ( !( o instanceof Student ) ) {
      return false;
    }
    if ( !( o instanceof StudentAthelete ) ) {
      return ( (Student) o ).equals( (Student) this );
    }
    boolean chk1 = super.equals((Student) o);
    boolean chk2 = ((StudentAthelete) o).sport == this.sport;
    return chk1 && chk2;
    //@include
  }

  @Override
  public String toString() {
    return super.toString() + "," + sport;
  }
}


//TODO(EE422C): update these to your name and eid
@interface Author {
  public String name() default  "John Snow" ;
  public String uteid() default  "js123" ;
}


//TODO(EE422C): update these to your name and eid
@Author(name="John Snow", uteid="js123")
public class JavaConstructs {

   static public class GpaPredicate implements StudentPredicate {
     //TODO(EE422C): implement this class so that 
     // it has low and high fields (which are doubles), 
     // and the check function sees if the passed student 
     // objects GPA is in the range [low, high]
     //@exclude
     double low;
     double high;
     public GpaPredicate( double low, double high ) {
       this.low = low;
       this.high = high;
     }
     public boolean check( Student s ) {
       return (s.GPA>= low && s.GPA <= high );
     }
     //@include
   }
    
  //TODO(EE422C): re-implement this function as per the lab specification
  public static String nCopies( int n, String s ) {
    //@updatestart
    String result = "";
    for ( int i = 0 ; i < n; i++ ) {
      result += s;
    }
    return result;
    //@updatefinish
  }

  //TODO(EE422C): implement this iterator as per the lab specification
  // the remove method should  throw an UnsupportedOperationException
  public class StudentIteratorBySchool implements Iterator<Student> {
    //@exclude

   int currentIndex;
   String school;
   Student [] underlyingArray;

   @Override  
    public boolean hasNext() {  
      int tmpIndex = currentIndex;
     while ( tmpIndex < underlyingArray.length && !underlyingArray[tmpIndex].school.equals( school ) ) {
       tmpIndex++;
     }
     if ( tmpIndex == underlyingArray.length ) {
       return false;
     }
     return true;
    }  
     
    @Override  
    public Student next() {  
     Student currentStudent = underlyingArray[currentIndex];
     int startingIndex = currentIndex;
     while ( currentIndex < underlyingArray.length && !underlyingArray[currentIndex].school.equals( school ) ) {
       currentIndex++;
     }
     if ( currentIndex == underlyingArray.length ) {
       throw new NoSuchElementException( "starting index = " + startingIndex);
     }
     Student result = underlyingArray[currentIndex];
     currentIndex++;
     return result;
    }  

    // EE422C: you do not need to implement this function
    @Override  
    public void remove() throws UnsupportedOperationException {  
     throw new UnsupportedOperationException("remove doesn't make sense for arrays");
    }

    public StudentIteratorBySchool( Student [] underlyingArray, String school ) {
      this.currentIndex = 0;
      this.underlyingArray = underlyingArray;
      this.school = school;
    }

    //@include
  }

  interface StudentPredicate {
    boolean check( Student s );
  }

  // iterates through input array, determining which students satisfy
  // predicate, returns array of precisely those elements. not the 
  // most efficient way to create the result, but the better ways
  // involve data structures we havent talked about yet
  public static Student [] apply( Student [] input, StudentPredicate predicate ) {
    Student [] tmparray = new Student[input.length];
    int i = 0;
    for ( Student s : input ) {
      if ( predicate.check( s ) ) {
        tmparray[i++] = s;
      }
    }
    Student [] result = new Student[i];
    i = 0;
    for ( Student s : tmparray ) {
      if ( s != null ) {
        result[i++] = s;
      } else  {
        break;
      }
    }
    return result;
  }

  // sanity checks for the iterator
  public static void checkIterator() {
    Student [] testCase = Student.createTestArray();
    JavaConstructs dummy = new JavaConstructs();
    Iterator<Student> berkeleyIterator = 
        dummy.new StudentIteratorBySchool(testCase, "Berkeley");
    while ( berkeleyIterator.hasNext() ) {
      System.out.println("berkeley: " + berkeleyIterator.next().toString());
    }
    Iterator<Student> mitIterator = 
        dummy.new StudentIteratorBySchool(testCase, "MIT");
    while ( mitIterator.hasNext() ) {
      System.out.println("MIT: " + mitIterator.next().toString());
    }
    Iterator<Student> stanfordIterator = 
        dummy.new StudentIteratorBySchool(testCase, "Stanford");
    while ( stanfordIterator.hasNext() ) {
      System.out.println("Stanford: " + mitIterator.next().toString());
    }
  }

  // sanity checks for predicate
  public static Student []  checkPredicate1(Student [] testCase, double L, double H ) {
    final double lowRange = L;
    final double highRange = H;
    Student [] filteredTestCase =  apply( testCase, new StudentPredicate() {
      // TODO(EE422C): implement this anonymous inner class
      // so that the call to apply returns students whose
      // GPAs are in the specified range
      // @exclude
      public boolean check( Student s ) {
        return (s.GPA >= lowRange && s.GPA <= highRange );
      }
      // @include
    });
    return filteredTestCase;
  }

  // sanity checks for predicate
  public static Student []  checkPredicate2(Student [] testCase, double L, double H ) {
    Student [] filteredTestCase = apply( testCase, new  GpaPredicate(L, H) );
    return filteredTestCase;
  }

  public static void checkString(int N) {
    for ( int i = 1 ; i < N; i++ ) {
       long startTime = System.nanoTime();
       String tmp = nCopies( (int) Math.pow(2,i), "test" );
       long finishTime = System.nanoTime();
       System.out.println("Iteration " + i + ": string of length " + tmp.length() + 
         " took " + ((double)(finishTime - startTime))/1000000000.0d + " seconds");
    }
  }

  public static void checkEquals() {
    Student s0 = new Student( 3.8d, "Adnan Aziz", 123, "UT Austin");
    Student s1 = new Student( 3.9d, "Adnan Aziz", 123, "UT Austin");
    Student s2 = new Student( 3.8d, "Adnan Aziz", 123, "UT Austin");
    StudentAthelete sa0 = new StudentAthelete( 3.9d, "Adnan Aziz", 123, "UT Austin", 
        SportType.Baseball);
    StudentAthelete sa1 = new StudentAthelete( 3.9d, "Adnan Aziz", 123, "UT Austin",
        SportType.Football);
    System.out.println(s0 + " == " + s1 + " : " + s0.equals(s1));
    System.out.println(s0 + " == " + s2 + " : " + s0.equals(s1));
    System.out.println(s1 + " == " + sa0 + " : " + s1.equals(sa0));
    System.out.println(sa0 + " == " + s1 + " : " + sa0.equals(s1));
    System.out.println(s1 + " == " + sa1 + " : " + s1.equals(sa1));
    System.out.println(sa1 + " == " + s1 + " : " + sa1.equals(s1));
    System.out.println(sa1 + " == " + sa0 + " : " + sa1.equals(sa0));
  }

  public static void main( String [] args ) {
    checkIterator();
    checkPredicate1(Student.createTestArray(), 3.5, 3.7);
    checkPredicate2(Student.createTestArray(), 3.5, 3.7);
    checkEquals();
    checkString(12);
  }
}

