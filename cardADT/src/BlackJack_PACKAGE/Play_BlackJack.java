package BlackJack;

import BasicIO.ASCIIDisplayer;
import BasicIO.ASCIIPrompter;

public class Play_BlackJack {


    private ASCIIDisplayer      display;                // need a display for the user to watch the game
    private DECK                theDeck;                // card deck to pull cards from
    private BlackJack_Player    player;                 // the blackjack player ( you! )
    private BlackJack_Player    dealer;                 // the blackjack dealer ( cpu )
    public boolean              userIsPlaying;          // true if user wants to keep playing. false while user is not playing
    private int                 hit;                    // boolean ( 1 or 2 ) that stores the choices 'hit' or 'stick'
    public boolean              playerHasLost;          // keeps track of if the user has lost the hand
    public boolean              dealerHasLost;          // keeps track of if the dealer has lost the hand

    public Play_BlackJack() {

        display = new ASCIIDisplayer();                     // we need a real displayer
        theDeck = new DECK();                               // we need a real deck
        playerHasLost = false;                              // initializes that no player has lost yet
        dealerHasLost = false;
        DECK.shuffle();                                     // deck needs to be shuffled
        enterGame();                                        // greeting message. initialize players.

        while ( userIsPlaying ) {                           // if the user wants to play = true, keep playing
            startRound();                                   // deals first two cards to player, one to dealer
            turn();                                         // allow the user to play the game
            displayInfo(player);                            // show players record
            displayInfo(dealer);                            // show dealers record
            userIsPlaying();                                // check if user wants to play another hand
        }
        display.writeLine("this is the end of the game");
        display.close();                                    // ending game.
    } // Play_BlackJack



    // methods
    /**
     * run at the start of a game of blackjack
     * player is greeted with a welcoming line
     * the player object and dealer object are initialized
     */
    public void enterGame () {
        display.writeString("Welcome to the BlackJack Table!");
        display.waitForUser();
        initPlayers();                                      // initializes the player and the dealer
        userIsPlaying();                                    // sets the boolean for play or not play
        display.newLine();
        display.writeLine(" When prompted, 1 for hit, 2 for stick.");
        display.writeLine("And, 1 for play again, anything else for quit");

    }

    /**
     * this method deals the player their first card.
     * the dealer then receives a card
     * the player receives their second card
     */
    public void startRound () {
        // deal each player a card, show the cards to the screen
        player.getCard();
        displayHand(player);
        dealer.getCard();
        displayHand(dealer);    // will also use the new display method
        display.waitForUser();
        player.getCard();       // user gets their second card
        displayHand(player);    // uses new display method
        display.newLine();
    }

    /**
     * prints to the displayer the contents of a players current hand
     * @param thePlayer ( could be dealer, or user )
     */
    private void displayHand ( BlackJack_Player thePlayer ) {
        display.newLine();
        if (thePlayer.getDealer()) display.writeLine("DEALER");
        else {
            display.writeLine("PLAYER");
        }
        for ( int i = 0 ; i < thePlayer.numCards ; i++ ) {
            display.writeLine(thePlayer.grabCard(i).getRank() + " of " + thePlayer.grabCard(i).getSuit());
        }
    }

    /**
     * gets the users desire, to hit or to stick
     * based on whether the user wants to hit or stick...
     *
     * if ( hit ) ---> give the player another card.
     *      display the players hand information to the screen
     *      check if the user busted out
     *      ( if player did not bust ) recall turn() -----> a recursive call
     *      ( if player busts )  cpu wins. player loses. score updated. user asked if they would like to play again
     *
     * if ( stick ) ----> get the users highest valid score
     *      dealer plays out
     *      ( if dealers busts ) player wins, dealer loses.
     *      ( if dealer beats player ) ...player loses, dealer wins
     */
    public void turn () {
        doesUserWantToHit();                                            // check if user wants to hit or stick after first 2 cards dealt

        // the player says hit me
        if (hit == 1) {
            display.writeLine("player says hit");
            player.getCard();                                           // give player a new card from top of deck
            displayHand(player);                                        // show the hand to the displayer
            checkForUserBust();                                         // check if having received the new card the player busted
        }

        // the player says stick
        else {
            display.writeLine("player says stick");
            int usersScore = 0;
            if ( player.getcurrentHandsMaxScore() < 22 ) {              // grabs the max score if its not a bust
                usersScore = player.getcurrentHandsMaxScore();
            }
            else {         // grabs the alt score if max is a bust
                usersScore = player.getCurrentHandsAltScore ();
            }
            dealerPlaysOut( usersScore );                               // tells dealer to try and beat the players best possible score
        }
    }


