package chess;


import chess.moves.Move;
import chess.pieces.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static chess.Position.getNewPosition;

/**
 * Class that represents the current state of the game.  Basically, what pieces are in which positions on the
 * board.
 */
public class GameState {

    /**
     * The current player
     */
    private Player currentPlayer = Player.White;

    /**
     * A map of board positions to pieces at that position
     */
    private Map<Position, Piece> positionToPieceMap;

    /**
     * Create the game state.
     */
    public GameState() {
        positionToPieceMap = new HashMap<Position, Piece>();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets a rival of the current player of the game.
     *
     * @return a rival of the current player
     */
    public Player getRival() {
        return getRival(currentPlayer);
    }

    /**
     * Gets a rival of the specified player.
     *
     * @param player a player whose rival is to be calculated
     * @return a rival of the specified player
     */
    public Player getRival(Player player) {
        return player == Player.White ? Player.Black : Player.White;
    }

    /**
     * Call to initialize the game state into the starting positions
     */
    public void reset() {
        // White Pieces
        placePiece(new Rook(Player.White), getNewPosition("a1"));
        placePiece(new Knight(Player.White), getNewPosition("b1"));
        placePiece(new Bishop(Player.White), getNewPosition("c1"));
        placePiece(new Queen(Player.White), getNewPosition("d1"));
        placePiece(new King(Player.White), getNewPosition("e1"));
        placePiece(new Bishop(Player.White), getNewPosition("f1"));
        placePiece(new Knight(Player.White), getNewPosition("g1"));
        placePiece(new Rook(Player.White), getNewPosition("h1"));
        placePiece(new Pawn(Player.White), getNewPosition("a2"));
        placePiece(new Pawn(Player.White), getNewPosition("b2"));
        placePiece(new Pawn(Player.White), getNewPosition("c2"));
        placePiece(new Pawn(Player.White), getNewPosition("d2"));
        placePiece(new Pawn(Player.White), getNewPosition("e2"));
        placePiece(new Pawn(Player.White), getNewPosition("f2"));
        placePiece(new Pawn(Player.White), getNewPosition("g2"));
        placePiece(new Pawn(Player.White), getNewPosition("h2"));

        // Black Pieces
        placePiece(new Rook(Player.Black), getNewPosition("a8"));
        placePiece(new Knight(Player.Black), getNewPosition("b8"));
        placePiece(new Bishop(Player.Black), getNewPosition("c8"));
        placePiece(new Queen(Player.Black), getNewPosition("d8"));
        placePiece(new King(Player.Black), getNewPosition("e8"));
        placePiece(new Bishop(Player.Black), getNewPosition("f8"));
        placePiece(new Knight(Player.Black), getNewPosition("g8"));
        placePiece(new Rook(Player.Black), getNewPosition("h8"));
        placePiece(new Pawn(Player.Black), getNewPosition("a7"));
        placePiece(new Pawn(Player.Black), getNewPosition("b7"));
        placePiece(new Pawn(Player.Black), getNewPosition("c7"));
        placePiece(new Pawn(Player.Black), getNewPosition("d7"));
        placePiece(new Pawn(Player.Black), getNewPosition("e7"));
        placePiece(new Pawn(Player.Black), getNewPosition("f7"));
        placePiece(new Pawn(Player.Black), getNewPosition("g7"));
        placePiece(new Pawn(Player.Black), getNewPosition("h7"));
    }

    /**
     * Get the piece at the position specified by the String
     * @param colrow The string indication of position; i.e. "d5"
     * @return The piece at that position, or null if it does not exist.
     */
    public Piece getPieceAt(String colrow) {
        Position position = getNewPosition(colrow);
        return getPieceAt(position);
    }

    /**
     * Get the piece at a given position on the board
     * @param position The position to inquire about.
     * @return The piece at that position, or null if it does not exist.
     */
    public Piece getPieceAt(Position position) {
        return positionToPieceMap.get(position);
    }

    /**
     * Get all the pieces with their positions on the board.
     *
     * @return a collection of pieces that are present on the board with their positions
     */
    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(positionToPieceMap);
    }

    /**
     * Method to place a piece at a given position
     * @param piece The piece to place
     * @param position The position
     */
    private void placePiece(Piece piece, Position position) {
        positionToPieceMap.put(position, piece);
    }

    /**
     * Performs a move of a piece according to the passed {@link Move} object and switches the current player.
     *
     * @param move contains an information about the piece move, i.e. start and finish positions
     */
    public void movePiece(Move move) {
        movePieceVirtually(move, null);
        currentPlayer = currentPlayer == Player.White ? Player.Black : Player.White;
    }

    /**
     * Performs a virtual move of a piece according to the passed {@link Move} object without switching the current
     * player and returns a piece that is captured in a result of the move so that it can be placed back when rolling
     * back the move.
     *
     * @param move             contains an information about the piece move, i.e. start and finish positions
     * @param pieceToPlaceBack a piece captured when the virtual move was played which now has to be placed back
     * @return a piece captured in a result of the virtual move.
     */
    public Piece movePieceVirtually(Move move, Piece pieceToPlaceBack) {
        Piece capturedPiece = positionToPieceMap.put(move.getTo(), positionToPieceMap.remove(move.getFrom()));
        if (pieceToPlaceBack != null) {
            positionToPieceMap.put(move.getFrom(), pieceToPlaceBack);
        }
        return capturedPiece;
    }

    /**
     * Gets the current player's King position.
     *
     * @return a position of the King belonging to the current player
     */
    public Position getCurrentPlayerKingPosition() {
        Optional<Map.Entry<Position, Piece>> kingsPosition =
                positionToPieceMap.entrySet()
                                  .stream()
                                  .filter(pieceAtPosition -> pieceAtPosition.getValue().getOwner() == currentPlayer)
                                  .filter(pieceAtPosition -> pieceAtPosition.getValue() instanceof King)
                                  .findFirst();
        return kingsPosition.map(Map.Entry::getKey).orElse(null);
    }

    protected void setPieces(Map<Position, Piece> pieces) {
        positionToPieceMap = pieces;
    }

}
