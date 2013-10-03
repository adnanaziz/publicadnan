import java.util.Comparator;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

class Hand5 {
  Card cards[] = new Card[5];

  @Override
  public String toString() {
    String result = "";
    for (Card c: cards) {
      result += c.toString() + " ";
    }
    return result;
  }

  public Hand5() {
  }

  public Hand5( Card a, Card b, Card c, Card d, Card e) {
   // TODO
  //skip-begin
    cards[0] = a;
    cards[1] = b;
    cards[2] = c;
    cards[3] = d;
    cards[4] = e;
    Arrays.sort(cards,
        // ascending sort
        CardCompare.compareObject
    );
  //skip-end
  }

//skip-begin
  boolean is4OfAKind() {
	    return (cards[0].value == cards[1].value && cards[1].value == cards[2].value && cards[2].value == cards[3].value)
	    		|| (cards[1].value == cards[2].value && cards[2].value == cards[3].value && cards[3].value == cards[4].value);
	  }
  
  boolean is3OfAKind() {
    return (cards[0].value == cards[1].value && cards[1].value == cards[2].value)
    		|| (cards[1].value == cards[2].value && cards[2].value == cards[3].value)
    		|| (cards[2].value == cards[3].value && cards[3].value == cards[4].value);
  }

  boolean isFlush() {
    return cards[0].suit == cards[1].suit && cards[1].suit == cards[2].suit && cards[2].suit == cards[3].suit && cards[3].suit == cards[4].suit;
  }
  
  boolean isStraight() {
    return cards[0].value.ordinal() == cards[1].value.ordinal() - 1
          && cards[1].value.ordinal() == cards[2].value.ordinal() - 1
          && cards[2].value.ordinal() == cards[3].value.ordinal() - 1
          && cards[3].value.ordinal() == cards[4].value.ordinal() - 1
        || cards[0].value == Value.TWO && cards[1].value == Value.THREE && cards[2].value == Value.FOUR && cards[3].value == Value.FIVE && cards[4].value == Value.ACE;
  }

  boolean isStraightFlush() {
    return isFlush() && isStraight();
  }

  boolean isTwo() {
    return (cards[0].value == cards[1].value)
      || (cards[1].value == cards[2].value)
      || (cards[2].value == cards[3].value)
      || (cards[3].value == cards[4].value);
  }
  
  boolean isTwoPair() {
	    return (cards[0].value == cards[1].value && cards[2].value == cards[3].value)
	      || (cards[1].value == cards[2].value && cards[3].value == cards[4].value)
	      || (cards[0].value == cards[1].value && cards[3].value == cards[4].value);
  }
  
  boolean isFullHouse() {
	    return (cards[0].value == cards[1].value && cards[1].value == cards[2].value && cards[3].value == cards[4].value)
	      || (cards[0].value == cards[1].value && cards[2].value == cards[3].value && cards[3].value == cards[4].value);
  }
  
//skip-end
}


class Hand5Comparator implements Comparator<Hand5> {
//skip-begin

  public static int highCheck( boolean b0, boolean b1, Hand5 h0, Hand5 h1 ) {
    if ( !b0 && b1 ) {
      return 1;
    } else if ( b0 && !b1 ) {
      return -1;
    } else if (b0 && b1) {
      int v0 = h0.cards[0].value.ordinal();
      int v1 = h1.cards[0].value.ordinal();
      // Used to debug:
      // System.out.println(h0);
      // System.out.println(h1);
      return v1 - v0;
    }
    return 0;
  }

  public static int compare3OfAKind(Hand5 h0, Hand5 h1) {
    boolean b0 = h0.is3OfAKind();
    boolean b1 = h1.is3OfAKind();
    if ( !b0 && b1 ) {
        return 1;
      } else if ( b0 && !b1 ) {
        return -1;
      } else if (b0 && b1) {
        int v0 = h0.cards[2].value.ordinal();
        int v1 = h1.cards[2].value.ordinal();
        return v1 - v0;
      }
      return 0;
  }

  public static int compareStraightFlush(Hand5 h0, Hand5 h1) {
    boolean b0 = h0.isStraightFlush();
    boolean b1 = h1.isStraightFlush();
    return highCheck( b0, b1, h0, h1 );
  }

  public static int compareFlush(Hand5 h0, Hand5 h1) {
     boolean b0 = h0.isFlush();
     boolean b1 = h1.isFlush();
     if ( !b0 && b1 ) {
       return 1;
     } else if ( b0 && !b1 ) {
       return -1;
     } else if ( b0 && b1 ) {
       return compareOne(h0, h1);
     } else {
       return 0;
     }
   }

