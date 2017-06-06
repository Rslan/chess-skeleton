package chess;

import chess.pieces.Piece;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This class provides the basic CLI interface to the Chess game.
 */
public class CLI {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final BufferedReader inReader;
    private final PrintStream outStream;

    private GameState gameState = null;

    public CLI(InputStream inputStream, PrintStream outStream) {
        this.inReader = new BufferedReader(new InputStreamReader(inputStream));
        this.outStream = outStream;
        writeOutput("Welcome to Chess!");
    }

    /**
     * Write the string to the output
     *
     * @param str The string to write
     */
    private void writeOutput(String str) {
        this.outStream.println(str);
    }

    /**
     * Retrieve a string from the console, returning after the user hits the 'Return' key.
     *
     * @return The input from the user, or an empty-length string if they did not type anything.
     */
    private String getInput() {
        try {
            this.outStream.print("> ");
            return inReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from input: ", e);
        }
    }

    void startEventLoop() {
        writeOutput("Type 'help' for a list of commands.");
        doNewGame();

        while (true) {
            showBoard();

            writeOutput(gameState.getCurrentPlayer() + "'s Move");

            String input = getInput();
            if (input == null) {
                break; // No more input possible; this is the only way to exit the event loop
            } else if (input.length() > 0) {
                if (input.equals("help")) {
                    showCommands();
                } else if (input.equals("new")) {
                    doNewGame();
                } else if (input.equals("quit")) {
                    writeOutput("Goodbye!");
                    System.exit(0);
                } else if (input.equals("board")) {
                    writeOutput("Current Game:");
                } else if (input.equals("list")) {
                    writeAllPossibleMovements();
                } else if (input.startsWith("move")) {
                    MoveResult moveResult = move(input);
                    if (moveResult == MoveResult.CHECK_MATE) {
                        writeOutput("The game is over.  Congrats to " + gameState.getCurrentPlayer());
                    } else if (moveResult == MoveResult.DRAW) {
                        writeOutput("The game is over. Draw");
                    } else if (moveResult == MoveResult.CHECK) {
                        writeOutput("Check");
                    } else if (moveResult == MoveResult.ILLEGAL) {
                        writeOutput("Illegal movement");
                    }
                } else {
                    writeOutput("I didn't understand that.  Type 'help' for a list of commands.");
                }
            }
        }
    }

    private MoveResult move(String input) {
        final int COMMAND_PARAMETERS_NUMBER = 3;

        String[] splits = input.split(" ");

        boolean isCommandLengthValid = (splits.length == COMMAND_PARAMETERS_NUMBER);
        if (!isCommandLengthValid) {
            writeInvalidCommand();
        }
        String posFrom = splits[1];
        String posTo = splits[2];
        String pattern = "[a-h][1-8]";
        boolean isCommandMatchesValid = Pattern.matches(pattern, splits[1])
                && Pattern.matches(pattern, splits[2]);
        if (!isCommandMatchesValid) {
            writeInvalidCommand();
        }

        return gameState.movePiece(posFrom, posTo);
    }

    private void writeInvalidCommand() {
        writeOutput("Invalid command. Command format is 'move e2 e4'.");
    }

    private void writeAllPossibleMovements() {
        for (Map.Entry<Position, List<Position>> movement : gameState.getPossibleMovements().entrySet()) {
            Position from = movement.getKey();
            for (Position to : movement.getValue()) {
                writeOutput(from + " " + to);
            }
        }
    }

    private void doNewGame() {
        gameState = new GameState();
        gameState.reset();
    }

    private void showBoard() {
        writeOutput(getBoardAsString());
    }

    private void showCommands() {
        writeOutput("Possible commands: ");
        writeOutput("    'help'                       Show this menu");
        writeOutput("    'quit'                       Quit Chess");
        writeOutput("    'new'                        Create a new game");
        writeOutput("    'board'                      Show the chess board");
        writeOutput("    'list'                       List all possible moves");
        writeOutput("    'move <colrow> <colrow>'     Make a move");
    }

    /**
     * Display the board for the user(s)
     */
    String getBoardAsString() {
        StringBuilder builder = new StringBuilder();
        builder.append(NEWLINE);

        printColumnLabels(builder);
        for (int i = Position.MAX_ROW; i >= Position.MIN_ROW; i--) {
            printSeparator(builder);
            printSquares(i, builder);
        }

        printSeparator(builder);
        printColumnLabels(builder);

        return builder.toString();
    }


    private void printSquares(int rowLabel, StringBuilder builder) {
        builder.append(rowLabel);

        for (char c = Position.MIN_COLUMN; c <= Position.MAX_COLUMN; c++) {
            Piece piece = gameState.getPieceAt(String.valueOf(c) + rowLabel);
            char pieceChar = piece == null ? ' ' : piece.getIdentifier();
            builder.append(" | ").append(pieceChar);
        }
        builder.append(" | ").append(rowLabel).append(NEWLINE);
    }

    private void printSeparator(StringBuilder builder) {
        builder.append("  +---+---+---+---+---+---+---+---+").append(NEWLINE);
    }

    private void printColumnLabels(StringBuilder builder) {
        builder.append("   ");
        for (char c = Position.MIN_COLUMN; c <= Position.MAX_COLUMN; c++) {
            builder.append(" ").append(c).append("  ");
        }

        builder.append(NEWLINE);
    }

    public static void main(String[] args) {
        CLI cli = new CLI(System.in, System.out);
        cli.startEventLoop();
    }
}
