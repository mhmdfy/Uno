/**
 *
 * @author MHMD
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*;

public class Uno extends JFrame implements ActionListener {
	
	private int toDo;
	
	private Deck deck;

    private JPanel jplBoard,     // Creates the Board which will have the The game itself
                   jplMyHand,    // The Cards in your hand
                   jplInfo,      // And the Information of the game
                   jplPiles,     // The deck and the discard pile
                   
                   jplPlayer1,  
                   jplPlayer2,   // The three enemy players.
                   jplPlayer3;     


    private JLabel jlbDeck,     // Creates the deck that you are going to draw from.
    			   jlbDiscard,  // Creates the discard pile.
    			   jlbP1Size,
    			   jlbP2Size,   // Creates the number of cards each player has.
    			   jlbP3Size;
    			   
    			   
    
    
    private ArrayList<JLabel> jlbHand;    // Creates arrays that will have the pictures
    private ArrayList<Card> cardsInHand;  // of the cards to choose the cards from the hand.
    
    private Card cardInDiscard;       // The card that is in the Discard to compare.
    
    private Enemy player1,            // Creates the players that you are going to play againts
    			  player2,
    			  player3;
    			  
    private JButton jbnPlay1,       // Creates the Buttons that should be clicked to make
    				jbnPlay2,       // a player makes his move.
    				jbnPlay3,
    				jbnRestart;
    				
   
   //Array of colors to choose from when using the wild cards.
   private String[] colors = {"Red", "Yellow", "Green", "Blue"};  

	// The main method.
    public static void main(String []args) {
            Uno u = new Uno();
            u.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            u.setVisible(true);
    }
	
	// The contructor.
    public Uno() {
        super(("Uno card game"));
        
        //creates the deck label and the action for it.
        deck = new Deck();
        deck.shuffle();
        
        jlbDeck = new JLabel(getFaceDownCardImage());
        jlbDeck.addMouseListener(new MouseAdapter() {
            	public void mouseClicked(MouseEvent e) {
            		deckMouseClicked(e);
            	}
            });
            
        //creates the discard pile and puts a card on it.
        int x=-1;
        do {
	        cardInDiscard = deck.draw();
	        if(cardInDiscard.getNumber() > 9 || cardInDiscard.getColor() > 3)
	        	deck.shuffle();
	        else {
	        	jlbDiscard = new JLabel(getCardImage(cardInDiscard.getNumber(), cardInDiscard.getColor()));
	        	x = 0;
	        }
        } while (x != 0);
        
        // Creates the button for restarting the game.
        jbnRestart = new JButton("Restart");
        jbnRestart.addActionListener(this);
        
        // puts the two piles into a panel
        jplPiles = new JPanel();
        jplPiles.setLayout(new FlowLayout());
        jplPiles.add(jlbDiscard);
        jplPiles.add(jlbDeck);
        jplPiles.add(jbnRestart);
        
        // creates the cards in the hand of the player from drawing 7 cards
        jlbHand = new ArrayList<JLabel>();
        cardsInHand = new ArrayList<Card>();
        
        // draws seven cards                
        for (int i=0; i < 7; i++) {
        	cardsInHand.add(deck.draw());
            JLabel theCard = new JLabel(getCardImage(cardsInHand.get(i).getNumber(), cardsInHand.get(i).getColor()));
            theCard.addMouseListener(new MouseAdapter() {
            	public void mouseClicked(MouseEvent e) {
            		handMouseClicked(e);
            	}
            });
            jlbHand.add(theCard);
        }
        
        // the panel the contains the cards in my hand
        jplMyHand = new JPanel();
        for (int i=0; i < jlbHand.size(); i++) {
            jplMyHand.add(jlbHand.get(i));
        }
        jplMyHand.setBackground(Color.BLUE);
		
		// creates the enemy players.
		player1 = new Enemy();
		player2 = new Enemy();
		player3 = new Enemy();
		
		// Draw 7 cards for each of the players
		for (int i=0; i < 7; i++) {
			player1.draw(deck.draw());
			player2.draw(deck.draw());
			player3.draw(deck.draw());
		}
		
		// Defines the buttons to be clicked to play.
		jbnPlay1 = new JButton("Play1");
		jbnPlay1.addActionListener(this);
		jbnPlay2 = new JButton("Play2");
		jbnPlay2.addActionListener(this);
		jbnPlay3 = new JButton("Play3");
		jbnPlay3.addActionListener(this);
		
		// add them to thier respective panels
		jplPlayer1 = new JPanel(new BorderLayout());
		jplPlayer1.add(new JLabel(getFaceDownCardImage()), BorderLayout.CENTER);
		jlbP1Size = new JLabel("Cards In Hand "+player1.handSize());
		jplPlayer1.add(jlbP1Size, BorderLayout.SOUTH);
		jplPlayer1.add(jbnPlay1, BorderLayout.NORTH);
		jplPlayer1.setBackground(Color.RED);
		
		jplPlayer2 = new JPanel(new BorderLayout());
		jplPlayer2.add(new JLabel(getFaceDownCardImage()), BorderLayout.CENTER);
		jlbP2Size = new JLabel("Cards In Hand "+player2.handSize());
		jplPlayer2.add(jlbP2Size, BorderLayout.SOUTH);
		jplPlayer2.add(jbnPlay2, BorderLayout.NORTH);
		jplPlayer2.setBackground(Color.YELLOW);
		
		jplPlayer3 = new JPanel(new BorderLayout());
		jplPlayer3.add(new JLabel(getFaceDownCardImage()), BorderLayout.CENTER);
		jlbP3Size = new JLabel("Cards In Hand "+player3.handSize());
		jplPlayer3.add(jlbP3Size, BorderLayout.SOUTH);
		jplPlayer3.add(jbnPlay3, BorderLayout.NORTH);
		jplPlayer3.setBackground(Color.GREEN);
		
		
		// creates the board of the game
		jplBoard = new JPanel();
        jplBoard.setLayout(new BorderLayout(10, 10));
        jplBoard.setBackground(Color.BLACK);
		
		// add the panels to the board
		jplBoard.add(jplMyHand, BorderLayout.SOUTH);
        jplBoard.add(jplPiles, BorderLayout.CENTER);
        jplBoard.add(jplPlayer1, BorderLayout.EAST);
        jplBoard.add(jplPlayer2, BorderLayout.NORTH);
        jplBoard.add(jplPlayer3, BorderLayout.WEST);
        
        // adds the board to the frame
        setSize (600,600);
        setLayout(new BorderLayout());
        add(jplBoard, BorderLayout.CENTER);
    }
    
    // performs the action for buttons clicked.
    public void actionPerformed(ActionEvent evt) {
    	String t = evt.getActionCommand();
    	if (t.equals("Play1"))
    		player1Play();
    	else if (t.equals("Play2"))
    		player2Play();
    	else if (t.equals("Play3"))
    		player3Play();
    	else if (t.equals("Restart")) {
    		restartGame();
    	}
    }

	// Discard the card that you have clicked on
	// after applying the rules
    public void handMouseClicked ( MouseEvent e) {
    	Component clickedComponent = e.getComponent();

    	for(int i=0; i < jlbHand.size(); i++) {
    		if(clickedComponent == jlbHand.get(i)) {
    			if(cardsInHand.get(i).getNumber() == cardInDiscard.getNumber()
    			|| cardsInHand.get(i).getColor() == cardInDiscard.getColor()) {
    				cardInDiscard = cardsInHand.get(i);
    				jlbDiscard.setIcon(getCardImage(cardsInHand.get(i).getNumber(), cardsInHand.get(i).getColor()));
    				toDo = cardsInHand.get(i).getNumber();
    				
    				jplMyHand.remove(jlbHand.get(i));
    				cardsInHand.remove(i);
    				jlbHand.remove(i);
    				jplMyHand.updateUI();
    				
    			}
    			
    			else if(cardsInHand.get(i).getColor() == 4) {
    				cardInDiscard = cardsInHand.get(i);
    				jlbDiscard.setIcon(getCardImage(cardsInHand.get(i).getNumber(), cardsInHand.get(i).getColor()));
    				
    				jplMyHand.remove(jlbHand.get(i));
    				cardsInHand.remove(i);
    				jlbHand.remove(i);
    				jplMyHand.updateUI();
    				
    				String colorChosen = (String) JOptionPane.showInputDialog(this, 
					        "What is the color you want to choose?",
					        "Choose Color",
					        JOptionPane.QUESTION_MESSAGE, 
					        null, 
					        colors, 
					        colors[0]);
    				if(colorChosen.equals("Red"))
    					cardInDiscard.changeColor(0);
    				else if(colorChosen.equals("Yellow"))
    					cardInDiscard.changeColor(1);
    				else if(colorChosen.equals("Green"))
    					cardInDiscard.changeColor(2);
    				else if(colorChosen.equals("Blue"))
    					cardInDiscard.changeColor(3);
    			}
    		}
    	}
    	
    	if(win())
    		JOptionPane.showMessageDialog(null, "You win!");
    }
    
    // draws a card if you clicked on the deck
    public void deckMouseClicked(MouseEvent e) {
    	drawCard();
    	toDo = -1;
    }
    
    // draws a card from the deck to the hand
    public void drawCard() {
    	Card card = deck.draw();
		cardsInHand.add(card);
    	JLabel theCard = new JLabel(getCardImage(card.getNumber(), card.getColor()));
        theCard.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		handMouseClicked(e);
        	}
        });
        jlbHand.add(theCard);
        jplMyHand.add(jlbHand.get(jlbHand.size()-1));
        jplMyHand.updateUI();
   	}
    
    
    // makes the play for player 1.
    // using the logic for each condition.
    public void player1Play() {
    	if (toDo == 10) {
    		player1.draw(deck.draw());
    		player1.draw(deck.draw());
    		toDo = -1;
    	}
    	
    	else if (toDo == 12) {
    		toDo = -1;
    	}
    	
    	else if (toDo == 13) {
    		player1.draw(deck.draw());
    		player1.draw(deck.draw());
    		player1.draw(deck.draw());
    		player1.draw(deck.draw());
    		toDo = -1;
    	}
    	
    	else {
	    	Card card = player1.discard(cardInDiscard);
	    	if (card == null) {
	    		player1.draw(deck.draw());
	    		card = player1.discard(cardInDiscard);
	    	}
	    	
	    	if(card != null) {
	    		jlbDiscard.setIcon(getCardImage(card.getNumber(), card.getColor()));
	    		cardInDiscard = card;
	    		if (card.getColor() == 4) {
	    			cardInDiscard.changeColor(0);
	    		}
	    		toDo = card.getNumber();
	    	}
    	}
    	jlbP1Size.setText("Cards In Hand "+player1.handSize());
    	if( haveWon(player1) ) {
    		JOptionPane.showMessageDialog(null, "unfortunately, Player 1 have won...");
    	}		
    }
    
    // makes the play for player 2.
    public void player2Play() {
    	if (toDo == 10) {
    		player2.draw(deck.draw());
    		player2.draw(deck.draw());
    		toDo = -1;
    	}
    	
    	else if (toDo == 12) {
    		toDo = -1;
    	}
    	
    	else if (toDo == 13) {
    		player2.draw(deck.draw());
    		player2.draw(deck.draw());
    		player2.draw(deck.draw());
    		player2.draw(deck.draw());
    		toDo = -1;
    	}
    	
    	else {
	    	Card card = player2.discard(cardInDiscard);
	    	if (card == null) {
	    		player2.draw(deck.draw());
	    		card = player2.discard(cardInDiscard);
	    	}
	    	
	    	if(card != null) {
	    		jlbDiscard.setIcon(getCardImage(card.getNumber(), card.getColor()));
	    		cardInDiscard = card;
	    		if (card.getColor() == 4) {
	    			cardInDiscard.changeColor(1);
	    		}
	    		toDo = card.getNumber();
	    	}
    	}
    	jlbP2Size.setText("Cards In Hand "+player2.handSize());
    	if( haveWon(player2) ) {
    		JOptionPane.showMessageDialog(null, "unfortunately, Player 2 have won...");
    	}		
    }
    
    // makes the play for player 3.
    public void player3Play() {
    	if (toDo == 10) {
    		player3.draw(deck.draw());
    		player3.draw(deck.draw());
    		toDo = -1;
    	}
    	
    	else if (toDo == 12) {
    		toDo = -1;
    	}
    	
    	else if (toDo == 13) {
    		player3.draw(deck.draw());
    		player3.draw(deck.draw());
    		player3.draw(deck.draw());
    		player3.draw(deck.draw());
    		toDo = -1;
    	}
    	
    	else {
	    	Card card = player3.discard(cardInDiscard);
	    	if (card == null) {
	    		player3.draw(deck.draw());
	    		card = player3.discard(cardInDiscard);
	    	}
	    	
	    	if(card != null) {
	    		jlbDiscard.setIcon(getCardImage(card.getNumber(), card.getColor()));
	    		cardInDiscard = card;
	    		if (card.getColor() == 4) {
	    			cardInDiscard.changeColor(2);
	    		}
	    		toDo = card.getNumber();
	    	}
    	}
    	jlbP3Size.setText("Cards In Hand "+player3.handSize());
    	if( haveWon(player3) ) {
    		JOptionPane.showMessageDialog(null, "unfortunately, Player 3 have won...");
    	}
    }
    
    // Checks if you still have a card in your hand or not,
    // return true if all are null (your hand is empty)
    // return false if you have any card left.
    public boolean win() {
    	for(int i = 0; i < cardsInHand.size(); i++) {
    		if(cardsInHand.get(i) != null)
    			return false;
    	}
    	
    	return true;
    }
    
    
    // Checks if one of the enemies still has cards or not.
    public boolean haveWon(Enemy e) {
    	return (e.handSize() == 0);
    }
    
    // Method to restart the game.
    public void restartGame() {
    	deck.shuffle();
    	int x=-1;
        do {
	        cardInDiscard = deck.draw();
	        if(cardInDiscard.getNumber() > 9 || cardInDiscard.getColor() > 3)
	        	deck.shuffle();
	        else {
	        	jlbDiscard.setIcon(getCardImage(cardInDiscard.getNumber(), cardInDiscard.getColor()));
	        	x = 0;
	        }
        } while (x != 0);
        
        // Empty your hand
        for(int i=cardsInHand.size()-1; i >= 0; i--) {
        	jplMyHand.remove(jlbHand.get(i));
        	cardsInHand.remove(i);
        	jlbHand.remove(i);
        	jplMyHand.updateUI();
        }
        
        // draw seven cards
        for (int i=0; i < 7; i++) {
        	drawCard();
        }
        
        // Empty the cards in each of the players' hands.
        player1.emptyHand();
        player2.emptyHand();
        player3.emptyHand();
        
        // Draw 7 cards for each of the players
		for (int i=0; i < 7; i++) {
			player1.draw(deck.draw());
			player2.draw(deck.draw());
			player3.draw(deck.draw());
		}
		jlbP1Size.setText("Cards In Hand "+player1.handSize());
		jlbP2Size.setText("Cards In Hand "+player2.handSize());
		jlbP3Size.setText("Cards In Hand "+player3.handSize());
	
    }
    
    
    // Methods to get the Image URL
    // then it retruns the ImageIcon for that URL.
    public ImageIcon getCardImage(int cardNumber ,int cardColor) {
    	ImageIcon icon;
    	java.net.URL imageURL = Uno.class.getResource("Images/"+cardNumber+cardColor+".gif");
    	
   		icon = new ImageIcon(imageURL);
		
		return icon;
    }
    
    public ImageIcon getFaceDownCardImage() {
    	ImageIcon icon;
    	java.net.URL imageURL = Uno.class.getResource("Images/fd.gif");
    	
    	icon = new ImageIcon(imageURL);
    	
    	return icon;
    }
}