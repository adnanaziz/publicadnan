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
}

class Board {
  SquareState boardstate[][];
  int n; // dimension
  
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
    }
    if ( result ) {
      return true;
    }
    for ( j = 0 ; j < n; j++ ) {
      result = true;
      for ( i = 0 ; i < n; i++ ) {
        if ( boardstate[i][j] != s ) {
          result = false;
          break;
        }
      }
    }
    result = true;
    for ( i = 0 ; i < n; i++ ) {
      if ( boardstate[i][i] != s ) {
        result = false;
        break;
      }
    }
    if ( result ) {
      return true;
    }
    result = true;
    for ( i = 0 ; i < n; i++ ) {
      if ( boardstate[i][(n-1)-i] != s ) {
        result = false;
        break;
      }
    }
    return result;
  }

  Outcome playerBestOutcome( SquareState s ) {
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
        if ( boardstate[i][j] != SquareState.E ) {
          boardstate[i][j] = s;
          Outcome outcome = this.playerBestOutcome( s.opposite() );
          if ( outcome == Outcome.LOSE ) {
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
      return Outcome.DRAW;
    }
    return Outcome.LOSE;
  }
}

public class TicTacToe {
  public static void main(String[] args) {
    Board b = new Board(3);
    System.out.println("Best outcome is " + b.playerBestOutcome( SquareState.X ) );
    b.set(0,0,SquareState.X);
    b.set(1,1,SquareState.O);
    b.set(2,2,SquareState.X);
    b.set(1,2,SquareState.O);
    System.out.println("Best outcome is " + b.playerBestOutcome( SquareState.X ) );

  }
}
