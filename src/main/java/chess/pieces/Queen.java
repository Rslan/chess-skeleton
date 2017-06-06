package chess.pieces;

import chess.GameState;
import chess.Player;
import chess.Position;

import java.util.List;

/**
 * The Queen
 */
public class Queen extends Piece {
    private final int offset = OFFSET_UNBOUNDED;

    public Queen(Player owner) {
        super(owner);
    }

    @Override
    public List<Position> getValidMovements(GameState state, Position position, Player player) {
        List<Position> movements = getLineMovements(player, state, position, offset);
        movements.addAll(getDiagonalMovements(player, state, position, offset));

        return movements;
    }

    @Override
    protected char getIdentifyingCharacter() {
        return Type.QUEEN.getId();
    }
}
