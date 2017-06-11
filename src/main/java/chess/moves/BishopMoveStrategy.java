package chess.moves;

import chess.GameState;
import chess.Player;
import chess.Position;

import java.util.LinkedList;
import java.util.List;

/**
 * A strategy of the Bishop movement.
 */
public class BishopMoveStrategy extends GenericMoveStrategy {

    public BishopMoveStrategy(MoveDeterminant moveDeterminant) {
        super(moveDeterminant);
    }

    @Override
    protected List<Position> getPositions(Direction direction, Position currentPosition, Player currentPlayer, GameState gameState) {
        List<Position> positions = new LinkedList<>();
        Position nextPosition = currentPosition;

        while (true) {
            switch (direction) {
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
                default:
                    nextPosition = Position.EMPTY;
                    break;
            }

            if (Position.EMPTY != nextPosition) {
                positions.add(nextPosition);
            } else {
                break;
            }
        }

        return positions;
    }

}
