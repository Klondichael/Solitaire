public enum Suit {
    CLUBS("Black", "♣"), DIAMONDS("Red", "♦"), HEARTS("Red", "♥"), SPADES("Black", "♠");

    private final String color;
    private final String icon;

    Suit(String color, String icon) {
        this.color = color;
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public String getColorCode() {
        if (color == "Red") {
            return "\u001B[31m";
        } else {
            return "\u001B[30m";
        }
    }

    public String getIcon() {
        return icon;
    }
}