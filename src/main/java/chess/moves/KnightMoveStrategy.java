package chess.moves;

import chess.GameState;
import chess.Player;
import chess.Position;

import java.util.LinkedList;
import java.util.List;

/**
 * A strategy of the Knight movement.
 */
public class KnightMoveStrategy extends GenericMoveStrategy {

    public KnightMoveStrategy(MoveDeterminant moveDeterminant) {
        super(moveDeterminant);
    }

    @Override
    protected List<Position> getPositions(Direction direction, Position currentPosition, Player currentPlayer, GameState gameState) {
        List<Position> positions = new LinkedList<>();
        Position nextPosition;

        switch (direction) {
            case FORWARD_LEFT:
                nextPosition = moveDeterminant.decCol(moveDeterminant.incRow(moveDeterminant.incRow(currentPosition)));
                if (Position.EMPTY != nextPosition) {
                    positions.add(nextPosition);
                }

                nextPosition = moveDeterminant.incRow(moveDeterminant.decCol(moveDeterminant.decCol(currentPosition)));
                if (Position.EMPTY != nextPosition) {
                    positions.add(nextPosition);
                }
                break;
            case FORWARD_RIGHT:
                nextPosition = moveDeterminant.incCol(moveDeterminant.incRow(moveDeterminant.incRow(currentPosition)));
                if (Position.EMPTY != nextPosition) {
                    positions.add(nextPosition);
                }

                nextPosition = moveDeterminant.incRow(moveDeterminant.incCol(moveDeterminant.incCol(currentPosition)));
                if (Position.EMPTY != nextPosition) {
                    positions.add(nextPosition);
                }
                break;
            case BACKWARD_LEFT:
                nextPosition = moveDeterminant.decCol(moveDeterminant.decRow(moveDeterminant.decRow(currentPosition)));
                if (Position.EMPTY != nextPosition) {
                    positions.add(nextPosition);
                }

                nextPosition = moveDeterminant.decRow(moveDeterminant.decCol(moveDeterminant.decCol(currentPosition)));
                if (Position.EMPTY != nextPosition) {
                    positions.add(nextPosition);
                }
                break;
            case BACKWARD_RIGHT:
                nextPosition = moveDeterminant.incCol(moveDeterminant.decRow(moveDeterminant.decRow(currentPosition)));
                if (Position.EMPTY != nextPosition) {
                    positions.add(nextPosition);
                }

                nextPosition = moveDeterminant.decRow(moveDeterminant.incCol(moveDeterminant.incCol(currentPosition)));
                if (Position.EMPTY != nextPosition) {
                    positions.add(nextPosition);
                }
                break;
        }

        return positions;
    }
}
