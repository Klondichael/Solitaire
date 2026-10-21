public class Card {
    private final Suit suit;
    private final Rank rank;
    private boolean faceUp;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.faceUp = false;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public int getValue() {
        return rank.getValue();
    }

    public String getColor() {
        return suit.getColor();
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    public String toWords() {
        return rank.name() + " of " + suit.name();
    }

    @Override
    public String toString() {
        if (faceUp) {
            return "\u001B[47m" + suit.getColorCode() + "|" + rank.getDisplay() + " ".repeat(3-(String.valueOf(rank.getDisplay())).length()) + suit.getIcon() + "|" + "\u001B[0m";
        }
        return "\u001B[47;34m" + "|*  *|" + "\u001B[0m";
    }
}
