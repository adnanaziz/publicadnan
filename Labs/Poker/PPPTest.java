import org.junit.*;
import static org.junit.Assert.*;

public class PPPTest {

  @Test
  public void t1() {
    Hand h0 = new Hand( new Card(Suit.CLUB, Value.ACE), 
                        new Card(Suit.HEART, Value.ACE), 
                        new Card(Suit.DIAMOND, Value.ACE) );
    Hand h1 = new Hand( new Card(Suit.CLUB, Value.KING), 
                        new Card(Suit.HEART, Value.KING), 
                        new Card(Suit.DIAMOND, Value.KING) );
    int cmp = new HandComparator().compare(h0,h1);
    assertTrue(cmp<0);
  }
  
  @Test
  public void t2() {
    Hand h0 = new Hand( new Card(Suit.CLUB, Value.ACE), 
                        new Card(Suit.HEART, Value.TWO), 
                        new Card(Suit.DIAMOND, Value.FIVE) );
    Hand h1 = new Hand( new Card(Suit.CLUB, Value.KING), 
                        new Card(Suit.HEART, Value.KING), 
                        new Card(Suit.DIAMOND, Value.KING) );
    int cmp = new HandComparator().compare(h0,h1);
    assertTrue(cmp>0);
  }

  @Test
  public void t3() {
    Hand h0 = new Hand( new Card(Suit.CLUB, Value.FOUR), 
                        new Card(Suit.CLUB, Value.TWO), 
                        new Card(Suit.CLUB, Value.THREE) );
    Hand h1 = new Hand( new Card(Suit.CLUB, Value.ACE), 
                        new Card(Suit.HEART, Value.TWO), 
                        new Card(Suit.DIAMOND, Value.FOUR) );
    int cmp = new HandComparator().compare(h0,h1);
    assertTrue(cmp<0);
  }
  @Test
  public void t4() {
    Hand h0 = new Hand( new Card(Suit.CLUB, Value.ACE), 
                        new Card(Suit.CLUB, Value.TWO), 
                        new Card(Suit.CLUB, Value.THREE) );
    Hand h1 = new Hand( new Card(Suit.CLUB, Value.ACE), 
                        new Card(Suit.HEART, Value.TWO), 
                        new Card(Suit.DIAMOND, Value.FOUR) );
    int cmp = new HandComparator().compare(h0,h1);
    assertTrue(cmp<0);
  }
  @Test
  public void t5() {
    Hand h0 = new Hand( new Card(Suit.CLUB, Value.FIVE), 
                        new Card(Suit.CLUB, Value.TWO), 
                        new Card(Suit.CLUB, Value.THREE) );
    Hand h1 = new Hand( new Card(Suit.CLUB, Value.ACE), 
                        new Card(Suit.HEART, Value.TWO), 
                        new Card(Suit.DIAMOND, Value.FOUR) );
    int cmp = new HandComparator().compare(h0,h1);
    assertTrue(cmp<0);
  }
  @Test
  public void t5_1() {
    Hand h0 = new Hand( new Card(Suit.DIAMOND, Value.FIVE), 
                        new Card(Suit.CLUB, Value.TWO), 
                        new Card(Suit.CLUB, Value.THREE) );
    Hand h1 = new Hand( new Card(Suit.CLUB, Value.ACE), 
                        new Card(Suit.HEART, Value.TWO), 
                        new Card(Suit.DIAMOND, Value.THREE) );
    int cmp = new HandComparator().compare(h0,h1);
    assertTrue(cmp>0);
  }
  @Test
  public void t5_2() {
    Hand h0 = new Hand( new Card(Suit.DIAMOND, Value.ACE), 
                        new Card(Suit.CLUB, Value.TWO), 
                        new Card(Suit.CLUB, Value.THREE) );
    Hand h1 = new Hand( new Card(Suit.CLUB, Value.FOUR), 
                        new Card(Suit.HEART, Value.TWO), 
                        new Card(Suit.DIAMOND, Value.THREE) );
    int cmp = new HandComparator().compare(h0,h1);
    assertTrue(cmp>0);
  }
  @Test
  public void t5_3() {
    Hand h0 = new Hand( new Card(Suit.DIAMOND, Value.ACE), 
                        new Card(Suit.CLUB, Value.TWO), 
                        new Card(Suit.CLUB, Value.THREE) );
    Hand h1 = new Hand( new Card(Suit.CLUB, Value.ACE), 
                        new Card(Suit.HEART, Value.QUEEN), 
                        new Card(Suit.DIAMOND, Value.KING) );
    int cmp = new HandComparator().compare(h0,h1);
    assertTrue(cmp>0);
  }
  @Test
  public void t5_4() {
    Hand h0 = new Hand( new Card(Suit.CLUB, Value.ACE), 
                        new Card(Suit.CLUB, Value.TWO), 
                        new Card(Suit.CLUB, Value.THREE) );
    Hand h1 = new Hand( new Card(Suit.CLUB, Value.ACE), 
                        new Card(Suit.HEART, Value.QUEEN), 
                        new Card(Suit.CLUB, Value.KING) );
    int cmp = new HandComparator().compare(h0,h1);
    assertTrue(cmp<0);
  }
  @Test
  public void t5_5() {
    Hand h0 = new Hand( new Card(Suit.CLUB, Value.ACE), 
                        new Card(Suit.HEART, Value.TWO), 
                        new Card(Suit.CLUB, Value.THREE) );
    Hand h1 = new Hand( new Card(Suit.CLUB, Value.ACE), 
                        new Card(Suit.CLUB, Value.QUEEN), 
                        new Card(Suit.CLUB, Value.KING) );
    int cmp = new HandComparator().compare(h0,h1);
    assertTrue(cmp>0);
  }
  @Test
  public void t6() {
    Hand h0 = new Hand( new Card(Suit.CLUB, Value.FIVE), 
                        new Card(Suit.HEART, Value.FOUR), 
                        new Card(Suit.CLUB, Value.THREE) );
    Hand h1 = new Hand( new Card(Suit.CLUB, Value.ACE), 
                        new Card(Suit.HEART, Value.TWO), 
                        new Card(Suit.DIAMOND, Value.FOUR) );
    int cmp = new HandComparator().compare(h0,h1);
    assertTrue(cmp<0);
  }
  @Test
  public void t7() {
    Hand h0 = new Hand( new Card(Suit.CLUB, Value.FIVE), 
                        new Card(Suit.HEART, Value.FOUR), 
                        new Card(Suit.CLUB, Value.ACE) );
    Hand h1 = new Hand( new Card(Suit.CLUB, Value.FOUR), 
                        new Card(Suit.HEART, Value.TWO), 
                        new Card(Suit.DIAMOND, Value.FOUR) );
    int cmp = new HandComparator().compare(h0,h1);
    assertTrue(cmp>0);
  }
  @Test
  public void t8() {
    Hand h0 = new Hand( new Card(Suit.CLUB, Value.FIVE), 
                        new Card(Suit.HEART, Value.FOUR), 
                        new Card(Suit.CLUB, Value.ACE) );
    Hand h1 = new Hand( new Card(Suit.CLUB, Value.FOUR), 
                        new Card(Suit.HEART, Value.TWO), 
                        new Card(Suit.DIAMOND, Value.KING) );
    int cmp = new HandComparator().compare(h0,h1);
    assertTrue(cmp<0);
  }
  @Test
  public void t9() {
    Hand h0 = new Hand( new Card(Suit.CLUB, Value.FIVE), 
                        new Card(Suit.HEART, Value.FOUR), 
                        new Card(Suit.CLUB, Value.ACE) );
    Hand h1 = new Hand( new Card(Suit.CLUB, Value.FOUR), 
                        new Card(Suit.HEART, Value.FIVE), 
                        new Card(Suit.DIAMOND, Value.ACE) );
    int cmp = new HandComparator().compare(h0,h1);
    assertTrue(cmp==0);
  }
}

