/**
 * A Card Interface
 * Used in the playing of BlackJack
 * Author: Andrew Pauls
 * Date:   March 9, 2022
 */

package CARDS;

public interface Card {

    public String[] SUITS = {"CLUBS", "DIAMONDS", "SPADES", "HEARTS"};
    public int[] RANKS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    public int[] COUNT = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public int getRank();

    public String getSuit();

    public int getCount();


    public int getAltCount();

    public int compareTo( Card c );

    public String toString();
}
