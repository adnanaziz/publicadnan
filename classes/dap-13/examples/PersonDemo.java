class Person {
  public Person( String n, int ag, String ad, String p ) {
    name = n; age = ag; address = ad; phone = p;
  }
    
  public String toString() {
    return getName( ) + " " + getAge( ) + " " + getPhoneNumber( );
  }
    
  public final String getName() {
    return name;
  }
    
  public final int getAge( ) {
    return age;
  }
    
  public final String getAddress( ) {
    return address;
  }
    
  public final String getPhoneNumber( ) {
    return phone;
  }
    
  public final void setAddress( String newAddress ) {
    address = newAddress;
  }
    
  public final void setPhoneNumber( String newPhone ) {
    phone = newPhone;
  }
    
  private String name;
  private int age;
  private String address;
  private String phone;
}
 
class Student extends Person {
    public Student( String n, int ag, String ad, String p, double g ) {
        super( n, ag, ad, p );
        gpa = g;
    }
    
    public String toString( ) {
        return super.toString( ) + " " + getGPA();
    }
    
    public double getGPA( ) {
        return gpa;
    }
    
    private double gpa;
}
 
class Employee extends Person
{
    public Employee( String n, int ag, String ad, String p, double s ) {
        super( n, ag, ad, p );
        salary = s;
    }
    
    public String toString( ) {
        return super.toString( ) + " $" + getSalary( );
    }
    
    public double getSalary( ) {
        return salary;
    }
     
    public void raise( double percentRaise ) {
        salary *= ( 1 + percentRaise );
    }
    
    private double salary;
}

interface PersonCompare {
  // Note: cannot specify static methods in an interface
  int compare(Person p, Person q);
}

 
class PersonDemo
{
  
    static class AgeCompare implements PersonCompare {
      // compare p and q by age, return negative if q is less than p,
      // positive if q is greater than p, and 0 otherwise
      public int compare(Person p, Person q) {
        int result =  q.getAge() - p.getAge();
        return result;
      }
    }

    static class NameCompare implements PersonCompare {
      public int compare(Person p, Person q) {
        return p.getName().compareTo(q.getName());
      }
    }

    static class BetterAgeCompare implements PersonCompare {
      public int compare(Person p, Person q) {
        int result =  q.getAge() - p.getAge();
        return result;
      }
      private BetterAgeCompare() {
      }
      public static BetterAgeCompare comparator = new BetterAgeCompare();
    }

    public static Person findMax(Person[] A, PersonCompare pc) {
      Person result = null;
      for ( Person p : A ) {
        if (result == null) {
          result = p;
        } else if ( pc.compare(result, p) > 0 ) {
          result = p; 
        }
      }
      return result;
    }

    public static void printAll( Person[ ] arr )
    {
        for( int i = 0; i < arr.length; i++ )
        {
            if( arr[ i ] != null )
            {
                System.out.print( "[" + i + "] " + arr[ i ] );
                System.out.println( );
            }
        }
    }
    
    public static void main( String [ ] args ) {
        Person [ ] p = new Person[ 6 ];
        
        p[0] = new Person( "joe", 25, "New York", "212-555-1212" );
        p[1] = new Student( "becky", 27, "SF", "312-555-1212", 3.9 );
        p[2] = new Student( "andy", 21, "Chicago", "510-555-1212", 3.5 );
        p[3] = new Employee( "bob", 29, "Boston", "512-555-1212", 100000.0 );
        p[4] = new Person( "liz", 29, "Austin", "888-555-1212");
        p[5] = new Student( "john", 23, "Boston", "310-555-1212", 3.3 );
        
        if( p[3] instanceof Employee ) {
            ((Employee) p[3]).raise( .04 ); 
        }
       
        printAll( p );

        Person oldestPerson = findMax( p, new AgeCompare() );
        System.out.println("oldest person = " + oldestPerson);

        Person alphaFirstPerson = findMax( p, new NameCompare() );
        System.out.println("first person, alphabetically = " + alphaFirstPerson);

        oldestPerson = findMax( p, BetterAgeCompare.comparator );
        System.out.println("oldest person using singleton = " + oldestPerson);

        alphaFirstPerson = findMax( p, 
                                    new PersonCompare() {
                                          public int compare( Person p, Person q) {
                                            return p.getName().compareTo(q.getName());
                                          }
                                        }
        );

        Person highestGpa = findMax( p, 
                                     // student is greater than nonstudent, 
                                     // students are then compared on gpa
                                     new PersonCompare() {
                                          public int compare( Person p, Person q) {
                                            if ( !(q instanceof Student) ) {
                                              return -1;
                                            } else if ( !(p instanceof Student ) ) {
                                              return 1;
                                            } else {
                                              double pgpa = ((Student) p).getGPA();
                                              double qgpa = ((Student) q).getGPA();
                                              if ( qgpa > pgpa ) {
                                                return 1;
                                              } else if ( qgpa < pgpa ) {
                                                return -1;
                                              } else {
                                                return 0;
                                              }
                                            }
                                          }
                                        }
                                      );
        System.out.println("student with highest gpa = " + ((Student) highestGpa));
    }
}
