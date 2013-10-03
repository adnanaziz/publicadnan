import java.util.Comparator;
import java.util.Arrays;
import java.util.Random;

enum Suit {
  HEART, CLUB, SPADE, DIAMOND
}

enum Value {
  ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
}

class Card {
  Suit suit;
  Value value;

  Card( Suit s, Value v) {
    suit = s;
    value = v;
  }

  @Override
  public String toString() {
    return suit.toString() + value.toString();
  }

  @Override
  public int hashCode() {
    int code = 0;
    code = 2*value.hashCode() + 3 * suit.hashCode();
    return code;
  }

  @Override
  public boolean equals(Object o) {
    if ( o == null || !(o instanceof Card) ) {
      return false;
    }
    Card oc = (Card) o;
    return oc.suit == suit && oc.value == value;
 }

 private static final Random r = new Random(0);

 public static Card randomCard() {
   Suit s = Suit.values()[r.nextInt( Suit.values().length )];
   Value v = Value.values()[r.nextInt( Value.values().length )];
   return new Card( s, v );
 }

}

class CardCompare implements Comparator<Card> {
  private CardCompare() { };
  static public final CardCompare compareObject = new CardCompare();
  // returns negative if s1 < s2, positive if s1 > s2
  public int compare(Card s1, Card s2) {
    return s1.value.ordinal() - s2.value.ordinal();
  }
}

class Hand {
  Card cards[] = new Card[3];

  Hand( Card a, Card b, Card c) {
    cards[0] = a;
    cards[1] = b;
    cards[2] = c;
    Arrays.sort(cards, 
        CardCompare.compareObject
    );
  }

  boolean is3OfAKind() {
    return cards[0].value == cards[1].value && cards[1].value == cards[2].value;
  }

  boolean isFlush() {
    return cards[0].suit == cards[1].suit && cards[1].suit == cards[2].suit;
  }

  boolean isStraight() {
    return cards[0].value.ordinal() == cards[1].value.ordinal() - 1 
          && cards[1].value.ordinal() == cards[2].value.ordinal() - 1 
        || cards[0].value == Value.TWO && cards[1].value == Value.THREE && cards[2].value == Value.ACE;
  }
  
  boolean isStraightFlush() {
    return isFlush() && isStraight();
  }

  boolean isTwo() {
    return cards[0].value.ordinal() == cards[1].value.ordinal() 
      || cards[1].value.ordinal() == cards[2].value.ordinal();
  }

}

class HandComparator {

  private static int highCheck( boolean b0, boolean b1, Hand h0, Hand h1 ) {
    if ( !b0 && b1 ) {
      return 1;
    } else if ( b0 && !b1 ) {
      return -1;
    } else if (b0 && b1) { 
      int v0 = h0.cards[0].value.ordinal();
      int v1 = h1.cards[0].value.ordinal();
      return v1 - v0;
    }
    return 0;
  }

  private static int compare3OfAKind(Hand h0, Hand h1) {
    boolean b0 = h0.is3OfAKind();
    boolean b1 = h1.is3OfAKind();
    return highCheck( b0, b1, h0, h1 );
  }

  private static int compareStraightFlush(Hand h0, Hand h1) {
    boolean b0 = h0.isStraightFlush();
    boolean b1 = h1.isStraightFlush();
    return highCheck( b0, b1, h0, h1 );
  }

  private static int compareFlush(Hand h0, Hand h1) {
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

  private static int compareStraight(Hand h0, Hand h1) {
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
       if ( v0 == Value.TWO.ordinal() && h0.cards[2].value.ordinal() == Value.ACE.ordinal() ) {
         if ( v1 == Value.TWO.ordinal() && h1.cards[2].value.ordinal() == Value.ACE.ordinal() ) {
           return 0;
         } else {
           return 1;
         }
       } else if ( v1 == Value.TWO.ordinal() && h1.cards[2].value.ordinal() == Value.ACE.ordinal() ) {
         return -1;
       }
       return h1.cards[2].value.ordinal() - h0.cards[2].value.ordinal();
     }
     return 0;
 }

  private static int compareTwo(Hand h0, Hand h1) {
    boolean b0 = h0.isTwo();
    boolean b1 = h1.isTwo();
    if ( !b0 && b1 ) {
      return 1;
    } else if ( b0 && !b1 ) {
      return -1;
    } else if ( b0 && b1 ) {
      // check if tied on the pair - middle card is the common card
      int max0 = h0.cards[1].value.ordinal();
      int max1 = h1.cards[1].value.ordinal();
      if ( max0 != max1 ) {
        return max1 - max0;
      } else {
        // tied on common card
        return compareOne(h0, h1);
      } 
    } 
    return 0;
  }

  private static int compareOne(Hand h0, Hand h1) {
    for ( int i = h0.cards.length-1; i >= 0; i-- ) {
      int v0 = h0.cards[i].value.ordinal();
      int v1 = h1.cards[i].value.ordinal();
      if ( v0 != v1 ) {
        return v1 - v0;
      }
    }
    return 0;
  }

  public int compare(Hand h0, Hand h1) {
    int cmp;
    // use short circuit evaluation
    if ( (0 != ( cmp = compare3OfAKind(h0,h1) ) )
          || (0 != ( cmp = compareStraightFlush(h0,h1) ) )
          || (0 != ( cmp = compareFlush(h0,h1) ) )
          || (0 != ( cmp = compareStraight(h0,h1) ) )
          || (0 != ( cmp = compareTwo(h0,h1) ) )
          || (0 != ( cmp = compareOne (h0,h1) ) ) ) {
    }
    return cmp;
  }
}
