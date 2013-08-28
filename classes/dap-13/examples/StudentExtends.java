class Student extends Person {

  public Student( String n, int ag, String ad, String p, double g ) { 
    // name = n; age = ag; address = ad; phone = p; 
    // need some syntax here!
    gpa = g; 
  }

  // there's a better way to do this:
  public String toString( ) { 
    return getName( ) + " " + getAge( ) + " "
         + getPhoneNumber( ) + " " + getGPA( ); 
  }

  public double getGPA( ) { return gpa; }

  private double gpa;
}
