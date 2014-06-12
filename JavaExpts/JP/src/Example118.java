// Example 118 from page 89 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Vehicle { } 

class Car extends Vehicle { }

class Sedan extends Car { } 

class Shop<T> { 
  private T thing;
  public T buyFrom() { 
    return thing; 
  } 
  public void sellTo(T thing) { 
    this.thing = thing; 
  } 
}

class Example118 { 
  public static void main(String[] args) { 
    Object object = new Object();
    Vehicle vehicle = new Vehicle();
    Car car = new Car();
    Sedan sedan = new Sedan();

    Shop b0;
    b0 = new Shop<Object>();
    b0 = new Shop<Vehicle>();
    b0 = new Shop<Car>();
    b0 = new Shop<Sedan>();
    Object o0 = b0.buyFrom();
    // Vehicle v4 = b0.buyFrom();           // Illegal
    // Car c4 = b0.buyFrom();               // Illegal
    // Sedan s4 = b0.buyFrom();             // Illegal
    b0.sellTo(object);
    b0.sellTo(vehicle);  
    b0.sellTo(car);
    b0.sellTo(sedan);
    b0.sellTo(b0.buyFrom());  

    Shop<Car> b1;
    // b1 = new Shop<Object>();             // Illegal 
    // b1 = new Shop<Vehicle>();            // Illegal 
    b1 = new Shop<Car>();
    // b1 = new Shop<Sedan>();              // Illegal 
    Object o1 = b1.buyFrom();
    Vehicle v1 = b1.buyFrom();
    Car c1 = b1.buyFrom();
    // Sedan s1 = b1.buyFrom();             // Illegal
    // b1.sellTo(object);                   // Illegal
    // b1.sellTo(vehicle);                  // Illegal
    b1.sellTo(car);
    b1.sellTo(sedan);
    b1.sellTo(b1.buyFrom());

    Shop<?> b2;
    b2 = new Shop<Object>();
    b2 = new Shop<Vehicle>();
    b2 = new Shop<Car>();
    b2 = new Shop<Sedan>();
    Object o2 = b2.buyFrom();
    // Vehicle v2 = b2.buyFrom();           // Illegal
    // Car c2 = b2.buyFrom();               // Illegal
    // Sedan s2 = b2.buyFrom();             // Illegal
    // b2.sellTo(object);                   // Illegal
    // b2.sellTo(vehicle);                  // Illegal
    // b2.sellTo(car);                      // Illegal
    // b2.sellTo(sedan);                    // Illegal
    // b2.sellTo(b2.buyFrom());             // Illegal

    Shop<? extends Car> b3;
    // b3 = new Shop<Object>();             // Illegal
    // b3 = new Shop<Vehicle>();            // Illegal
    b3 = new Shop<Car>();
    b3 = new Shop<Sedan>();
    Object o3 = b3.buyFrom();
    Vehicle v3 = b3.buyFrom();
    Car c3 = b3.buyFrom();
    // Sedan s3 = b3.buyFrom();             // Illegal
    // b3.sellTo(object);                   // Illegal
    // b3.sellTo(vehicle);                  // Illegal
    // b3.sellTo(car);                      // Illegal
    // b3.sellTo(sedan);                    // Illegal
    // b3.sellTo(b3.buyFrom());             // Illegal

    Shop<? super Car> b4;
    b4 = new Shop<Object>();
    b4 = new Shop<Vehicle>();
    b4 = new Shop<Car>();
    // b4 = new Shop<Sedan>();              // Illegal
    Object o4 = b4.buyFrom();
    // Vehicle v4 = b4.buyFrom();           // Illegal
    // Car c4 = b4.buyFrom();               // Illegal
    // Sedan s4 = b4.buyFrom();             // Illegal
    // b4.sellTo(object);                   // Illegal
    // b4.sellTo(vehicle);                  // Illegal
    b4.sellTo(car);
    b4.sellTo(sedan);
    // b4.sellTo(b4.buyFrom());             // Illegal

    // Shop<Object> b5 = new Shop<Car>();   // Illegal
    Shop<Object> b5 = new Shop<Object>();
    Object o5 = b5.buyFrom();
    // Vehicle v4 = b5.buyFrom();           // Illegal
    // Car c4 = b5.buyFrom();               // Illegal
    // Sedan s4 = b5.buyFrom();             // Illegal
    b5.sellTo(object);
    b5.sellTo(vehicle);  
    b5.sellTo(car);
    b5.sellTo(sedan);
    b5.sellTo(b5.buyFrom());  

  }
}

