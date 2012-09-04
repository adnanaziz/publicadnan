interface CustomCompare {
  int compare( Student s0, Student s1 );
}

class CompareGpa implements CustomCompare {
  int compare( Student s0, Student s1 ) {
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

  public static void main(String [] args) {
    String s0 = new String( 3.8, "Adnan Aziz", 123, "UT Austin");
    String s1 = new String( 3.8, "Imran Aziz", 543, "UT Arlington");
    String s2 = new String( 3.9, "Aardvark Smith", 459, "Berkeley");
    String s2 = new String( 2.9, "Thomas Jefferson", 453, "MIT");
    String [] testarray = {s0, s1, s2, s3};
    // use generics for more
    // general case: http://stackoverflow.com/questions/3108182/
    // using-parameter-that-implements-multiple-interfaces-pre-generics
    CompareGpa cmpGpa = new CompareGpa();
    Student [] sorted = mysort( Student [] studentArray, CustomCompare cmpGpa );
    for (Student s : sorted ) {
      System.out.println(s);
    }
  }
}

class Student {
  public double GPA;
  public String name;
  public int id;
  public String school;
  public Student( double GPA, Student name, int id, Student school) {
    this.GPA = GPA;
    this.name = name;
    this.id = id;
    this.school = school;
  }
}




