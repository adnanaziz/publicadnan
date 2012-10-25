import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

public class GenTicTacToe {
  // L is the length of the line that X or O have to make to win
  // the input P is of the form "X" or "O" indicating which player is to play next
  // the input String... rows is a set of rows, each of which is a string
  // of the form "X,E,E,O". the rows are in order, e.g., when
  // winnable("X", "X,O,E", "E,E,O", "E,O,X") is called the corresponding
  // board position is 
  // E O X
  // E E O
  // X O E
  // You can assume that the number of entries per row equals
  // the number of rows
  public static boolean winnable(int L, String S, String... rows ) {
  //TODO(EE422C): implement this function
    return false;
  }

  // identical to function above, with K specifying the number
  // of moves allowed for player s
  public static boolean winnable(int L, String S, int K, String... rows ) {
  //TODO(EE422C): implement this function
    return false;
  }
}