  public static int compareStraight(Hand5 h0, Hand5 h1) {
     boolean b0 = h0.isStraight();
     boolean b1 = h1.isStraight();
     if ( !b0 && b1 ) {
       return 1;
     } else if ( b0 && !b1 ) {
       return -1;
     } else if ( b0 && b1 ) {
       int v0 = h0.cards[0].value.ordinal();
       int v1 = h1.cards[0].value.ordinal();
       // special ace, 2, 3 straight; watch out for confusion with 2,3,4
       if ( v0 == Value.TWO.ordinal() && h0.cards[4].value.ordinal() == Value.ACE.ordinal() ) {
         if ( v1 == Value.TWO.ordinal() && h1.cards[4].value.ordinal() == Value.ACE.ordinal() ) {
           return 0;
         } else {
           return 1;
         }
       } else if ( v1 == Value.TWO.ordinal() && h1.cards[4].value.ordinal() == Value.ACE.ordinal() ) {
         return -1;
       }
       return h1.cards[4].value.ordinal() - h0.cards[4].value.ordinal();
     }
     return 0;
 }

  public static int compareTwo(Hand5 h0, Hand5 h1) {
    boolean b0 = h0.isTwo();
    boolean b1 = h1.isTwo();
    if ( !b0 && b1 ) {
      return 1;
    } else if ( b0 && !b1 ) {
      return -1;
    } else if ( b0 && b1 ) {
    	
      // Find pair value for h0
      int max0;
      if(h0.cards[0].value == h0.cards[1].value) {
    	  max0 = h0.cards[1].value.ordinal();
      } else if(h0.cards[1].value == h0.cards[2].value) {
    	  max0 = h0.cards[2].value.ordinal();
      } else if(h0.cards[2].value == h0.cards[3].value) {
    	  max0 = h0.cards[3].value.ordinal();
      } else {
    	  max0 = h0.cards[4].value.ordinal();
      }
      
      // Find pair value for h1
      int max1;
      if(h1.cards[0].value == h1.cards[1].value) {
    	  max1 = h1.cards[1].value.ordinal();
      } else if(h1.cards[1].value == h1.cards[2].value) {
    	  max1 = h1.cards[2].value.ordinal();
      } else if(h1.cards[2].value == h1.cards[3].value) {
    	  max1 = h1.cards[3].value.ordinal();
      } else {
    	  max1 = h1.cards[4].value.ordinal();
      }
    	
      // check if tied on the pair
      if ( max0 != max1 ) {
        return max1 - max0;
      } else {
        // tied on pair
        return compareOne(h0, h1);
      }
    }
    return 0;
  }

  public static int compareOne(Hand5 h0, Hand5 h1) {
    for ( int i = h0.cards.length-1; i >= 0; i-- ) {
      int v0 = h0.cards[i].value.ordinal();
      int v1 = h1.cards[i].value.ordinal();
      if ( v0 != v1 ) {
        return v1 - v0;
      }
    }
    return 0;
  }
  
  public static int compareTwoPair(Hand5 h0, Hand5 h1) {
	    boolean b0 = h0.isTwoPair();
	    boolean b1 = h1.isTwoPair();
	    if ( !b0 && b1 ) {
	      return 1;
	    } else if ( b0 && !b1 ) {
	      return -1;
	    } else if ( b0 && b1 ) {
	    	
	      // Find pair values for h0
	      int high0;
	      int low0;
	      if(h0.cards[0].value == h0.cards[1].value && h0.cards[2].value == h0.cards[3].value) {
	    	  high0 = h0.cards[3].value.ordinal();
	    	  low0 = h0.cards[1].value.ordinal();
	      } else if(h0.cards[1].value == h0.cards[2].value && h0.cards[3].value == h0.cards[4].value) {
	    	  high0 = h0.cards[4].value.ordinal();
	    	  low0 = h0.cards[2].value.ordinal();
	      } else {
	    	  high0 = h0.cards[4].value.ordinal();
	    	  low0 = h0.cards[1].value.ordinal();
	      }
	      
	      // Find pair values for h1
	      int high1;
	      int low1;
	      if(h1.cards[0].value == h1.cards[1].value && h1.cards[2].value == h1.cards[3].value) {
	    	  high1 = h0.cards[3].value.ordinal();
	    	  low1 = h0.cards[1].value.ordinal();
	      } else if(h1.cards[1].value == h1.cards[2].value && h1.cards[3].value == h1.cards[4].value) {
	    	  high1 = h0.cards[4].value.ordinal();
	    	  low1 = h0.cards[2].value.ordinal();
	      } else {
	    	  high1 = h0.cards[4].value.ordinal();
	    	  low1 = h0.cards[1].value.ordinal();
	      }
	    	
	      // check if tied on the high pair
	      if ( high0 != high1 ) {
	        return high1 - high0;
	      // check if tied on the low pair
	      } else if ( low0 != low1 ) {
	        return low1 - low0;
	      } else {
	        // tied on pair
	        return compareOne(h0, h1);
	      }
	    }
	    return 0;
  }
  
  public static int compareFullHouse(Hand5 h0, Hand5 h1) {
	  boolean b0 = h0.isFullHouse();
	  boolean b1 = h1.isFullHouse();
	  if ( !b0 && b1 ) {
		  return 1;
	  } else if ( b0 && !b1 ) {
		  return -1;
	  } else if (b0 && b1) {
		  int v0 = h0.cards[2].value.ordinal();
	      int v1 = h1.cards[2].value.ordinal();
	      return v1 - v0;
	  }
	  return 0;
  }
  
