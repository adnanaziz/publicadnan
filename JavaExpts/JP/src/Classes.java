interface CustomCompare {
  int compare( Student s0, Student s1 );
}

public class Classes {

 public static Student [] createTestArray() {
    Student s0 = new Student( 3.8d, "Adnan Aziz", 123, "UT Austin");
    Student s1 = new Student( 3.8d, "Imran Aziz", 543, "UT Arlington");
    Student s2 = new Student( 3.9d, "Aardvark Smith", 459, "Berkeley");
    Student s3 = new Student( 2.9d, "Thomas Jefferson", 453, "MIT");
    Student [] testarray = {s0, s1, s2, s3};
    return testarray;
 }

 // static member class
 static public class CompareName implements CustomCompare {
   public int compare( Student s0, Student s1 ) {
     return s0.name.compareTo(s1.name);
   }
 }

 static public CompareName compareName = new CompareName();

 // nonstatic member class
 public class CompareSchool implements CustomCompare {
   public int compare( Student s0, Student s1 ) {
     return s0.school.compareTo(s1.school);
   }
 }

 // need this nonstatic function,
 // since nonstatic class cannot be accessed
 // from a static method
 public Student [] compareSchool(Student [] testarray) {
    Student [] sortedSchool = Classes.mysort( testarray, new CompareSchool() );
    return sortedSchool;
 }

  public static void main(String [] args) {

    Student [] testarray = createTestArray();

    // local class
    class CompareGpa implements CustomCompare {
      public int compare( Student s0, Student s1 ) {
        if ( s0.GPA < s1.GPA ) {
          return -1;
        } else if ( s0.GPA > s1.GPA ) {
          return 1;
        } else {
          return 0;
        }
      }
    }

    // use generics for more
    // general case: http://stackoverflow.com/questions/3108182/
    // using-parameter-that-implements-multiple-interfaces-pre-generics
    CompareGpa cmpGpa = new CompareGpa();
    Student [] sortedGpa = mysort( testarray, cmpGpa );
    System.out.println("by gpa");
    for (Student s : sortedGpa ) {
      System.out.println(s);
    }  

    Student [] sortedId = mysort( testarray,
      // anonymous local class
      new CustomCompare() {
        public int compare( Student s0, Student s1 ) {
          if ( s0.id < s1.id ) {
            return -1;
          } else if ( s0.id > s1.id ) {
            return 1;
          } else {
            return 0;
          }
        }
      }
    );
    System.out.println("by id");
    for (Student s : sortedId ) {
      System.out.println(s);
    }

    System.out.println("by name");
    Student [] sortedName = mysort( testarray, compareName );
    for (Student s : sortedName ) {
      System.out.println(s);
    }

    Student [] sortedSchool = new Classes().compareSchool(testarray);
    System.out.println("by school");
    for (Student s : sortedSchool ) {
      System.out.println(s);
    }
  }

  public static Student [] mysort( Student [] studentArray, CustomCompare cmp ) {
    Student [] result = new Student[studentArray.length];
    for ( int i = 0 ; i < result.length; i++ ) {
      result[i] = studentArray[i];
    }
    for ( int i = 0 ; i < result.length; i++ ) {
      int minindex = i;
      for ( int j = i ; j < result.length; j++ ) {
        if ( cmp.compare( result[j], result[minindex] ) < 0 ) {
           minindex = j;
        }
      }
      Student tmp = result[minindex];
      result[minindex] = result[i];
      result[i] = tmp;
    }
    return result;
  }

}

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
}

