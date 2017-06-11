package chess.moves;

import chess.GameState;
import chess.Player;
import chess.Position;

import java.util.LinkedList;
import java.util.List;

/**
 * A strategy of the King movement.
 */
public class KingMoveStrategy extends GenericMoveStrategy {

    public KingMoveStrategy(MoveDeterminant moveDeterminant) {
        super(moveDeterminant);
    }

    @Override
    protected List<Position> getPositions(Direction direction, Position currentPosition, Player currentPlayer, GameState gameState) {
        List<Position> positions = new LinkedList<>();
        Position nextPosition = currentPosition;

        switch (direction) {
            case FORWARD:
                nextPosition = moveDeterminant.incRow(nextPosition);
                break;
            case BACKWARD:
                nextPosition = moveDeterminant.decRow(nextPosition);
                break;
            case LEFT:
                nextPosition = moveDeterminant.decCol(nextPosition);
                break;
            case RIGHT:
                nextPosition = moveDeterminant.incCol(nextPosition);
                break;
            case FORWARD_LEFT:
                nextPosition = moveDeterminant.decColIncRow(nextPosition);
                break;
            case FORWARD_RIGHT:
                nextPosition = moveDeterminant.incColIncRow(nextPosition);
                break;
            case BACKWARD_LEFT:
                nextPosition = moveDeterminant.decColDecRow(nextPosition);
                break;
            case BACKWARD_RIGHT:
                nextPosition = moveDeterminant.incColDecRow(nextPosition);
                break;
        }

        if (Position.EMPTY != nextPosition) {
            positions.add(nextPosition);
        }

        return positions;
    }

}
