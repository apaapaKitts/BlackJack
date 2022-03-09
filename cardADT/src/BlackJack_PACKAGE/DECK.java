/**
 * a DECK of cards
 * used in card games
 * in this program, we use the deck for playing the game BlackJack
 * author: Andrew Pauls
 * date:   March 9, 2021
 */

package BlackJack;

import CARDS.*;
import static CARDS.Card.RANKS;
import static CARDS.Card.SUITS;
import static java.lang.Math.random;

public class DECK {
    private static Card[]           Deck;                            // our actual card deck data structure
    private static int              currentTopCard = 0;              // index counter of the top card


    /**
     * the constructor puts one of each type of card in the "deck"
     * NOTE ******* DECK is NOT shuffled.
     */
    public DECK ( ) {
        Deck = new Card[52];                                    // an array of 'Card' objects, 52 of them
        Card theCard;                                           // some card
        int counter = 0;                                        // counter

        for (int i = 0; i < 4; i++) {                           // for each suit
            String theSuit;
            theSuit = SUITS[i];
            for (int j = 0; j < 13; j++) {                      // for each rank of card ( Ace, 1, 2, ..., King )
                int theRank;
                theRank = RANKS[j];
                theCard = new SomeCard(theSuit, theRank);       // build a card
                Deck[counter] = theCard;                        // put the card in the deck
                counter = counter + 1;                          // slide the counter along
            }
        }
    } // constructs a deck


    // methods
    public String getSuit (int index) {
        return Deck[index].getSuit();
    }

    public int getRank (int index) {
        return Deck[index].getRank();
    }

    /**
     * puts 'this' into a sufficiently random order
     */
    public static void shuffle() {
        for ( int i = 0 ; i < 75 ; i++ ) {
            // generate a random num between 0 - 51
            int randomCard1;
            int randomCard2;
            randomCard1 = (int) (52 * random());
            randomCard2 = (int) (52 * random());
            Card item;
            item = Deck[randomCard1];
            Deck[randomCard1] = Deck[randomCard2];
            Deck[randomCard2] = item;
        }
    }


    // method that is called whenever a player is dealt a card in BlackJack
    public static void updateCurrentIndex() {
        currentTopCard = currentTopCard + 1;
    }

    /**
     * returns the top card of the deck. if for example player and dealer have received 10 cards, the top of the deck would be
     * the 11th card in the "DECK" array
     * @return
     */
    public static Card topCard() {
        Card top;
        String suit;
        int rank;
        if ( currentTopCard < 52 ) {
            suit = Deck[currentTopCard].getSuit();
            rank = Deck[currentTopCard].getRank();
            currentTopCard = currentTopCard + 1;
        }
        else {              // if the entire deck has been dealt, build a new deck, shuffle it, return the first card (index[0])
            shuffle();
            currentTopCard = 0;
            suit = Deck[currentTopCard].getSuit();
            rank = Deck[currentTopCard].getRank();
            currentTopCard = currentTopCard + 1;
        }
        top = new SomeCard(suit, rank);
        return top;
    }

} // DECK
