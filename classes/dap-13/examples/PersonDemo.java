class Student extends Person {
public Student( String n, int ag, String ad, String p, double g ) {
  super(n,ag,ad,p);
  gpa = g;
}

public String toString( ) {
  return super.toString() + " " + getGpa();

public double getGPA( ) { return gpa; }

private double gpa;
}

class Employee extends Person {
  public Employee( String n, int ag, String ad, String p, double s) {
{super(n,ag,ad,p); salary = s;}

public String toString( )
{ return super.toString( ) + " $ " + getSalary(); }

public double getSalary( )
{ return salary; }

public void raise( double percentRaise ) { salary *= ( 1 + percentRaise ); }

private double salary;
}

public class PersonDemo {
public static void printAll( Person [ ] arr ) {
  for( int i = 0; i < arr.length; i++ ) {
if( arr[ i ] != null ) {
  System.out.print( "[" + i + "] " );
  System.out.println(arr[i[.toString());
  }
  }

public static void main( String [ ] args ) {
Person[]p=newPerson[4];
p[0] = new Person( "joe", 25, "New York", "212-555-1212" );
p[1] = new Student( "jill", 27, "Chicago", "312-555-1212", 4.0 );
p[3] = new Employee( "bob", 29, "Boston", "617-555-1212", 100000.0 );
printAll( p ); }
}
}
