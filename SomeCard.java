/**
 * implements the 'Card' interface
 * Author: Andrew Pauls
 * Date:   March 9, 2022
 */

package CARDS;

public class SomeCard implements Card {

    public String suit;         // a card has a suit
    public int rank;            // a card has a rank ( 1 for Ace, 2, 3, ... 10, 11 for Jack, ...K )
    public int count;           // a card has a count ( 11 for Ace, 10 for face cards, rank for 2-->10
    public int altCount;        // ACES may take on the count 1 as well as 11. altCount = count for all non-Ace cards


    public SomeCard( String theSuit, int theRank ) {            // pass while constructing the suit and the rank
        if (checkForValidSuit(theSuit)) suit = theSuit;         // check a real suit has been passed
        else {
            throw new AbsurdSuitException();
        }

        if (checkForValidRank(theRank)) {                       // check a real rank has been passed
            rank = theRank;
            if ( 2 <= rank & rank <= 10 ) {                     // set the count, altCount appropriately
                count = rank;
                altCount = rank;
            }
            else if ( 11 == rank | 12 == rank | 13 == rank ) {  // set the face card counts appropriately
                count = 10;
                altCount = 10;
            }
            else if ( 1 == rank ) {                             // handles special case of ACES
                count = 11;
                altCount = 1;
            }
        }
        else {                                                  // throw an absurd rank exception if rank is not valid
            throw new AbsurdRankException();
        }
    }

    private boolean checkForValidSuit(String someSuit) {
        boolean result;
        result = false;
        for ( String s : SUITS ) {
            if ( s == someSuit ) {
                result = true;
            }
            else {
                if (result != true){
                    result = false;
                }
            }
        }
        return result;
    }

    private boolean checkForValidRank(int someRank) {
        boolean result;
        result = false;
        for ( int i : RANKS ) {
            if ( i == someRank ) {
                result = true;
            }
            else {
                if ( result != true ) {
                    result = false;
                }
            }
        }
        return result;
    }


    public int getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getCount() {
        return count;
    }

    public int getAltCount() {
        return altCount;
    }

    public int compareTo( Card c ) {
        // returns -1 if the card being passed is less than this card
        // returns 0 if the cards are the same
        // returns 1 if the card being passed is greater than this card
        int result;
        result = 0;
        int a = this.rank;
        int b = c.getRank();
        if ( a < b ) result = 1;
        if ( a > b ) result = -1;
        if ( a == b ) result = 0;

        return result;
    }



    public String toString() {
        String result;
        String component1 =  ""; // suit
        String component2 =  ""; // rank
        String suit;       // provided suit of the card 'c'

        if ( this.rank == 1 )              component1 = "A";
        if ( 1 < this.rank & this.rank < 11 ) component1 = Integer.toString(this.rank);
        if ( this.rank == 11 )             component1 = "J";
        if ( this.rank == 12 )             component1 = "Q";
        if ( this.rank == 13 )             component1 = "K";

        if ( this.suit == SUITS[0] ) component2 = "C";
        if ( this.suit == SUITS[1] ) component2 = "D";
        if ( this.suit == SUITS[2] ) component2 = "S";
        if ( this.suit == SUITS[3] ) component2 = "H";

        result = component1 + "_" +  component2;
        return result;
        }


    }


