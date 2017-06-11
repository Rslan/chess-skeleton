package chess.moves;

import chess.GameState;
import chess.Player;
import chess.Position;
import chess.pieces.Piece;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static chess.Position.*;

/**
 * Determines a list of possible moves in a game.
 */
public class MoveDeterminant {

    /**
     * Get a list of possible moves for the current player.
     *
     * @param gameState current state of the game
     * @return a list of moves the current player can perform
     */
    public List<Move> getPossibleMoves(GameState gameState) {
        return getPossibleMoves(gameState, gameState.getCurrentPlayer());
    }

    /**
     * Get a list of possible moves for a specified player.
     *
     * @param gameState current state of the game
     * @param player    a player whose moves are to be calculated
     * @return a list of moves the specified player can perform
     */
    public List<Move> getPossibleMoves(GameState gameState, Player player) {
        return gameState.getPieces()
                        .entrySet()
                        .stream()
                        .filter(pieceAtPosition -> pieceAtPosition.getValue().getOwner() == player)
                        .flatMap(pieceAtPosition -> pieceAtPosition.getValue().getPossibleMoves(gameState, pieceAtPosition.getKey()).stream())
                        .collect(Collectors.toCollection(LinkedList::new));
    }

    public Position incCol(Position current) {
        char newCol = (char) (current.getColumn() + 1);
        return getNewPosition(newCol, current.getRow());
    }

    public Position decCol(Position current) {
        char newCol = (char) (current.getColumn() - 1);
        return getNewPosition(newCol, current.getRow());
    }

    public Position incRow(Position current) {
        int newRow = current.getRow() + 1;
        return getNewPosition(current.getColumn(), newRow);
    }

    public Position decRow(Position current) {
        int newRow = current.getRow() - 1;
        return getNewPosition(current.getColumn(), newRow);
    }

    public Position incColIncRow(Position current) {
        return incRow(incCol(current));
    }

    public Position incColDecRow(Position current) {
        return decRow(incCol(current));
    }

    public Position decColIncRow(Position current) {
        return incRow(decCol(current));
    }

    public Position decColDecRow(Position current) {
        return decRow(decCol(current));
    }

    public static boolean isOccupiedByRival(Position position, GameState gameState, Player rival) {
        Optional<Piece> piece = Optional.ofNullable(gameState.getPieceAt(position));
        return piece.isPresent() && piece.get().getOwner() == rival;
    }

    public static boolean isFree(Position position, GameState gameState) {
        return gameState.getPieceAt(position) == null;
    }

}
