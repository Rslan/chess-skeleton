package chess.pieces;

import chess.Player;
import chess.moves.KnightMoveStrategy;
import chess.moves.MoveDeterminant;

/**
 * The Knight class
 */
public class Knight extends Piece {

    public Knight(Player owner) {
        super(owner, new KnightMoveStrategy(new MoveDeterminant()));
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'n';
    }
}
