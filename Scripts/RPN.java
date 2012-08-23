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

@Author(name="Adnan Aziz", uteid="aa123")
public class RPN {

  public static boolean isStringNumber( String s ) {
    char [] chararray = s.toCharArray();
    for ( int i = 0 ; i < chararray.length; i++ ) {
       if ( !Character.isDigit( chararray[i] ) ) 
         return false;
    }
    return true;
  }

  public static int evalRPN( String s ) throws DataFormatException {
    Stack<Integer> stack = new Stack<Integer>();
    String [] strings = s.split(" ");
    for ( int i = 0 ; i < strings.length; i++ ) {
      try {
        if ( isStringNumber( strings[i] ) ) {
          stack.push( new Integer( strings[i] ) );
        } else if ( strings[i].equals("+") ) {
          int a = stack.pop();
          int b = stack.pop();
          stack.push( a + b );
        } else if ( strings[i].equals("*") ) {
          int a = stack.pop();
          int b = stack.pop();
          stack.push( a + b );
        } else {
          throw new DataFormatException("Unknown operation: " +  strings[i]);
        }
      } catch (EmptyStackException e) {
          throw new DataFormatException("Stack empty operation: " +  e.toString());
      }
    }
    return stack.pop();
  }

  public static void testEvalRPN( String s ) {
    try {
      evalRPN( s );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String args[]) {
    testEvalRPN( "1 2 +" );
    testEvalRPN( "1 2 + 3 *" );
    testEvalRPN( "1 2 + 3 * +" );
    testEvalRPN( "+" );
    testEvalRPN( "a b +" );
    testEvalRPN( "12.3 44 +" );
  }
}
