/**
 * a class that represents players in a blackjack game
 * for our purposes, players will only be ----> the user, the dealer
 * players may hold a hand, have a record of wins and losses
 */
package BlackJack;
import CARDS.*;

public class BlackJack_Player {

    Card[] hand;                                        // array to hold their hand. max possible hand size is 11 in Blackjack
    int currentHandsMaxScore;                           // used to keep track of hands score ( taking Aces as 11 )
    int currentHandsAltScore;                           // keeps track of hands score ( Aces as 1s )
    int handsWon;                                       // wins
    int handsLost;                                      // losses
    boolean isDealer;                                   // true when player is dealer. false otherwise
    int numCards;                                       // number of cards in hand

    public BlackJack_Player(boolean Dealer) {           // originally, player is fresh at the table. no hand, no record.
        hand                  = new Card[11];
        currentHandsMaxScore  = 0;
        currentHandsAltScore  = 0;
        handsWon              = 0;
        handsLost             = 0;
        isDealer              = Dealer;
        numCards              = 0;
    } // constructor


    public Card grabCard ( int index ) {            // pass an index representing the index of players hand. returns card at that index
        return hand[index];
    }

    public int getcurrentHandsMaxScore () {         // grabs the max score in the hand
        return currentHandsMaxScore;
    }

    public void LostRound () {                      // handles a loss of hand, prepares player for new hand. updates record
        this.handsLost = handsLost + 1;
        hand = new Card[11];
        currentHandsMaxScore = 0;
        currentHandsAltScore = 0;
        numCards = 0;
    }

    public void WonRound () {                       // handles a victory of hand, prepares player for new hand. updates record
        this.handsWon = handsWon + 1;
        hand = new Card[11];
        currentHandsMaxScore = 0;
        currentHandsAltScore = 0;
        numCards = 0;
    }

    public int getCurrentHandsAltScore () {         // grabs the alternate score
        return currentHandsAltScore;
    }

    public void getCard () {                        // provides the player with a new card for their hand
        hand[numCards] = DECK.topCard();            // in the next available position in dealers hand, put in the top card from the deck
        DECK.updateCurrentIndex();                  // update the top card to the card below the top card
        numCards = numCards + 1;                    // update the number of cards in the dealers hand by adding one
        updateCurrentHandsMaxScore();               // update the current score of the dealer
        updateCurrentHandsAltScore();               // update the current alt score of the dealer
    }

    private void updateCurrentHandsMaxScore() {     // must update max score whenever a player receives a new card
        currentHandsMaxScore = currentHandsMaxScore + hand[numCards - 1].getCount();
    }

    private void updateCurrentHandsAltScore() {     // must update alt score whenever a player receives a new card
        currentHandsAltScore = currentHandsAltScore + hand[numCards - 1].getAltCount();
    }

    public String getSuit ( int index ) {           // returns suit of card at the index passed
        return hand[index].getSuit();
    }

    public int getRank ( int index ) {              // returns rank of card at the index passed
        return hand[index].getRank();
    }

    public boolean getDealer () {                   // returns true or false if player is the dealer
        return isDealer;
    }

}
