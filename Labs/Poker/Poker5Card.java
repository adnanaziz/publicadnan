import java.util.Comparator;
import java.util.Arrays;

class Hand5 {
  Card cards[] = new Card[5];

  public Hand5() {
  }

  public Hand5( Card a, Card b, Card c, Card d, Card e) {
   // TODO
  }


  //TODO: implement to return negative, 0, or positive if h1 is less than, 
  // equal to, or greater than h0. (This is the reverse of the 
  // usual convention, but it has the benefit that when used with Arrays.sort, 
  // it gives the best hand in index 0.)
  public int compare(Hand5 h0, Hand5 h1) {
    int cmp=0;
    return cmp;
  }

  // creates a hand from the 7 cards provided, skipping cards i and j
  public static Hand5 pick5( Card[] sevenCards, int i, int j ) {
    Hand5 result = null;
    return result;
  }
 
  // returns 21 hands of 5 cards made up from the seven cards
  // provided
  public static Hand5[] compute7Choose5( Card[] sevenCards ) {
    Hand5[] result = new Hand5[21]; // 7 choose 2 is 21
    int k = 0;
    for ( int i = 0 ; i < 7; i++ ) {
      for ( int j = i+1; j < 7; j++ ) {
        Hand5 aHand = pick5( sevenCards, i, j );
        result[k++] = aHand;
      }
    }
    return result;
  }

  public static Hand5 best5OutOf7( Card[] sevenCards ) {
    if ( sevenCards.length != 7 ) {
      System.out.println("Error: need 7 cards input to best5OutOf7");
      return null;
    }

    Hand5[] all5Hands = compute7Choose5( sevenCards );
    Arrays.sort(all5Hands, new Hand5Comparator() );

    return all5Hands[0];
  }

  // TODO: for students with more sophisticated programming skills
  // assume there are 0 -- 5 community cards. Each player has 2 cards
  // in the hand. return the probability of each player winning, using
  // the random simulation approach outlined in the lab document
  public static double[] probabilities( Card[] community, Card[]... players) {
    double[] result = new double[players.length];
    // TODO:writeme
    return result;
  }
}
