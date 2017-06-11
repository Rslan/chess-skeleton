package chess.pieces;

import chess.Player;
import chess.moves.MoveDeterminant;
import chess.moves.RookMoveStrategy;

/**
 * The 'Rook' class
 */
public class Rook extends Piece {

    public Rook(Player owner) {
        super(owner, new RookMoveStrategy(new MoveDeterminant()));
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'r';
    }
}
