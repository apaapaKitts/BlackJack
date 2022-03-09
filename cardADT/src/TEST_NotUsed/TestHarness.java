/**
 * USED FOR ENSURING ONLY VALID CARDS CAN BE CONSTRUCTED
 * ___NOT___ used in the playing of blackjack ( only valid cards are passed in BlackJack )
 * Author: Andrew Pauls
 * Date:   March 9, 2022
 */

package TEST1;
import CARDS.*;
import BasicIO.*;

public class TestHarness {

    private ASCIIDisplayer display;

    public TestHarness ( ) {
        display  =  new ASCIIDisplayer();

        // attempt to build a card that is correctly given suit and rank
        Card a,x;
        a = new SomeCard("CLUBS", 5);
        display.writeLine("the suit of the card is " + a.getSuit());
        display.writeLine("the rank of the card is " + a.getRank());

        // attempt to build a card that is correctly given suit and rank
        display.waitForUser();
        a = new SomeCard("DIAMONDS", 11);
        display.writeLine("the suit of this card is " + a.getSuit());
        display.writeLine("the rank of the card is " + a.getRank());

        // attempt to build a card that is given an incorrect suit
        display.waitForUser();
        try {
            a = new SomeCard("diamonds", 2);
            display.writeLine("the suit of this card is " + a.getSuit());
            display.writeLine("the rank of the card is " + a.getRank());
        } catch ( AbsurdSuitException ase ) {
            display.writeLine("invalid attempt at a suit");
        }

        // attempt to build a card that is given an incorrect rank
        display.waitForUser();
        try {
            a = new SomeCard("HEARTS", 22);
            display.writeLine("the suit of this card is " + a.getSuit());
            display.writeLine("the rank of the card is " + a.getRank());
        } catch ( AbsurdRankException are ) {
            display.writeLine("invalid attempt at picking a rank");
        }


        // testing the toString method
        a = new SomeCard("SPADES", 12);
        String newString = a.toString();
        display.writeString(newString);
        display.waitForUser();


        // testing compareTo method
        a = new SomeCard("DIAMONDS", 2);
        x = new SomeCard("SPADES", 2);
        int result;
        result = a.compareTo(x);
        display.writeInt(result);
        result = x.compareTo(a);
        display.writeInt(result);
        display.waitForUser();
        a = new SomeCard("HEARTS", 12);
        result = a.compareTo(x);
        display.writeInt(result);
        result = x.compareTo(a);
        display.writeInt(result);
        display.waitForUser();

        display.close();

    }

    public static void main (String [] args) {TestHarness th = new TestHarness();}
}
