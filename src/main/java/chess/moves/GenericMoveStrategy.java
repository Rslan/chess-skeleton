package chess.moves;

import chess.GameState;
import chess.Player;
import chess.Position;

import java.util.LinkedList;
import java.util.List;

import static chess.moves.MoveDeterminant.isFree;
import static chess.moves.MoveDeterminant.isOccupiedByRival;

/**
 * A strategy of a Piece movement.
 */
public abstract class GenericMoveStrategy implements MoveStrategy {

    protected final MoveDeterminant moveDeterminant;

    public GenericMoveStrategy(MoveDeterminant moveDeterminant) {
        this.moveDeterminant = moveDeterminant;
    }

    @Override
    public List<Move> getPossibleMoves(GameState gameState, Position currentPosition, Player currentPlayer) {
        List<Move> moves = new LinkedList<>();

        for (Direction direction : Direction.values()) {
            for (Position position : getPositions(direction, currentPosition, currentPlayer, gameState)) {
                if (isFree(position, gameState)) {
                    moves.add(new Move(currentPosition, position));
                } else if (isOccupiedByRival(position, gameState, gameState.getRival(currentPlayer))) {
                    moves.add(new Move(currentPosition, position));
                    break;
                } else {
                    break;
                }
            }
        }

        return moves;
    }

    protected abstract List<Position> getPositions(Direction direction, Position currentPosition, Player currentPlayer, GameState gameState);

    public enum Direction {
        FORWARD,
        BACKWARD,
        RIGHT,
        LEFT,
        FORWARD_RIGHT,
        FORWARD_LEFT,
        BACKWARD_RIGHT,
        BACKWARD_LEFT;
    }
}
