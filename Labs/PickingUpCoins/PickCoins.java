import static java.lang.Math.*;

public class PickCoins {

  private static int maxVictory_helperImproved(int[] C, int a, int b, int[][] cache, int[][] Sij) {

    if ( a > b ) {
      return 0;
    }

    if ( a == b ) {
      return C[a]; 
    }

    if ( cache[a][b] == -1 ) {
      int L = C[a] + ( Sij[a+1][b] - maxVictory_helperImproved(C, a+1, b, cache, Sij) );
      int R = C[b] + ( Sij[a][b-1] - maxVictory_helperImproved(C, a, b-1, cache, Sij) );
      cache[a][b] = max( L, R );
      // System.out.println("a, b = " + a + ", " + b);
      // System.out.println("L, R = " + L + "," + R);
    }
    return cache[a][b];
  }

  private static int maxVictory_helper(int[] C, int a, int b, int[][]cache) {

    if ( a > b ) {
      return 0;
    }

    if ( cache[a][b] == -1 ) {
      cache[a][b] = max(C[a] + min(maxVictory_helper(C, a + 2, b, cache),
                                maxVictory_helper(C, a + 1, b - 1, cache)),
                     C[b] + min(maxVictory_helper(C, a + 1, b - 1, cache),
                                maxVictory_helper(C, a, b - 2, cache)));
    }
    return cache[a][b];
  }

  private static int[][] initCache(int N) {
    int[][] cache = new int[N][];
    for (int i = 0 ; i < N; i++ ) {
      cache[i] = new int[N];
      for ( int j = 0 ; j < N; j++ ) {
        cache[i][j] = -1;
      }
    }
    return cache;
  }

  private static int[][] initSij(int[] C) {
    final int N = C.length;
    int[][] Sij = new int[N][];
    for (int i = 0 ; i < N; i++ ) {
      Sij[i] = new int[N];
      int tmp = 0;
      for ( int j = i ; j < N; j++ ) {
        if ( i == j ) {
          Sij[i][j] = C[i];
        } else {
          Sij[i][j] = Sij[i][j-1] + C[j];
        }
        // System.out.println("Sij[" + i + "][" + j + "] = " + Sij[i][j]);
      }
    }
    return Sij;
  }


  private static int maxVictory_helperGrangerBinLaw(int[] C, int i, int j, int[][] Sij) {
    if ( i == j ) {
      return C[i];
    }
    if ( i + 1 == j ) {
      return max(C[i], C[j]);
    }

    if ( C[i+1] - C[i] > C[j-1] - C[j] ) {
      return C[j] + Sij[i][j-1] - maxVictory_helperGrangerBinLaw(C, i, j-1, Sij);
    } else {
      return C[i] + Sij[i+1][j] - maxVictory_helperGrangerBinLaw(C, i+1, j, Sij);
    }
    
  }

  /**
   * Take an array of nonnegative ints representing the initial state of the
   * pick up coins game, and return the maximum amount of coins the first player
   * can pick up.
   *
   */
  public static int maxVictory(int[] C) {
    int[][] cache = initCache(C.length);
    return maxVictory_helper(C, 0, C.length - 1, cache);
  }

  
  public static int maxVictoryImproved(int[] C) {
    int[][] cache = initCache(C.length);
    int[][] Sij = initSij(C);
    return maxVictory_helperImproved(C, 0, C.length - 1, cache, Sij);
  }

  public static int maxVictoryGrangerBinLaw(int[] C) {
    int[][] Sij = initSij(C);
    return maxVictory_helperGrangerBinLaw(C, 0, C.length - 1, Sij);
  }

}
