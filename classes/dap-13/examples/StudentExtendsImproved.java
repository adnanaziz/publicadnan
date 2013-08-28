class StudentExtendsImproved extends Person {

  public Student( String n, int ag, String ad, String p, double g ) { 
    super(n, ag, ad, p);
    gpa = g; 
  }

  public String toString( ) { 
    return super.toString() + getGPA( ); 
  }

}
