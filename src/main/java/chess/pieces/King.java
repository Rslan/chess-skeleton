package chess.pieces;

import chess.Player;
import chess.moves.KingMoveStrategy;
import chess.moves.MoveDeterminant;

/**
 * The King class
 */
public class King extends Piece {

    public King(Player owner) {
        super(owner, new KingMoveStrategy(new MoveDeterminant()));
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'k';
    }
}
