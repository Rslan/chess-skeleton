package chess.moves;

import chess.GameState;
import chess.Player;
import chess.Position;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static chess.moves.MoveDeterminant.isOccupiedByRival;

/**
 * A strategy of the Pawn movement.
 */
public class PawnMoveStrategy extends GenericMoveStrategy {

    private static final int WHITE_INITIAL_POSITION = 2;
    private static final int BLACK_INITIAL_POSITION = 7;

    public PawnMoveStrategy(MoveDeterminant moveDeterminant) {
        super(moveDeterminant);
    }

    @Override
    protected List<Position> getPositions(Direction direction, Position currentPosition, Player currentPlayer, GameState gameState) {
        List<Position> positions = new LinkedList<>();

        switch (direction) {
            case FORWARD:
                positions.addAll(getPositionsForForwardMove(currentPosition, currentPlayer, gameState));
                break;
            case FORWARD_RIGHT:
                getPositionForForwardRightMove(currentPosition, currentPlayer, gameState).ifPresent(positions::add);
                break;
            case FORWARD_LEFT:
                getPositionForForwardLeftMove(currentPosition, currentPlayer, gameState).ifPresent(positions::add);
                break;
        }

        return positions;
    }

    private Optional<Position> getPositionForForwardLeftMove(Position currentPosition, Player currentPlayer, GameState gameState) {
        Position fwdLeft;

        if (Player.White == currentPlayer) {
            fwdLeft = moveDeterminant.decColIncRow(currentPosition);
        } else {
            fwdLeft = moveDeterminant.incColDecRow(currentPosition);
        }

        if (isOccupiedByRival(fwdLeft, gameState, gameState.getRival(currentPlayer))) {
            return Optional.of(fwdLeft);
        } else {
            return Optional.empty();
        }
    }

    private Optional<Position> getPositionForForwardRightMove(Position currentPosition, Player currentPlayer, GameState gameState) {
        Position fwdRight;

        if (Player.White == currentPlayer) {
            fwdRight = moveDeterminant.incColIncRow(currentPosition);
        } else {
            fwdRight = moveDeterminant.decColDecRow(currentPosition);
        }

        if (isOccupiedByRival(fwdRight, gameState, gameState.getRival(currentPlayer))) {
            return Optional.of(fwdRight);
        } else {
            return Optional.empty();
        }
    }

    private List<Position> getPositionsForForwardMove(Position currentPosition, Player currentPlayer, GameState gameState) {
        List<Position> fwdPositions = new LinkedList<>();

        if (Player.White == currentPlayer) {
            Position nextPosition = moveDeterminant.incRow(currentPosition);
            addNextForwardPosition(nextPosition, fwdPositions);
            nextPosition = moveDeterminant.incRow(nextPosition);
            if (currentPosition.getRow() == WHITE_INITIAL_POSITION
                    && !isOccupiedByRival(nextPosition, gameState, gameState.getRival(currentPlayer))) {
                addNextForwardPosition(nextPosition, fwdPositions);
            }
        } else {
            Position nextPosition = moveDeterminant.decRow(currentPosition);
            addNextForwardPosition(nextPosition, fwdPositions);
            nextPosition = moveDeterminant.decRow(nextPosition);
            if (currentPosition.getRow() == BLACK_INITIAL_POSITION
                    && !isOccupiedByRival(nextPosition, gameState, gameState.getRival(currentPlayer))) {
                addNextForwardPosition(nextPosition, fwdPositions);
            }
        }

        return fwdPositions;
    }

    private void addNextForwardPosition(Position nextPosition, List<Position> fwdPositions) {
        if (Position.EMPTY != nextPosition) {
            fwdPositions.add(nextPosition);
        }
    }

}
