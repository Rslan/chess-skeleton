package chess.pieces;

import chess.GameState;
import chess.Player;
import chess.Position;

import java.util.List;

/**
 * The 'Bishop' class
 */
public class Bishop extends Piece {
    private final int offset = OFFSET_UNBOUNDED;

    public Bishop(Player owner) {
        super(owner);
    }

    @Override
    public List<Position> getValidMovements(GameState state, Position position, Player player) {
        return getDiagonalMovements(player, state, position, offset);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return Type.BISHOP.getId();
    }
}
