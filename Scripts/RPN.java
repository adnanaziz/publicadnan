import java.util.Stack;
import java.util.zip.DataFormatException;
import java.util.EmptyStackException;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target(TYPE)
@Retention(RUNTIME)

@interface Author {
  public String name() default  "Missing Name" ;
  public String uteid() default  "Missing UTEID" ;
}


// TODO(AA): remove the constructor
// TODO(EE422C): implement this as specified in the lab
class StackLeftoverException extends RuntimeException {
  public StackLeftoverException( String op ) {
    super("StackLeftoverException:" + op );
  }
}

@Author(name="Adnan Aziz", uteid="aa123")
public class RPN {

  // TODO(AA): remove this helper function
  public static boolean isStringNumber( String s ) {
    char [] chararray = s.toCharArray();
    for ( int i = 0 ; i < chararray.length; i++ ) {
       if ( (i > 0 ) && !Character.isDigit( chararray[i] ) ) {
         return false;
       }
       if ( i==0 ) {
        if (!Character.isDigit( chararray[i] )  && !(chararray[i] == '-')) { 
           return false;
        }
        if ( chararray.length == 1 ) {
          if ( chararray[i] == '-' ) {
            return false;
          }
        }
       }
    }
    return true;
  }

  // TODO(AA): remove the implementation of this function
  // TODO(EE422C): implement this function as per the lab specification
  public static int evalRPN( String s ) throws 
      ArithmeticException, 
      IllegalArgumentException, 
      StackLeftoverException {
    Stack<Integer> stack = new Stack<Integer>();
    String strim = s.trim();
    String [] strings = strim.split("[ ][ ]*");
    for ( String tmp : strings ) {
      // System.out.println(tmp + "," );
    }
    for ( int i = 0 ; i < strings.length; i++ ) {
        if ( isStringNumber( strings[i] ) ) {
          stack.push( new Integer( strings[i] ) );
        } else if ( strings[i].equals("+") ) {
          int a = stack.pop();
          int b = stack.pop();
          stack.push( a + b );
        } else if ( strings[i].equals("*") ) {
          int a = stack.pop();
          int b = stack.pop();
          stack.push( a * b );
        } else if ( strings[i].equals("-") ) {
          int b = stack.pop();
          int a = stack.pop();
          stack.push( a - b );
        } else if ( strings[i].equals("/") ) {
          int b = stack.pop();
          int a = stack.pop();
          if ( b == 0 ) {
            throw new ArithmeticException("Divide by zero:" + i);
          }
          stack.push( a / b );
        } else {
          throw new IllegalArgumentException("Bad input:" + i);
        }
      }
    if ( stack.size() > 1 ) {
      String badStackMessage = "Stack contains more than one entry:";
      for ( Integer i : stack ) {
        badStackMessage += (i.toString() + ",");
      }
      throw new StackLeftoverException(badStackMessage);
    }
    return stack.pop();
  }

  // this is a convenience function; it's a little
  // easier to experiment with the calculator without junit
  // using the main within the class, specified below
  public static void testEvalRPN( String s ) {
    try {
      Integer result = evalRPN( s );
      System.out.println(s + " = " + result );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String args[]) {
    testEvalRPN( "1 3 + 0 /" );
    testEvalRPN( " 2    -7    + -6    * -1 /  ");
    testEvalRPN( "1 2 +" );
    testEvalRPN( "1 2 + 3 *" );
    testEvalRPN( "1 2 + 3 * +" );
    testEvalRPN( " 0 1 2 + 3 * +" );
    testEvalRPN( " 0 1 2 + 3 * + -123" );
    testEvalRPN( "+" );
    testEvalRPN( "a b +" );
    testEvalRPN( "12.3 44 +" );
  }
}