  public static int compareFour(Hand5 h0, Hand5 h1) {
	  boolean b0 = h0.is4OfAKind();
	  boolean b1 = h1.is4OfAKind();
	  if ( !b0 && b1 ) {
		  return 1;
	  } else if ( b0 && !b1 ) {
		  return -1;
	  } else if (b0 && b1) {
		  int v0 = h0.cards[2].value.ordinal();
	      int v1 = h1.cards[2].value.ordinal();
	      return v1 - v0;
	  }
	  return 0;
  }
//skip-end

  //TODO: implement to return negative, 0, or positive if h1 is less than, 
  // equal to, or greater than h0. (This is the reverse of the 
  // usual convention, but it has the benefit that when used with Arrays.sort, 
  // it gives the best hand in index 0.)
  public int compare(Hand5 h0, Hand5 h1) {
    int cmp=0;
//skip-begin
    // use short circuit evaluation
    if ( (0 != ( cmp = compareStraightFlush(h0,h1) ) )
          || (0 != ( cmp = compareFour(h0,h1) ) )
          || (0 != ( cmp = compareFullHouse(h0,h1) ) )
          || (0 != ( cmp = compareFlush(h0,h1) ) )
          || (0 != ( cmp = compareStraight(h0,h1) ) )
          || (0 != ( cmp = compare3OfAKind(h0,h1) ) )
          || (0 != ( cmp = compareTwoPair(h0,h1) ) )
          || (0 != ( cmp = compareTwo(h0,h1) ) )
          || (0 != ( cmp = compareOne (h0,h1) ) ) ) {
    }
//skip-end
    return cmp;
  }

  // creates a hand from the 7 cards provided, skipping cards i and j
  public static Hand5 pick5( Card[] sevenCards, int i, int j ) {
    Hand5 result = null;
  //skip-begin
    Card[] tmp = new Card[5];
  // TODO
    int count = 0;
    for ( int k = 0 ; k < 7; k++ ) {
      if ( k != i && k != j ) {
        tmp[count++] = sevenCards[k];
      }
    }
    result = new Hand5(tmp[0], tmp[1], tmp[2], tmp[3], tmp[4] );
  //skip-end
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

  public final static int NUMTRIALS = 10000;
  //skip-begin

  private final static Random r = new Random(0);
  //skip-end
  // returns the winner using a random deal for the remaining cards
  public static int simulate( Card[] community, Card[][] players ) {
    int result = -1;
    //skip-begin
    Set<Card> dealtCards= new HashSet<Card>();
    for ( Card c : community ) {
      dealtCards.add(c); 
    }
    for ( Card[] p : players ) {
      for ( Card c : p ) {
        dealtCards.add(c); 
      }
    }

    Card[] completeCommunity = new Card[5];
    int i;
    for ( i = 0 ; i < community.length; i++ ) {
      completeCommunity[i] = community[i];
    }

    while ( i < 5 ) {
      Card c = Card.randomCard();
      if ( ! dealtCards.contains( c ) ) {
        completeCommunity[i++] = c;
        dealtCards.add(c);
      }
    }

    Integer[] pids = new Integer[players.length];
    for ( Integer j = 0 ; j < players.length; j++ ) {
      pids[j] = j;
    }

    final Hand5[] hands = new Hand5[players.length];
    for ( i = 0 ; i < players.length; i++ ) {
      Card[] seven = new Card[7];
      for ( int j = 0 ; j < 5; j++ ) {
        seven[j] = completeCommunity[j];
      }
      seven[5] = players[i][0];
      seven[6] = players[i][1];
      hands[i] = best5OutOf7( seven );
    }

    Arrays.sort( pids, new Comparator<Integer>() {
      public int compare( Integer p, Integer q) {
        return new Hand5Comparator().compare( hands[p], hands[q] );
      }
    });

    int numTies = 1;
    while( true ) {
      if ( numTies == hands.length ) {
        break; // all players are tied
      }
      if ( 0 != new Hand5Comparator().compare( hands[numTies-1], hands[numTies] ) ) {
        break;
      } 
      numTies++;
    }

    result = pids[r.nextInt(numTies)];
    //skip-end
    return result;
  }

  // TODO: for students with more sophisticated programming skills
  // assume there are 0 -- 5 community cards. Each player has 2 cards
  // in the hand. return the probability of each player winning, using
  // the random simulation approach outlined in the lab document
  public static double[] probabilities( Card[] community, Card[]... players) {
    double[] result = new double[players.length];
    // TODO:writeme
    for ( int i = 0 ; i < NUMTRIALS; i++ ) {
      int winner = simulate( community, players );
      // TODO: biased to earlier players, does not take into account ties
      result[winner] += 1.0;
    }
    for ( int i = 0 ; i < result.length; i++ ) {
      result[i] /= ((double) NUMTRIALS);
    }
    return result;
  }
}
