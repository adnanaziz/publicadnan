import java.util.Stack;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target(TYPE)
@Retention(RUNTIME)

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
    Student s1 = new Student( 3.8d, "Imran Aziz", 543, "UT Arlington");
    Student s2 = new Student( 3.9d, "Aardvark Smith", 459, "Berkeley");
    Student s3 = new Student( 2.9d, "Thomas Jefferson", 453, "UT Austin");
    Student s4 = new Student( 3.3d, "Matt Biondi", 3383, "Berkeley");
    Student [] testarray = {s0, s1, s2, s3, s4};
    return testarray;
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
    
  //TODO(EE422C): re-implement this function as per the lab specification
  public static String nCopies( int n, String s ) {
    String result = "";
    for ( int i = 0 ; i < n; i++ ) {
      result += s;
    }
    return result;
  }

  //TODO(EE422C): implement this iterator as per the lab specification
  public class StudentIteratorBySchool implements Iterator<Student> {

   int currentIndex;
   String school;
   Student [] underlyingArray;

   @Override  
    public boolean hasNext() {  
     while ( currentIndex < underlyingArray.length && !underlyingArray[currentIndex].equals( school ) ) {
       currentIndex++;
     }
     if ( currentIndex == underlyingArray.length ) {
       return false;
     }
     return true;
    }  
     
    @Override  
    public Student next() {  
     Student currentStudent = underlyingArray[currentIndex];
     int startingIndex = currentIndex;
     while ( currentIndex < underlyingArray.length && !underlyingArray[currentIndex].equals( school ) ) {
       currentIndex++;
     }
     if ( currentIndex == underlyingArray.length ) {
       throw new NoSuchElementException( "starting index = " + startingIndex);
     }
     return underlyingArray[currentIndex];
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

    public StudentIteratorBySchool getInstance(
      String [] underlyingArray, String schoolName ) {
      return new StudentIteratorBySchool( underlyingArray, schoolName ) ;
    }
  }



  public static void main(String [] args) {
    Student [] testCase = Student.createTestArray();
    Iterator<Student> berkeleyIterator = 
        new StudentIteratorBySchool().getInstance(testCase, "Berkeley");
    while ( berkeleyIterator.hasNext() ) {
      System.out.println("berkeley: " + berkeleyIterator.next().toString());
    }
  }
}

