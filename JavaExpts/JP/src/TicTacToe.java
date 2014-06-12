import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

enum Outcome {
  WIN, LOSE, DRAW;
}

enum SquareState {
  E, X, O;

  void checkNotEmpty() {
    if ( this == E ) {
      System.out.println("Eoor: empty square");
      assert(false);
    }
  }

  SquareState opposite( ) {
    this.checkNotEmpty();
    if ( this == X ) {
      return O;
    } 
    return X;
  }

  static SquareState fromString(String s) {
    if (s.equals("E"))
      return E;
    else if (s.equals("X")) 
      return X;
    else if (s.equals("O"))
      return O;
    else {
      System.out.println("Bad argument to SquareState constructor:" + s);
      assert(false);
      return null;
    }
  }
}

class Board {
  SquareState boardstate[][];
  int n; // dimension
  Map<SquareState[][], Outcome> cache = new HashMap<SquareState[][], Outcome>();
  
  boolean isFull() {
    int i, j;
    for ( i = 0 ; i < n; i++ ) {
      for ( j = 0 ; j < n; j++ ) {
        if ( boardstate[i][j] == SquareState.E ) {
          return false;
        }
      }
    }
    return true;
  }

  void set( int x, int y, SquareState s ) {
    assert ( 0 <= x && x < n && 0 <= y && y < n );
    boardstate[x][y] = s;
  }

  Board(int n) {
    this.boardstate = new SquareState[n][];
    this.n = n;
    int i, j;
    for (i = 0 ; i < n; i++ ) 
      boardstate[i] = new SquareState[n];
  }

  Board( String ... rows ) {
    n = rows.length;
    boardstate = new SquareState[n][];
    for ( int i = 0 ; i < n; i++ ) {
      boardstate[i] = new SquareState[n];
      String [] entries = (rows[i]).split(",");
      if ( entries.length != n ) {
        System.out.println("bad input");
        assert(false);
      }
      for ( int j = 0 ; j < entries.length; j++ ) {
        boardstate[i][j] = SquareState.fromString(entries[j]);
      }
    }
  }

  static public void p(String s) { System.out.println(s); }

  int numEmpty() {
    int result = 0;
    for ( int i = 0 ; i < n; i++ ) {
      for ( int j = 0 ; j < n; j++ ) {
        if ( boardstate[i][j] == SquareState.E ) {
          result++;
        }
      }
    }
    return result;
  }

  boolean winFor( SquareState s ) {
    boolean result = false;
    int i, j;
    for ( i = 0 ; i < n; i++ ) {
      result = true;
      for ( j = 0 ; j < n; j++ ) {
        if ( boardstate[i][j] != s ) {
          result = false;
          break;
        }
      }
      if (result) {
        return true;
      }
    }

    for ( j = 0 ; j < n; j++ ) {
      result = true;
      for ( i = 0 ; i < n; i++ ) {
        if ( boardstate[i][j] != s ) {
          result = false;
          break;
        }
      }
      if (result) {
        return true;
      }
    }

    result = true;
    for ( i = 0 ; i < n; i++ ) {
      if ( boardstate[i][i] != s ) {
        result = false;
        break;
      }
    }
    if (result) 
      return true;

    result = true;
    for ( i = 0 ; i < n; i++ ) {
      if ( boardstate[i][(n-1)-i] != s ) {
        result = false;
        break;
      }
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    for ( int i = 0 ; i < n; i++ ) {
      for ( int j = 0 ; j < n ; j++ ) {
        sb.append(boardstate[i][j]);
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  static long numCalls;

  static SquareState[][] deepcopy( SquareState[][] A) {
    SquareState [][] result = new SquareState[A.length][];
    for ( int i = 0; i < A.length; i++ ) {
      result[i] = A[i].clone();
    }
    return result;
  }

  Outcome playerBestOutcome( SquareState s ) {
    if ( (numCalls++ % 1000000) == 0 ) {
      p(numCalls + " calls, state is:\n" + this.toString() );
      System.out.flush();
    }

    Outcome cachedresult = cache.get(boardstate);

    if ( cachedresult != null ) {
      return cachedresult;
    }
    s.checkNotEmpty();
    if (this.isFull()) {
      return Outcome.DRAW;
    }
    if ( this.winFor( s ) ) {
      return Outcome.WIN;
    }
    if ( this.winFor( s.opposite() ) ) {
      return Outcome.LOSE;
    }

    int i, j;
    boolean canDraw = false;
    for ( i = 0 ; i < n; i++ ) {
      for ( j = 0 ; j < n; j++ ) {
        if ( boardstate[i][j] == SquareState.E ) {
          boardstate[i][j] = s;
          Outcome outcome = this.playerBestOutcome( s.opposite() );
          if ( outcome == Outcome.LOSE ) {
            boardstate[i][j] = SquareState.E;
            cache.put(deepcopy(boardstate), Outcome.WIN);
            return Outcome.WIN;
          }
          if ( outcome == Outcome.DRAW ) {
            canDraw = true;
          }
          boardstate[i][j] = SquareState.E;
        }
      }
    }
    if ( canDraw ) {
      cache.put(deepcopy(boardstate), Outcome.DRAW);
      return Outcome.DRAW;
    }
    return Outcome.LOSE;
  }
}

public class TicTacToe {
  public static void main(String[] args) {
    Board b1 = new Board("X,X", "X,X");
    assert( b1.winFor(SquareState.X));
    Board b2 = new Board("O,O", "O,O");
    assert( !b2.winFor(SquareState.X));
    Board b3 = new Board("O,X", "X,O");
    assert( b3.winFor(SquareState.X));
    Board b4 = new Board("O,X,O", "O,X,O", "X,X,O");
    assert( b4.winFor(SquareState.X));
    Board b5 = new Board("O,X,O", "O,E,O", "X,X,O");
    assert( !b5.winFor(SquareState.X));

    Board b6 = new Board("O,X,O", "O,E,O", "X,X,E");
    assert( Outcome.WIN == b6.playerBestOutcome(SquareState.X));

    Board b7 = new Board("E,E,E", "E,E,E", "E,E,E");
    assert( Outcome.DRAW == b7.playerBestOutcome(SquareState.X));

    Board b8 = new Board("O,O,O,E", "E,E,X,X", "X,X,E,E", "O,O,O,E");
    assert( Outcome.LOSE == b8.playerBestOutcome(SquareState.X));

    Board b9 = new Board("O,O,E,E", "E,E,E,E", "E,E,E,E", "E,E,X,X");
    assert( Outcome.DRAW == b9.playerBestOutcome(SquareState.X));
  }
}
