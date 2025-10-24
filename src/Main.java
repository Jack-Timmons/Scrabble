import model.*;
import java.util.*;

/**
 * Entry point for the text-based Scrabble game.
 * Allows players to take turns placing words or passing/exchanging tiles.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Scrabble!");

        // Ask for number of players (2-4)
        int numPlayers = 0;
        while (numPlayers < 2 || numPlayers > 4) {
            System.out.print("Enter number of players (2-4): ");
            numPlayers = scanner.nextInt();
        }
        scanner.nextLine(); // consume newline

        // Create and start game
        Game game = new Game(numPlayers);

        // Game loop
        while (true) {
            Player current = game.getCurrentPlayer();
            System.out.println("\nCurrent Player: " + current.getName());
            System.out.println("Rack: " + current.rackString());
            game.getBoard().printBoard();

            System.out.print("Enter command (WORD to place, PASS to exchange, QUIT to exit): ");
            String command = scanner.nextLine().trim();

            if (command.equalsIgnoreCase("QUIT")) break;

            if (command.equalsIgnoreCase("PASS")) {
                game.passTurn();
                continue;
            }

            // Try to play word
            boolean success = game.playWord(command);
            if (!success) {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            // Show updated scores
            game.printScores();

            // Next player's turn
            game.nextTurn();
        }

        System.out.println("Thanks for playing!");
        scanner.close();
    }
}
