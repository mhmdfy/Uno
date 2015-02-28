/**
 *
 * @author MHMD
 */
/*
   An object of class card represents one of the 108 cards in a
   standard uno deck.  Each card has a color and
   a number.
*/


public class Card {

    public final static int RED = 0,        // Codes for the 4 colors.
                            YELLOW = 1,     // Including the WildCards
                            GREEN = 2,
                            BLUE = 3,
                            WILD = 4;
                            
    public final static int DRAWTWO = 10,    // Codes for the non-numeric cards.
                            REVERSE = 11,    // Cards 0 through 9 have their
                            SKIP = 12,       // numerical values for their codes.
                            WILD4 = 13;
                            
    private int color;   // The suit of this card, one of the constants
                               // RED, YELLOW, GREEN, BLUE.
                              
    private int number;  // The number of this card, from 0 to 12.
                             
    public Card(int theNumber, int theColor) {
            // Construct a card with the specified number and color.
            // numbers must be between 0 and 12.  Colors must be between
            // 0 and 4.  If the parameters are outside these ranges,
            // the constructed card object will be invalid.
        number = theNumber;
        color = theColor;
    }
        
    public int getColor() {
            // Return the int that codes for this card's color.
        return color;
    }
    
    public int getNumber() {
            // Return the int that codes for this card's value.
        return number;
    }
    
    public void changeColor(int newColor) {
    	color = newColor;
    }
    
    public void changeNumber(int newNumber) {
    	number = newNumber;
    }
    
    public String getColorAsString() {
            // Return a String representing the card's color.
            // (If the card's suit is invalid, "??" is returned.)
        switch ( color ) {
           case RED:      return "Red";
           case YELLOW:   return "Yellow";
           case GREEN:    return "Green";
           case BLUE:     return "Blue";
           case WILD:     return "Wild";
           default:       return "??";
        }
    }
    
    public String getNumberAsString() {
            // Return a String representing the card's number.
            // If the card's value is invalid, "??" is returned.
        switch ( number ) {
           case 0:   return "0";
           case 1:   return "1";
           case 2:   return "2";
           case 3:   return "3";
           case 4:   return "4";
           case 5:   return "5";
           case 6:   return "6";
           case 7:   return "7";
           case 8:   return "8";
           case 9:   return "9";
           case 10:  return "Draw-Two";
           case 11:  return "Reverse";
           case 12:  return "Skip";
           case 13:  return "Draw-Four";
           default:  return "??";
        }
    }
    
    public String toString() {
           // Return a String representation of this card,
           // such as "9 Green" or "Reverse Red".
           // In case it was WildCard, it will print it differently,
           // either "Wild" or "Wild Draw 4".

        if(getColorAsString().equals("Wild")) {
            return getColorAsString();
        }
        
        return getNumberAsString() + " " + getColorAsString();
    }


} // end class Card