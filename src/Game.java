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
        if (deck.size() == 0) {
            System.out.println("1: Replace Deck with Waste Pile and Draw Card");
        } else {
            System.out.println("1: Draw Card From Deck");
        }
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

    // handle taking a card from the draw pile
    private void handleTakeCard() {
        if (!board.wasteIsEmpty()) {
            Card drawnCard = board.getDrawnCard();
            switch (getFoundOrTab()) {
                case 1:
                    // place card in tableau
                    int colChoice = getColumnChoice();
                    Column column = board.getColumnAt(colChoice);
                    if (handleColumnPlacement(column, drawnCard)) {
                        board.takeDrawnCard();
                    }
                    break;
                case 2:
                    // place card in foundations
                    if (handleFoundPlacement(drawnCard)) {
                        board.takeDrawnCard();
                    }
                    break;
            }
        } else {
            System.out.println("The waste pile is empty. If you want to take a card, you must first draw a card from the deck.");
        }
    }

    // move card(s) between columns or to foundations
    private void handleMoveCard() {
        int colPosition = getColumnChoice();
        int cardPosition = getCardChoice(colPosition);
        Column column = board.getColumnAt(colPosition);
        Card card = column.getCardAtPosition(cardPosition);

        if (cardPosition == 1) {
            // if only the topmost card is selected
            switch (getFoundOrTab()) {
                case 1:
                    // place card in tableau
                    int endColumnPosition = getColumnChoice();
                    Column endColumn = board.getColumnAt(endColumnPosition);
                    if (handleColumnPlacement(endColumn, card)) {
                        // remove card from previous column
                        column.takeCardAtPosition(cardPosition);
                        column.setTopCardFaceUp();
                    }
                    break;
                case 2:
                    // place card in foundations
                    if (handleFoundPlacement(card)) {
                        // remove card from previous column
                        column.takeCardAtPosition(cardPosition);
                        column.setTopCardFaceUp();
                    }
                    break;
            }
        } else {
            // if the topmost card is not selected
            int endColumnPosition = getColumnChoice();
            Column endColumn = board.getColumnAt(endColumnPosition);
            if (cardIsAllowedInCol(endColumn, card)) {
                for (int i = cardPosition; i >= 1; i--) {
                    // place card in endColumn
                    handleColumnPlacement(endColumn, column.getCardAtPosition(i));
                    // remove card from previous column
                    column.takeCardAtPosition(i);
                }
                column.setTopCardFaceUp();
            } else {
                System.out.println("Cannot place card in column " + endColumn.getPosition());
            }
        }
    }

    // return 1 if placing card in tableau; return 2 if placing card in foundations
    private int getFoundOrTab() {
        boolean entryIsValid = false;
        int choice = 0;
        promptFoundOrTab();
        while (!entryIsValid) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice != 1 && choice != 2) {
                    System.out.println("Please enter a valid number.");
                } else {
                    entryIsValid = true;
                }
            } else {
                System.out.println("Invalid Input. Please enter a number.");
                scanner.next();
            }
            if (!entryIsValid) {
                promptFoundOrTab();
            }
        }
        return choice;
    }

    // return position of users column choice
    private int getColumnChoice() {
        boolean colEntryIsValid = false;
        int colChoice = 0;
        promptColumnChoice();
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
                promptColumnChoice();
            }
        }
        return colChoice;
    }

    // return position of users card choice
    private int getCardChoice() {
        int colChoice = getColumnChoice();
        Column column = board.getColumnAt(colChoice);
        boolean cardEntryIsValid = false;
        int cardChoice = 0;
        int availableCards = 0;
        for (int i = 1; i <= column.getLength(); i++) {
            if (column.getCardAtPosition(i).isFaceUp()) {
                availableCards++;
            } else {
                break;
            }
        }
        promptCardChoice(column);
        while (!cardEntryIsValid) {
            if (scanner.hasNextInt()) {
                cardChoice = scanner.nextInt();
                if (cardChoice < 1 || cardChoice > availableCards) {
                    System.out.println("Please enter a number from 1 to 7.");
                } else {
                    cardEntryIsValid = true;
                }
            } else {
                System.out.println("Invalid Input. Please enter a number.");
                scanner.next();
            }
            if (!cardEntryIsValid) {
                promptColumnChoice(board.getDrawnCard());
            }
        }
        return colChoice;
    }

    // return position of users card choice from given column
    private int getCardChoice(int colChoice) {
        Column column = board.getColumnAt(colChoice);
        boolean cardEntryIsValid = false;
        int cardChoice = 0;
        int availableCards = 0;
        for (int i = 1; i <= column.getLength(); i++) {
            if (column.getCardAtPosition(i).isFaceUp()) {
                availableCards++;
            } else {
                break;
            }
        }
        promptCardChoice(column);
        while (!cardEntryIsValid) {
            if (scanner.hasNextInt()) {
                cardChoice = scanner.nextInt();
                if (cardChoice < 1 || cardChoice > availableCards) {
                    System.out.println("Please enter a number from 1 to " + availableCards + ".");
                } else {
                    cardEntryIsValid = true;
                }
            } else {
                System.out.println("Invalid Input. Please enter a number.");
                scanner.next();
            }
            if (!cardEntryIsValid) {
                promptCardChoice(column);
            }
        }
        return cardChoice;
    }

    // prompt user to place card in either tableau or foundations
    private void promptFoundOrTab() {
        System.out.println("Would you like to place the card in the Tableau or in the Foundations?");
        System.out.println("1: Tableau");
        System.out.println("2: Foundations");
    }

    // prompt user for column
    private void promptColumnChoice() {
        System.out.println("Please select a column.");
        board.showColumns();
        System.out.print("Column #: ");
    }

    // prompt user for column in which to place given card
    private void promptColumnChoice(Card card) {
        System.out.println("Where would you like to place the " + card.toWords() + "? " + card.toString());
        board.showColumns();
        System.out.print("Column #: ");
    }

    // prompt user for which card to move
    private void promptCardChoice(Column column) {
        System.out.println("Which card would you like to move?");
        board.showColumn(column.getPosition());
        System.out.print("Card #: ");
    }

    // handle placing card in column
    private boolean handleColumnPlacement(Column column, Card card) {
        if (cardIsAllowedInCol(column, card)) {
            column.addCard(card);
            return true;
        }
        System.out.println("Cannot place card in column " + column.getPosition());
        return false;
    }

    // return true if a card is allowed to be placed in a column and false otherwise
    private boolean cardIsAllowedInCol(Column column, Card card) {
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

    // return the Foundation where the given card is valid; if none, return first foundation
    private Foundation getValidFound(Card card) {
        for (Foundation foundation : board.getFoundations()) {
            if (card.getSuit().equals(foundation.getSuit())) {
                if (foundation.isEmpty()) {
                    if (card.getRank().equals(Rank.ACE)) {
                        return foundation;
                    }
                } else {
                    if (!foundation.getTopCard().getRank().equals(Rank.KING)) {
                        if (foundation.getTopCard().getValue() == card.getValue() - 1) {
                            return foundation;
                        }
                    }
                }
            }
        }
        return board.getFoundations().get(0);
    }

    // handle placement of given card into its foundation if valid and return true; if not valid, tell user and return false
    private boolean handleFoundPlacement(Card card) {
        if (cardIsAllowedInFound(card)) {
            Foundation foundation = getValidFound(card);
            foundation.addCard(card);
            return true;
        }
        System.out.println("No valid placement for " + card.toString() + " in foundation piles.");
        return false;
    }

    // return whether a valid placement in the foundations exists for a given card;
    private boolean cardIsAllowedInFound(Card card) {
        for (Foundation foundation : board.getFoundations()) {
            if (card.getSuit().equals(foundation.getSuit())) {
                if (foundation.isEmpty()) {
                    if (card.getRank().equals(Rank.ACE)) {
                        return true;
                    }
                } else {
                    if (!foundation.getTopCard().getRank().equals(Rank.KING)) {
                        if (foundation.getTopCard().getValue() == card.getValue() - 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
