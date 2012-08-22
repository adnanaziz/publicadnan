import java.util.Stack;
import java.util.zip.DataFormatException;

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
    }
    return stack.pop();
  }

  public static void main(String args[]) {
    try {
      evalRPN( "1 2 +" );
      evalRPN( "1 2 + 3 *" );
      evalRPN( "1 2 + 3 * +" );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