    /**
     * consumes the maximum valid user score
     * gives the dealer new cards until the dealer either busts out or the dealers score exceeds the players
     * @param userScore
     */
    private void dealerPlaysOut (int userScore) {                       // method that is called everytime the player  says stick
        int dealersScore;                                               // stores the best possible dealer score

        while ( true ) {                                                // dealer gets card until someone wins
            dealer.getCard();
            displayHand(dealer);

            // case 1. dealer has blackjack. dealer automatically wins.
            dealersScore = dealer.getcurrentHandsMaxScore();
            if (dealersScore == 21) {
                dealer.WonRound();
                player.LostRound();
                display.writeLine("you lost");
                playerHasLost = true;
                break;
            }

            // case 2. dealer has a max score less than blackjack.
            //   we check if dealers score >= userScore. if it is, dealer wins.
            else if (dealersScore < 21) {
                checkForDealerVictory(userScore, dealersScore);

                if ( need2break(userScore, dealersScore)) {         // check if dealer has legally surpassed players score)
                    break;
                }
            }

            // case 3. dealers max score is bigger than 21
            //   we check the situation based on the value of their alt score
            else {
                dealersScore = dealer.getCurrentHandsAltScore();

                // case 3.1. --> dealers alt score is a blackjack. dealer wins.
                if (dealersScore == 21) {
                    dealer.WonRound();
                    player.LostRound();
                    playerHasLost = true;
                    display.writeLine("you lost");
                    break;
                }

                // case 3.2 --> dealers alt score is less than blackjack. we check if dealer wins.
                else if (dealersScore < 21) {
                    checkForDealerVictory(userScore, dealersScore);
                    if ( need2break ( userScore, dealersScore )) {
                        break;
                    }
                }

                // case 3.3 --> dealers alt score is a bust. player wins.
                else {
                    player.WonRound();
                    dealer.LostRound();
                    display.newLine();
                    display.newLine();
                    display.newLine();
                    display.writeLine("you win");
                    display.newLine();
                    display.newLine();
                    display.newLine();
                    dealerHasLost = true;
                    break;
                }
            }
        }
    }


    /**
     * a method that returns a boolean that indicates whether the turn() loop needs to be broken out of
     * @return
     */
    private boolean need2break (int thePlayersScore, int dealersScore) {
        boolean doWeBreak = false;
        if (thePlayersScore <= dealersScore) {                  // if dealers score is tied or bigger than players
            doWeBreak = true;                                   // dealer ultimately has won
            return doWeBreak;
        }
        return doWeBreak;                                       // return is false if players score is bigger than dealers
    }

    /**
     * a simple method that handles the end game scenario that arises when the user says stick and the dealer gets a card
     * @param usersScore
     * @param dealersScore
     */
    private void checkForDealerVictory ( int usersScore , int dealersScore ) {
        if (usersScore <= dealersScore) {
            display.newLine() ;
            display.newLine();
            display.newLine();
            display.writeLine("you lost");
            display.newLine() ;
            display.newLine();
            display.newLine();
            display.waitForUser();
            dealer.WonRound();
            player.LostRound();
            playerHasLost = true;
        }
    }


    /**
     * a simple method has the potential to change the boolean value storing whether or not the user has lost
     * checks if both alt and max count is over or below or equal to 21 ('blackjack')
     */
    private void checkForUserBust () {
        int scoreBeingUsed = player.getcurrentHandsMaxScore();                      // grab users maximum count
        if (scoreBeingUsed <= 21) {                                                 // if the max is not a bust, recall turn (recall turn)
            turn();
        } else {                                                                    // if users max count is a bust
            scoreBeingUsed = player.getCurrentHandsAltScore();                      // grab the alt count
            if (scoreBeingUsed > 21) {                                              // they have busted
                userHasBusted();                                                    // a method that handles the user losing the game
            } else {                                                                // user has not busted with the lower score
                turn();                                                             // recall turn ( hit or stick )
            }
        }
    }


    /**
     * called when the user has busted. ends the round. updates all necessary information on player, dealer objects
     */
    public void userHasBusted () {
        playerHasLost = true;                           // exits the 'hand' of cards
        display.newLine() ;
        display.newLine();
        display.newLine();
        display.writeLine("you busted");
        display.newLine() ;
        display.newLine();
        display.newLine();
        display.waitForUser();
        player.LostRound();                             // prepare for new hand. update leaderboard.
        dealer.WonRound();
    }


    /**
     * simple method that buids a prompter that the user interacts with.
     * gets the desire of the user --> will they be hitting or sticking
     */
    public void doesUserWantToHit () {
        ASCIIPrompter input;
        hit = 0;
        display.waitForUser();
        input = new ASCIIPrompter();
        input.setLabel("1 or 2");
        hit = input.readInt();
        input.close();
    }


    /**
     * simple method that asks the user if they will keep playing or not after a hand has been completed
     */
    public void userIsPlaying () {
        ASCIIPrompter input;
        input = new ASCIIPrompter();
        input.setLabel("Play ?");
        int answer = input.readInt();
        if ( answer == 1 ){
            userIsPlaying = true; }
        else {
            userIsPlaying = false;
        }
        input.close();
    }


    /**
     * displays a Blackjack players information ( records, current hands contents, current hands possible scores )
     * @param thePlayer
     */
    public void displayInfo (BlackJack_Player thePlayer) {
        display.newLine();
        if ( thePlayer.getDealer() == true ) display.writeLine("DEALER");
        else {
            display.writeLine("PLAYER");
        }
        display.writeLine("Hands won: " + thePlayer.handsWon + ". Hands Lost: " + thePlayer.handsLost );
        display.writeLine("number of cards in hand: " + thePlayer.numCards);
    }


    /**
     * shows the deck order
     * @param ourDeck
     */
    public void displayDeck( DECK ourDeck) {
        display.writeLine("here is the start of the deck.");
        int index = 0;
        for (int i = 0; i < 52 ; i++) {
            display.writeLine("Index in Deck:" + index );
            display.writeLine("Suit:" + ourDeck.getSuit(i));
            display.writeLine("Rank:" + ourDeck.getRank(i));
            index = index + 1;
        }
        display.newLine();
    }


    /**
     * initializes the players who will be playing the game
     *
     */
    private void initPlayers () {
        player = new BlackJack_Player(false);
        dealer = new BlackJack_Player(true);
    }




    public static void main (String [] args) {Play_BlackJack pb = new Play_BlackJack();}
}   // Play_BlackJack
