import model.*;
import java.util.*;

/**
 * Entry point for the text-based Scrabble game.
 * Allows players to take turns placing words or passing/exchanging tiles.
 */
public class Main {
    public static void main(String[] args) {

        // constants for commands
        final String QUIT = "QUIT";
        final String PASS = "PASS";

        Scanner scanner = new Scanner(System.in);

        // display welcome
        System.out.println("Welcome to Scrabble!");

        // Ask for number of players (2-4)

        boolean gettingInput = true; // true while the user is entering input
        int numPlayers = 0;

        while(gettingInput){
            System.out.print("Enter number of players (2-4): ");
            try{
                numPlayers = new Scanner(System.in).nextInt();

                if (numPlayers >= 2 && numPlayers <= 4){    // if the input is 2-4, continue
                    gettingInput = false;
                }else{
                    System.out.println("ERROR: can only be 2-4 players");   // if input is != 2->4, display error
                }

            }catch(Exception e){
                System.out.println("ERROR: enter a number");    // if the input != int, display error
            }
        }




        // Create and start game
        Game game = new Game(numPlayers);

        // Game loop
        while (true) {
            Player current = game.getCurrentPlayer();
            game.newTile(); //adds new tile to player's rack

            //display current player and tiles
            System.out.println("\nCurrent Player: " + current.getName());
            System.out.println("Rack: " + current.rackString());

            //display board
            game.getBoard().printBoard();

            //get command
            System.out.print("Enter command (WORD to place, PASS to exchange, QUIT to exit): ");
            String command = scanner.nextLine().trim();

            //execute command
            if (command.equalsIgnoreCase(QUIT)) break;

            if (command.equalsIgnoreCase(PASS)) {
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
