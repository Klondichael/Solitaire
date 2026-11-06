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

    public Card getCardAtIndex(int index) {
        return cards.get(index);
    }

    public Card getCardAtPosition(int position) {
        return cards.get(cards.size()-position);
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

    public void takeCardAtPosition(int position) {
        cards.remove(cards.size()-position);
    }

    public void setTopCardFaceUp() {
        if (getLength() > 0) {
            getTopCard().setFaceUp(true);
        }
    }

    @Override
    public String toString() {
        int faceUpCardCount = 0;
        for (int i = 1; i <= getLength(); i++) {
            if (getCardAtPosition(i).isFaceUp()) {
                faceUpCardCount++;
            } else {
                break;
            }
        }
        String string = "";
        for (int i = 0; i < cards.size(); i++) {
            int position = cards.size()-i;
            String number = "";
            if (position <= faceUpCardCount) {
                if (position < 10) {
                    number += " " + (char) ('\u2080' + position);
                } else {
                    number += (char) ('\u2080' + position/10);
                    number += (char) ('\u2080' + position%10);
                }
            } else {
                number += "  ";
            }
            System.out.print("\u001b[30m" + number + "\u001b[0m" + " " + getCardAtIndex(i) + "\n");
        }
        return string;
    }
}
