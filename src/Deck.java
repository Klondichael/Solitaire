import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        reset();
    }

    // Populate a standard 52-card deck
    public void reset() {
        cards.clear();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards); // Built-in efficient shuffling utility
    }

    public Card dealCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("The deck is empty!");
        }
        return cards.remove(cards.size() - 1); // Pops the top card
    }

    public void addCard(Card card) {
        cards.add(card);
        card.setFaceUp(false);
    }

    public int size() {
        return cards.size();
    }

    @Override
    public String toString() {
        if (size() > 0) {
            return "\u001B[47;34m" + "|*  *|" + "\u001B[0m";
        }
        return "\u001B[30m" + "[ ## ]" + "\u001B[0m";
    }
}
