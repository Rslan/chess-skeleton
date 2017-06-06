package chess.pieces;

import chess.GameState;
import chess.Player;
import chess.Position;

import java.util.List;

/**
 * The 'Rook' class
 */
public class Rook extends Piece {
    private final int offset = OFFSET_UNBOUNDED;

    public Rook(Player owner) {
        super(owner);
    }

    @Override
    public List<Position> getValidMovements(GameState state, Position position, Player player) {
        return getLineMovements(player, state, position, offset);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return Type.ROOK.getId();
    }
}
