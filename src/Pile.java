import java.util.ArrayList;
import java.util.List;

public class Pile {
    private List<Card> cards;
    private String title;

    public Pile() {
        cards = new ArrayList<>();
        title = "##";
    }

    public Pile(ArrayList<Card> cards) {
        this.cards = cards;
        title = "##";
    }

    public Pile(String title) {
        cards = new ArrayList<>();
        this.title = title;
    }
    
    public Pile(ArrayList<Card> cards, String title) {
        this.cards = cards;
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addCard(Card card) {
        cards.add(card);
        card.setFaceUp(true);
    }

    public void reset() {
        cards.clear();
    }

    public Card getCardAt(int index) {
        return cards.get(index);
    }

    public Card getTopCard() {
        return cards.get(cards.size()-1);
    }

    public void takeTopCard() {
        cards.remove(cards.size()-1);
    }

    public boolean isEmpty() {
        return cards.size() == 0;
    }

    @Override
    public String toString() {
        if (cards.size() == 0) {
            return "\u001B[30m" + "[ " + title + " ]" + "\u001B[0m";
        }
        return cards.get(cards.size()-1).toString();
    }
}
