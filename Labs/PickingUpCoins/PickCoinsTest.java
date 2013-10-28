import static org.junit.Assert.*;

import org.junit.Test;

public class PickCoinsTest {
  
  @Test
  public void simple1() {
    int A[] = new int[]{1,2,3,4};
    assertEquals(6, PickCoins.maxVictory(A) );
  }

  @Test
  public void simple2() {
    int A[] = new int[]{1,2,3};
    assertEquals(4, PickCoins.maxVictory(A) );
  }

  @Test
  public void complex1() {
    int A[] = new int[]{25,5,10,5,10,5,10,25,1,25,1,25,1,25,5,10};
    assertEquals(140, PickCoins.maxVictory(A) );
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
  }

}

