public class Foundation extends Pile {
    private final Suit suit;

    public Foundation(Suit suit) {
        super();
        this.suit = suit;
        setTitle("A" + suit.getIcon());
    }

    public Suit getSuit() {
        return suit;
    }
}
