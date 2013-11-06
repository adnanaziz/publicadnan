import static org.junit.Assert.*;

import org.junit.Test;

public class PickCoinsTest {
  
  @Test
  public void simple1() {
    int A[] = new int[]{1,2,3,4};
    assertEquals(6, PickCoins.maxVictory(A) );
    assertEquals(6, PickCoins.maxVictoryImproved(A) );
    assertEquals(6, PickCoins.maxVictoryGrangerBinLaw(A) );
  }

  @Test
  public void simple2() {
    int A[] = new int[]{1,2,3};
    assertEquals(4, PickCoins.maxVictory(A) );
    assertEquals(4, PickCoins.maxVictoryImproved(A) );
    assertEquals(4, PickCoins.maxVictoryGrangerBinLaw(A) );
  }

  @Test
  public void complex1() {
    int A[] = new int[]{25,5,10,5,10,5,10,25,1,25,1,25,1,25,5,10};
    assertEquals(140, PickCoins.maxVictory(A) );
    assertEquals(140, PickCoins.maxVictoryGrangerBinLaw(A) );
  }

  private static int[] random_driver(int N) {
    int A[] = new int[N];
    java.util.Random rnd = new java.util.Random(0);
    for ( int i = 0 ; i < N; i++ ) {
      A[i] = rnd.nextInt(11);
    }
    return A;
  }

  @Test
  public void random1() {
    int A[] = random_driver(100);
    assertEquals(255, PickCoins.maxVictory(A) );
    assertEquals(255, PickCoins.maxVictoryImproved(A) );
    assertEquals(255, PickCoins.maxVictoryGrangerBinLaw(A) );
  }

  @Test
  public void random2() {
    for ( int i = 0 ; i < 1000 ; i++ ) {
      int N = 6;
      int A[] = random_driver(N);
      int expectedResult = PickCoins.maxVictory(A);
      int BinLawResult = PickCoins.maxVictoryGrangerBinLaw(A);
      if ( expectedResult != BinLawResult ) {
        System.out.println("BinLawResult is wrong!");
        for (int j = 0 ; j < N; j++ ) {
          System.out.print(A[j] + ",");
        }
        assertEquals(expectedResult, BinLawResult);
      }
    }
  }

}

