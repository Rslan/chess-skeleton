package chess;

import chess.moves.Move;
import chess.moves.MoveDeterminant;
import chess.pieces.Piece;

import java.io.*;

import static chess.Position.getNewPosition;

/**
 * This class provides the basic CLI interface to the Chess game.
 */
public class CLI {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final BufferedReader inReader;
    private final PrintStream outStream;

    private GameState gameState = null;
    private final MoveDeterminant moveDeterminant;

    public CLI(InputStream inputStream, PrintStream outStream) {
        this.inReader = new BufferedReader(new InputStreamReader(inputStream));
        this.outStream = outStream;
        moveDeterminant = new MoveDeterminant();
        writeOutput("Welcome to Chess!");
    }

    /**
     * Write the string to the output
     * @param str The string to write
     */
    private void writeOutput(String str) {
        this.outStream.println(str);
    }

    /**
     * Retrieve a string from the console, returning after the user hits the 'Return' key.
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

            if (isGameOver()) {
                writeOutput("The game is over. Congrats to " + gameState.getRival());
                break;
            }

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
                    listPossibleMoves();
                } else if (input.startsWith("move")) {
                    String[] cmd = input.split("\\s");
                    if (cmd.length == 3) {
                        doMove(cmd[1], cmd[2]);
                    } else {
                        writeOutput("Usage: move {from} {to}");
                    }
                } else {
                    writeOutput("I didn't understand that.  Type 'help' for a list of commands.");
                }
            }
        }
    }

    private boolean isGameOver() {
        boolean checkMate;

        if (isCheck()) {
            checkMate = true;
            for (Move move : moveDeterminant.getPossibleMoves(gameState)) {
                Piece capturedPiece = null;
                try {
                    capturedPiece = gameState.movePieceVirtually(move, null);
                    if (!isCheck()) {
                        checkMate = false;
                        break;
                    }
                } finally {
                    gameState.movePieceVirtually(new Move(move.getTo(), move.getFrom()), capturedPiece);
                }
            }
        } else {
            checkMate = false;
        }

        return checkMate;
    }

    private boolean isCheck() {
        return moveDeterminant.getPossibleMoves(gameState, gameState.getRival())
                              .stream()
                              .anyMatch(move -> move.getTo().equals(gameState.getCurrentPlayerKingPosition()));
    }

    private void doMove(String from, String to) {
        try {
            Position posFrom = getNewPosition(from);
            Position posTo = getNewPosition(to);
            Move move = new Move(posFrom, posTo);
            if (moveDeterminant.getPossibleMoves(gameState).contains(move)) {
                gameState.movePiece(move);
            } else {
                writeOutput("The specified move is not possible");
            }
        } catch (IllegalArgumentException e) {
            writeOutput(e.getMessage());
        }
    }

    private void listPossibleMoves() {
        if (gameState != null) {
            writeOutput(gameState.getCurrentPlayer() + "'s Possible Moves");
            moveDeterminant.getPossibleMoves(gameState).forEach(move -> writeOutput(move.toString()));
        } else {
            writeOutput("Start a new game first");
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
