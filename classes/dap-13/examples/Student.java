class Student {

  public Student( String n, int ag, String ad, String p, double g ) { 
    name = n; age = ag; address = ad; phone = p; gpa = g; 
  }

  public String toString( ) { 
    return getName( ) + " " + getAge( ) + " "
         + getPhoneNumber( ) + " " + getGPA( ); 
  }

  public String getName() { return name; }

  public int getAge( ) { return age; }
 
  public String getAddress( ) { return address; }

  public String getPhoneNumber( ) { return phone; }

  public void setAddress( String newAddress ) { address = newAddress; }

  public void setPhoneNumber( String newPhone ) { phone = newPhone; }

  public double getGPA( ) { return gpa; }

  private String name;
  private int age;
  private String address;
  private String phone;
  private double gpa;
}
