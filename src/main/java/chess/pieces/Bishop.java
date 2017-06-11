package chess.pieces;

import chess.Player;
import chess.moves.BishopMoveStrategy;
import chess.moves.MoveDeterminant;

/**
 * The 'Bishop' class
 */
public class Bishop extends Piece {

    public Bishop(Player owner) {
        super(owner, new BishopMoveStrategy(new MoveDeterminant()));
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'b';
    }
}
