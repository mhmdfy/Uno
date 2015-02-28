/**
 *
 * @author MHMD
 */
import java.util.ArrayList;

public class Enemy {
	private ArrayList<Card> hand;
	
	public Enemy() {
		hand = new ArrayList<Card>();
	}
	
	public void draw(Card card) {
		hand.add(card);
	}
	
	public int handSize() {
		return hand.size();
	}
	
	public Card discard(Card inDiscard) {
		for(int i= 0; i < hand.size(); i++) {
			if(hand.get(i).getNumber() == inDiscard.getNumber()
    		|| hand.get(i).getColor() == inDiscard.getColor()) {
				Card card = hand.get(i);
				hand.remove(i);
				return card;
			}
			
			else if(hand.get(i).getColor() == 4) {
				Card card = hand.get(i);
				hand.remove(i);
				return card;
			}
		}
		
		return null;
	}
	
	public void emptyHand() {
		for(int i = hand.size()-1; i >= 0; i--) {
			hand.remove(i);
		}
	}
	
}