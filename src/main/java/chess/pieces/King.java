package chess.pieces;

import chess.GameState;
import chess.Player;
import chess.Position;

import java.util.List;

/**
 * The King class
 */
public class King extends Piece {
    private final int offset = OFFSET_ONE;

    public King(Player owner) {
        super(owner);
    }

    @Override
    public List<Position> getValidMovements(GameState state, Position position, Player player) {
        List<Position> kingMovements = getLineMovements(player, state, position, offset);
        kingMovements.addAll(getDiagonalMovements(player, state, position, offset));

        Position opKingPosition = state.getKingPosition(state.getOpponent());
        List<Position> opKingMovements = getLineMovements(player, state, opKingPosition, offset);
        opKingMovements.addAll(getDiagonalMovements(player, state, opKingPosition, offset));

        kingMovements.removeAll(state.getKingBeatedPositions(player, kingMovements, opKingMovements));
        return kingMovements;
    }

    @Override
    protected char getIdentifyingCharacter() {
        return Type.KING.getId();
    }
}
