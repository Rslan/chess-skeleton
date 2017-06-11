package chess.moves;

import chess.GameState;
import chess.Player;
import chess.Position;

import java.util.LinkedList;
import java.util.List;

/**
 * A strategy of the Rook movement.
 */
public class RookMoveStrategy extends GenericMoveStrategy {

    public RookMoveStrategy(MoveDeterminant moveDeterminant) {
        super(moveDeterminant);
    }

    @Override
    protected List<Position> getPositions(Direction direction, Position currentPosition, Player currentPlayer, GameState gameState) {
        List<Position> positions = new LinkedList<>();
        Position nextPosition = currentPosition;

        while (true) {
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
