import java.util.List;
import java.util.ArrayList;

public class Column {
    private List<Card> cards;
    private int position;

    public Column(int position) {
        cards = new ArrayList<>();
        this.position = position;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card getCardAt(int index) {
        return cards.get(index);
    }

    public Card getTopCard() {
        return cards.get(cards.size()-1);
    }

    public int getLength() {
        return cards.size();
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        String string = "";
        for (int i = 0; i < cards.size(); i++) {
            int position = cards.size()-i;
            String number = "";
            if (position < 10) {
                number += " " + (char) ('\u2080' + position);
            } else {
                number += (char) ('\u2080' + position/10);
                number += (char) ('\u2080' + position%10);
            }
            System.out.print("\u001b[30m" + number + "\u001b[0m" + " " + getCardAt(i) + "\n");
        }
        return string;
    }
}
