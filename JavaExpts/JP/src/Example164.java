// Example 164 from page 139 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.lang.annotation.*;                         // Annotation
import static java.lang.annotation.ElementType.*;      // @Target arguments
import static java.lang.annotation.RetentionPolicy.*;  // @Retention arguments
import java.lang.reflect.*;                            // Method

// Annotation arguments are evaluated at compiletime; they must be
// constant expressions of a limited repertoire of types.

// Annotation objects are constructors are constructed at
// compile-time, and may either be discarded after that, or retained
// in the class file, or retained at run-time.

@Target({TYPE, METHOD})     // Attribute can be used on types and methods only
@Retention(RUNTIME)         // Attribute values are kept until run-time
@interface Author { 
  public final int oneHour = 60 * 60 * 1000;
  public String name();
  public Month month();
  public String[] diet() default { "Coffee", "Cola", "Mars bars" };
  public int weeklyWork() default 56 * oneHour;
}

@Retention(RUNTIME)         // Attribute values are kept until run-time
@interface Authors {        
  public Author[] value();  
}

class Example164 {
  @Author(name="Peter", month=Month.Nov, diet={ "Dr. Pepper" })
  public void myMethod1() { }

  @Author(name="Jens", month=Month.Jul)
  public void myMethod2() { }

  @Authors({@Author(name="Ulrik", month=Month.Jul),
            @Author(name="Andrzej", month=Month.Aug, diet = { "Tea" })})
  public void myMethod3() { }

  public static void main(String[] args) {
    Class ty = Example164.class;
    for (Method mif : ty.getMethods()) {
      if (mif.getName().startsWith("myMethod")) {
        System.out.println("\nGetting the annotations of " + mif.getName());
        // This finds only annotations with RUNTIME retention
        Annotation[] annos = mif.getDeclaredAnnotations();
        System.out.println("The annotations are:");
        for (Annotation anno : annos) 
          System.out.println(anno);
      }
    }
  }
}

