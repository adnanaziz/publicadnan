# from Adam Barr's wonderful book "Find the Bug"

''' This function draws a car from a deck and puts it into a hand. It is
meant to be part of the game Go Fish, so if the resulting hand has all four 
suits for a given card rank, those four cards are removed from the hand. 

Cards are identified by their rank and suit: the rank is one of the elements
in the list ["2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"]
and the suit is on of the elements of the list ["spades", "hearts", "diamonds", "clubs"].

A deck is a list that initially contains 52 elements. Each element of the list
is a tuple with two elements: the rank and the suit. So a single entry
in the deck might be the tuple ("K", "spades"), which is the king of spades.

A hand is a dictionary. In each element of the dictionary, the key is
the rank and the value is a list that contains the names of the suits that the hand
holds for that rank. E.g., if a hand as the 3 of spades and the 3 of hearts, and
no other 3s, then the key "3" has the value ["spades", "hearts"]. A key should not
have an empty list associated with it - if no cards of a given rank are held,
no value exists for that key.'''

import random

def getCard(deck):

  ''' Randomly remove a single card from the deck and return it. Assumes that the deck 
      is not empty.

      deck: A deck as described above

      Returns: a single card, which is a tuple with two elements, the rank and the suit

  '''

  index = int (len(deck) * random.random())
  newCard = deck[index]
  del deck[index]
  return newCard

def drawCard(name, deck, hand):

  ''' Draw a new card from the deck and add it to the hand. If the hand now holds the rank
      in all four suits, then remove them from the hand.

      name: A string with the name of the playerHand, used on for display purposes.
      deck: A deck as described above
      hand: A hand dictionary as described above

      Returns: None.
  '''

  if len(deck) > 0: # protect against empty deck
    newCard = getCard(deck)
    cardRank = newCard[0]
    cardSuit = newCard[1]

    if cardRank in hand:
      # append this suit to the result
      hand[cardRank].append(cardSuit)
      if len(hand) == 4:
        print name, "lay down", cardRank + "s"
        del hand[cardRank]

    else:
      # first of this suit, create a list with one element
      hand[cardRank] =  [ cardSuit ]
