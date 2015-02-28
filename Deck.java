/**
 *
 * @author MHMD
 */
/*
    An object of type Deck represents an ordinary deck of 108 Uno cards.
    The deck can be shuffled, and cards can be drawn from the deck.
*/

public class Deck {

    private Card[] deck;   // An array of 108 Cards, representing the deck.
    private int cardsUsed; // How many cards have been drawn from the deck.

    public Deck() {
           // Create an unshuffled deck of cards.
       deck = new Card[108];
       int cardCt = 0; // How many cards have been created so far.
       for ( int color = 0; color <= 3; color++ ) {
          for ( int number = 0; number <= 12; number++ ) {
             deck[cardCt] = new Card(number,color);
             cardCt++;
          }
        }
       for (int color = 0; color <=3; color++) {
           for (int number = 1; number <= 12; number++) {
               deck[cardCt] = new Card(number, color);
               cardCt++;
           }
       }

       for (int i = 0; i <4; i++) {
             deck[cardCt] = new Card(0, 4);
             cardCt++;
       }

       for (int i = 0; i <4; i++) {
             deck[cardCt] = new Card(13, 4);
             cardCt++;
       }

       cardsUsed = 0;
    }

    public void shuffle() {
          // Used to restart the game.
          // Shuffles the deck with the discard pile into a random order.
        for ( int i = 107; i > 0; i-- ) {
            int rand = (int)(Math.random()*(i+1));
            Card temp = deck[i];
            deck[i] = deck[rand];
            deck[rand] = temp;
        }
        cardsUsed = 0;
    }

    public int cardsLeft() {
          // As cards are drawn from the deck, the number of cards left
          // decreases.  This function returns the number of cards that
          // are still left in the deck.
        return 108 - cardsUsed;
    }


    public Card draw() {
        // Draws the card that is at the top of the deck
        // and thus the deck would lose a card.
        if(cardsLeft() == 0)
        	shuffle();
        cardsUsed++;
        return deck[cardsUsed-1];
    }
    
    public String toString() {
        // Prints out the cards that are still in the deck in the deck.
        for(int i=0; i< deck.length-cardsUsed; i++) {
            System.out.println(deck[i]);
        }
        return "";
    }

} // end class Deck
