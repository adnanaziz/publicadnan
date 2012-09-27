public class SudokuSolver {

   // "Near worst case" example from
   // http://en.wikipedia.org/wiki/Sudoku_algorithms
  public static final String testCaseString =
				"153 " + "178 " + "185 " +
				 "221 " + "242 " +
			 	 "335 " + "357 " +
				 "424 " + "461 " +
				 "519 " +
				 "605 " + "677 " + "683 " +
				 "722 " + "741 " +
				 "844 " + "889 ";

  public static final int N = 9;

  public static void main(String[] args) {
    String result = solve(testCaseString);
    boolean correct = isLegalSolution( result, testCaseString );
    System.out.println("Your solver ouput is " + (correct ? "wrong" : "right"));
  }

  public static String solve( String s ) {
    //@exclude
    int [][] matrix = readInput( s );
    return isSolvable(matrix);
    //@include
  }

  //@exclude
  public static String isSolvable(int[][] matrix) {
    String result = "none";
    if ( !isSolvable(0, 0, matrix)) {
      result = "UNSOLVABLE";
    } else {
      result = matrixToString(matrix);
    }
    return result;
  }


  public static boolean isSolvable(int i, int j, int[][] matrix) {
    if (i == N) {
      i = 0;
      if (++j == N) {
        return true;
      }
    }

    // Skip nonempty entries
    if (matrix[i][j] != 0) {
      return isSolvable(i+1,j,matrix);
    }

    for (int entryval = 1; entryval <= N; ++entryval) {
      // Note: practically, it's substantially quicker to check if entryval
      // conflicts with any of the constraints if we add it at (i,j) before
      // adding it, rather than adding it and then calling isValid. The reason
      // is that we know we are starting with a valid configuration, and the
      // only entry which can cause a problem is entryval at (i,j).
      if (validToAdd(i, j, entryval, matrix)) {
        matrix[i][j] = entryval;
        if (isSolvable(i + 1, j, matrix)) {
          return true;
        }
      }
    }

    // backtrack => undo assignment
    matrix[i][j] = 0;
    return false;
  }

  static boolean validToAdd(int i, int j, int entry, int[][] matrix) {
    // Row constraints
    for (int k = 0; k < N; ++k) {
      if (entry == matrix[k][j])
        return false;
    }

    // Column constraints
    for (int k = 0; k < N; ++k) {
      if (entry == matrix[i][k])
        return false;
    }

    // Region constraints
    int I = i / 3;
    int J = j / 3;
    for (int k = 0; k < 3; ++k) {
      for (int m = 0; m < 3; ++m) {
        if (entry == matrix[3 * I + k][3 * J + m]) {
          return false;
        }
      }
    }
    return true;
  }
  //@include

  public static boolean isLegalSolution( String solution, String initialConfig ) {
    int [][] solutionmatrix = readInput( solution );
    int [][] initialConfigMatrix = readInput( initialConfig );
    if (!isValid (solutionmatrix)) {
      return false;
    }
    // check that it's fully filled in
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        if ( solutionmatrix[i][j] == 0 || solutionmatrix[i][j] < 0 || solutionmatrix[i][j] > 9 ) {
          return false;
        }
        // check that it matches with initialConfigMatrix
        if ( initialConfigMatrix[i][j] != 0  &&
             initialConfigMatrix[i][j] != solutionmatrix[i][j] ) {
           return false;
         }
      }
    }
    return true;
  }

  // Check if a partially filled matrix has any conflicts
  public static boolean isValid(int[][] matrix) {
    // Row constraints
    for (int i = 0; i < N; ++i) {
      boolean[] present = new boolean[N + 1];
      for (int j = 0; j < N; ++j) {
        if (matrix[i][j] != 0 && present[matrix[i][j]]) {
          return false;
        } else {
          present[matrix[i][j]] = true;
        }
      }
    }

    // Column constraints
    for (int j = 0; j < N; ++j) {
      boolean[] present = new boolean[N + 1];
      for (int i = 0; i < N; ++i) {
        if (matrix[i][j] != 0 && present[matrix[i][j]]) {
          return false;
        } else {
          present[matrix[i][j]] = true;
        }
      }
    }

    // Region constraints
    for (int I = 0; I < 3; ++I) {
      for (int J = 0; J < 3; ++J) {
        boolean[] present = new boolean[N + 1];
        for (int i = 0; i < 3; ++i) {
          for (int j = 0; j < 3; ++j) {
            if (matrix[3 * I + i][3 * J + j] != 0 &&
                present[matrix[3 * I + i][3 * J + j]]) {
              return false;
            } else {
              present[matrix[3 * I + i][3 * J + j]] = true;
            }
          }
        }
      }
    }
    return true;
  }

  public static int[][] readInput( String arg ) {
    String [] args = arg.split("\\s");
    return readInput( args );
  }

   public static int[][] readInput( String[] args ) {
     int[][] result = new int[N][];
     for ( int k = 0 ; k < N; k++ ) {
       result[k] = new int[N];
     }
     for ( int k = 0 ; k < args.length; k++ ) {
       int val = new Integer( args[k] );
       // format: 634 -> in row 6, col 4 value is 4
       result[val / 100][(val % 100) / 10] = (val % 10);
     }
     return result;
   }

   static String matrixToString(int[][] matrix) {
     String result = "";
     for ( int i  = 0 ; i < matrix.length; i++ ) {
       for ( int j  = 0 ; j < matrix[0].length; j++ ) {
         result = result + i + j + matrix[i][j] + " ";
       }
     }
     return result;
   }


   static void print(String msg, int[][] matrix) {
     System.out.println(msg);
     for ( int i  = 0 ; i < matrix.length; i++ ) {
       for ( int j  = 0 ; j < matrix[0].length; j++ ) {
         System.out.print(matrix[i][j] + ( (j < 8) ? " " : "\n" ) );
       }
     }
   }
}
