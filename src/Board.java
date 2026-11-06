import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Board {
    private Deck deck;

    private Foundation clubs;
    private Foundation diamonds;
    private Foundation spades;
    private Foundation hearts;

    private Pile waste;
    private Deck stock;

    private Column Col1;
    private Column Col2;
    private Column Col3;
    private Column Col4;
    private Column Col5;
    private Column Col6;
    private Column Col7;

    private List<Foundation> foundations;
    private List<Column> tableau;

    public Board(Deck deck) {
        this.deck = deck;

        // the 4 piles where the finished suits go
        clubs = new Foundation(Suit.CLUBS);
        diamonds = new Foundation(Suit.DIAMONDS);
        spades = new Foundation(Suit.SPADES);
        hearts = new Foundation(Suit.HEARTS);

        // list of all 4 finished suit piles
        foundations = new ArrayList<>(Arrays.asList(clubs, diamonds, spades, hearts));

        // the 7 columns of cards where all the game stuff happens
        Col1 = new Column(1);
        Col2 = new Column(2);
        Col3 = new Column(3);
        Col4 = new Column(4);
        Col5 = new Column(5);
        Col6 = new Column(6);
        Col7 = new Column(7);
        // list of all 7 columns
        tableau = new ArrayList<>(Arrays.asList(Col1, Col2, Col3, Col4, Col5, Col6, Col7));

        // card that can be used from draw pile
        waste = new Pile();

        // clear current cards?? and populate with new ones
        reset();
    }

    public void reset() {
        for (Pile foundation : foundations) {
            foundation.reset();
        }
        for (int i = 0; i < 7; i++) {
            // add two random cards to each foundation pile and set topmost to face up
            for (int j = 0; j <= i; j++) {
                tableau.get(i).addCard(deck.dealCard());
            }
        }

        for (Column col : tableau) {
            col.getCardAtIndex(col.getLength()-1).setFaceUp(true);
        }
        
        // draw pile
        stock = deck;
    }

    public void showBoard() {
        // show the ace, waste, and stock piles
        System.out.println(clubs.toString()+" "+diamonds.toString()+" "+spades.toString()+" "+hearts.toString()+"        "+waste.toString()+" "+stock.toString());

        showColumns();
    }

    public void showColumns() {
        for (int i = 1; i <= 7; i++) {
            char character = (char) ('\u2080' + i);
            System.out.print("\u001b[30m" + character + "      " + "\u001b[0m");
        }
        System.out.println();
        // show all cards in play area
        // for each row going downwards until the end of the longest pile
        for (int i = 0; i < tableau.stream().mapToInt(Column::getLength).max().orElse(0); i++) {
            // for each column
            for (int j = 0; j < 7; j++) {
                if (i == 0 && tableau.get(j).getLength() == 0) {
                    // if the column has a length of 0, put placeholder at row 0
                    System.out.print("\u001B[30m" + "[ ## ]" + "\u001B[0m");
                } else if (tableau.get(j).getLength() < i+1) {
                    // if the columns length is less than current row, fill with blank space
                    System.out.print("      ");
                } else {
                    // print the card that is at this row and column
                    System.out.print(tableau.get(j).getCardAtIndex(i));
                }
                // leave a space between cards horizontally
                System.out.print(" ");
            }
            // newline after all columns
            System.out.println();
        }
    }

    public void showColumn(int column) {
        System.out.println(tableau.get(column-1));
    }

    public List<Foundation> getFoundations() {
        return foundations;
    }

    public void showFoundations() {
        for (int i = 1; i <= 4; i++) {
            char character = (char) ('\u2080' + i);
            System.out.print("\u001b[30m" + character + "      " + "\u001b[0m");
        }
        System.out.println();
        for (Pile foundation : foundations) {
            System.out.print(foundation.toString() + " ");
        }
        System.out.println();
    }

    public Column getColumnAt(int position) {
        return tableau.get(position-1);
    }

    public boolean wasteIsEmpty() {
        return waste.isEmpty();
    }

    public void drawCard() {
        if (stock.size() == 0) {
            while (!wasteIsEmpty()) {
                stock.addCard(waste.getTopCard());
                waste.takeTopCard();
            }
        }
        waste.addCard(stock.dealCard());
    }

    public Card getDrawnCard() {
        return waste.getTopCard();
    }

    public void takeDrawnCard() {
        waste.takeTopCard();
    }
}
