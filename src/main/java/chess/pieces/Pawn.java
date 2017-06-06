package chess.pieces;

import chess.GameState;
import chess.Player;
import chess.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * The Pawn
 */
public class Pawn extends Piece {
    public Pawn(Player owner) {
        super(owner);
    }

    @Override
    public List<Position> getValidMovements(GameState state, Position position, Player player) {
        List<Position> movements = new ArrayList<>();
        Piece pawn = state.getPieceAt(position);

        char col = position.getColumn();
        int row = position.getRow();
        if (pawn.getOwner() == Player.White) {
            if (isPawnStartPosition(state, position)) {
                saveDirectionMovements(player, state, Direction.UP, movements, position, OFFSET_TWO, false);
            } else {
                saveDirectionMovements(player, state, Direction.UP, movements, position, OFFSET_ONE, false);
            }

            Position testPosition = new Position((char) (col + 1), row + 1);
            if (checkAttack(state, testPosition)) {
                movements.add(testPosition);
            }
            testPosition = new Position((char) (col - 1), row + 1);
            if (checkAttack(state, testPosition)) {
                movements.add(testPosition);
            }
        } else {
            if (isPawnStartPosition(state, position)) {
                saveDirectionMovements(player, state, Direction.DOWN, movements, position, OFFSET_TWO, false);
            } else {
                saveDirectionMovements(player, state, Direction.DOWN, movements, position, OFFSET_ONE, false);
            }

            Position testPosition = new Position((char) (col + 1), row - 1);
            if (checkAttack(state, testPosition)) {
                movements.add(testPosition);
            }
            testPosition = new Position((char) (col - 1), row - 1);
            if (checkAttack(state, testPosition)) {
                movements.add(testPosition);
            }
        }

        return movements;
    }

    private boolean isPawnStartPosition(GameState state, Position position) {
        final int SECOND_WHITE_ROW = Position.MIN_ROW + 1;
        final int SECOND_BLACK_ROW = Position.MAX_ROW - 1;

        boolean isWhiteStartPosition = state.getCurrentPlayer() == Player.White
                && position.getRow() == SECOND_WHITE_ROW;
        boolean isBlackStartPosition = (state.getCurrentPlayer() == Player.Black)
                && position.getRow() == SECOND_BLACK_ROW;

        return isWhiteStartPosition || isBlackStartPosition;
    }

    private boolean checkAttack(GameState state, Position testPosition) {
        Piece piece = state.getPieceAt(testPosition);
        return (piece != null && piece.getOwner() != getOwner());
    }

    @Override
    protected char getIdentifyingCharacter() {
        return Type.PAWN.getId();
    }
}
