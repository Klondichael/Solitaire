import java.util.Scanner;

public class Game {
    private Deck deck;
    private Board board;
    private Scanner scanner;
    private boolean isRunning;

    public Game() {
        deck = new Deck();
        scanner = new Scanner(System.in);
        isRunning = true;
    }

    public void Start() {
        System.out.println("Game Starting");
        deck.shuffle();

        board = new Board(deck);
        
        while (isRunning) {
            board.showBoard();
            displayMenu();
            int choice = getUserChoice();
            handleChoice(choice);
        }
    }

    public void displayMenu() {
        System.out.println("1: Draw Card From Deck");
        System.out.println("2: Take Card From Pile");
        System.out.println("3: Move Card From Column");
        System.out.println("4: End Game");
    }

    public int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid Input. Please enter a number.");
            scanner.next();
            displayMenu();
        }
        return scanner.nextInt();
    }

    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                board.drawCard();
                break;
            case 2:
                handleTakeCard();
                break;
            case 3:
                handleMoveCard();
                break;
            case 4:
                System.out.print("Are You Sure? (y/n) ");
                scanner.nextLine();
                if (scanner.nextLine().equals("y")) {
                    isRunning = false;
                }
                break;
        }
    }

    private void handleTakeCard() {
        Card drawnCard = board.getDrawnCard();
        int colChoice = getColumnChoice();
        Column column = board.getColumnAt(colChoice);
        if (handleColumnPlacement(column, drawnCard)) {
            board.takeDrawnCard();
        }
    }

    private void handleMoveCard() {

    }

    private int getColumnChoice() {
        boolean colEntryIsValid = false;
        int colChoice = 0;
        promptColumnChoice(board.getDrawnCard());
        while (!colEntryIsValid) {
            if (scanner.hasNextInt()) {
                colChoice = scanner.nextInt();
                if (colChoice < 1 || colChoice > 7) {
                    System.out.println("Please enter a number from 1 to 7.");
                } else {
                    colEntryIsValid = true;
                }
            } else {
                System.out.println("Invalid Input. Please enter a number.");
                scanner.next();
            }
            if (!colEntryIsValid) {
                promptColumnChoice(board.getDrawnCard());
            }
        }
        return colChoice;
    }

    // prompt user for column in which to place given card
    private void promptColumnChoice(Card card) {
        System.out.println("Where would you like to place the " + card.toWords() + "? " + card.toString());
        board.showColumns();
        System.out.print("Column #: ");
    }

    // handle placing card in column
    private boolean handleColumnPlacement(Column column, Card card) {
        if (cardIsAllowed(column, card)) {
            column.addCard(card);
            return true;
        }
        System.out.println("Cannot place card in column " + column.getPosition());
        return false;
    }

    // return true if a card is allowed to be placed in a column and false otherwise
    private boolean cardIsAllowed(Column column, Card card) {
        if (column.getLength() == 0) {
            if (card.getRank() == Rank.KING) {
                return true;
            } else {
                return false;
            }
        }

        Card topCard = column.getTopCard();
        if (topCard.getValue()-1 == card.getValue()) {
            if (!topCard.getColor().equals(card.getColor())) {
                return true;
            }
        }
        return false;
    }
}
