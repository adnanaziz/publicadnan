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

 @Override
 public boolean equals(Object o) {
    if ( o == null ) {
      return false;
    }
    if ( !( o instanceof Student ) ) {
      return false;
    }
    Student os = (Student) o;
    return (os.GPA == GPA) && os.name.equals(name) && (os.id == id) && (os.school.equals(school));
 }

}

class StudentAthelete extends Student {
  public enum SportType {Baseball, Basketball, Football, Soccer };
  SportType sport;

  public StudentAthelete( double GPA, String name, int id, String school, SportType sport) {
    super(GPA, name, id, school);
    this.sport = sport;
  }

  @Override
  public boolean equals(Object o) {
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
     double low;
     double high;
     public GpaPredicate( double low, double high ) {
       this.low = low;
       this.high = high;
     }
     public boolean check( Student s ) {
       return (s.GPA>= low && s.GPA <= high );
     }
   }
    
  //TODO(EE422C): re-implement this function as per the lab specification
  public static String nCopies( int n, String s ) {
    // String result = "";
    StringBuilder sb = new StringBuilder();
    for ( int i = 0 ; i < n; i++ ) {
      // result += s;
      sb.append(s);
    }
    // return result;
    return sb.toString();
  }

  //TODO(EE422C): implement this iterator as per the lab specification
  public class StudentIteratorBySchool implements Iterator<Student> {

   int currentIndex;
   String school;
   Student [] underlyingArray;

   @Override  
    public boolean hasNext() {  
      int tmpIndex = currentIndex;
     while ( tmpIndex < underlyingArray.length && !underlyingArray[tmpIndex].school.equals( school ) ) {
       // System.out.println(school + ": currentIndex = " + tmpIndex );
       // System.out.println(underlyingArray[currentIndex]);
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
  }

  interface StudentPredicate {
    boolean check( Student s );
  }

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

  public static void checkPredicate() {
    Student [] testCase = Student.createTestArray();
    final double lowRange = 3.5;
    final double highRange = 3.5;
    Student [] filteredTestCase =  apply( testCase, new StudentPredicate() {
      public boolean check( Student s ) {
        return (s.GPA >= lowRange && s.GPA <= highRange );
      }
    });
    for ( Student s : filteredTestCase ) {
      System.out.println( "anonymous class 3.5-3.5, GpaPredicate:" + s );
    }
    filteredTestCase = apply( testCase, new  GpaPredicate(3.5, 3.6) );
    for ( Student s : filteredTestCase ) {
      System.out.println( "GpaPredicate 3.5-3.6:" + s );
    }

  }

  public static void checkString() {
    for ( int i = 1 ; i < 16; i++ ) {
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
        StudentAthelete.SportType.Baseball);
    StudentAthelete sa1 = new StudentAthelete( 3.9d, "Adnan Aziz", 123, "UT Austin",
        StudentAthelete.SportType.Football);
    // System.out.println(s0 + " == " + s1 + " : " + s0.equals(s1));
    // System.out.println(s0 + " == " + s2 + " : " + s0.equals(s1));
    // System.out.println(s1 + " == " + sa0 + " : " + s1.equals(sa0));
    // System.out.println(sa0 + " == " + s1 + " : " + sa0.equals(s1));
    // System.out.println(s1 + " == " + sa1 + " : " + s1.equals(sa1));
    // System.out.println(sa1 + " == " + s1 + " : " + sa1.equals(s1));
    System.out.println(sa1 + " == " + sa0 + " : " + sa1.equals(sa0));
  }

  public static void main( String [] args ) {
    checkPredicate();
    // checkEquals();
  }
}

