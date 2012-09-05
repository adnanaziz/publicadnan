interface CustomCompare {
  int compare( Student s0, Student s1 );
}

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

public class Classes {

 static public class CompareName implements CustomCompare {
   public int compare( Student s0, Student s1 ) {
     System.out.println(s0 + " " + s1 + ":" + s0.name.compareTo(s1.name));
     return s0.name.compareTo(s1.name);
   }
 }

 public class CompareSchool implements CustomCompare {
   public int compare( Student s0, Student s1 ) {
     System.out.println(s0 + " " + s1 + ":" + s0.name.compareTo(s1.name));
     return s0.school.compareTo(s1.school);
   }
 }

 public void dummy() {
    Student s0 = new Student( 3.8d, "Adnan Aziz", 123, "UT Austin");
    Student s1 = new Student( 3.8d, "Imran Aziz", 543, "UT Arlington");
    Student s2 = new Student( 3.9d, "Aardvark Smith", 459, "Berkeley");
    Student s3 = new Student( 2.9d, "Thomas Jefferson", 453, "MIT");
    Student [] testarray = {s0, s1, s2, s3};
    Student [] sortedSchool = mysort( testarray, new CompareSchool() );
    System.out.println("by schools");
    for (Student s : sortedSchool ) {
      System.out.println(s);
    }
 }

  public static void main(String [] args) {
    new Classes().dummy();
    Student s0 = new Student( 3.8d, "Adnan Aziz", 123, "UT Austin");
    Student s1 = new Student( 3.8d, "Imran Aziz", 543, "UT Arlington");
    Student s2 = new Student( 3.9d, "Aardvark Smith", 459, "Berkeley");
    Student s3 = new Student( 2.9d, "Thomas Jefferson", 453, "MIT");
    Student [] testarray = {s0, s1, s2, s3};
    // use generics for more
    // general case: http://stackoverflow.com/questions/3108182/
    // using-parameter-that-implements-multiple-interfaces-pre-generics
    CompareGpa cmpGpa = new CompareGpa();
    Student [] sortedGpa = mysort( testarray, cmpGpa );
    for (Student s : sortedGpa ) {
      // System.out.println(s);
    }
    Student [] sortedId = mysort( testarray,
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
    for (Student s : sortedId ) {
      // System.out.println(s);
    }
    Student [] sortedName = mysort( testarray, new CompareName() );
    for (Student s : sortedName ) {
      System.out.println(s);
    }
  }

  public static Student [] mysort( Student [] studentArray, CustomCompare cmpGpa ) {
    Student [] result = new Student[studentArray.length];
    for ( int i = 0 ; i < result.length; i++ ) {
      result[i] = studentArray[i];
    }
    for ( int i = 0 ; i < result.length; i++ ) {
      for ( int j = i ; j < result.length; j++ ) {
        if ( cmpGpa.compare( result[j], result[i] ) == -1 ) {
           System.out.println("swapping: " + i + "->" + j + result[j] + ":" + result[i] );
           Student tmp = result[j];
           result[j] = result[i];
           result[i] = tmp;
        }
      }
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

