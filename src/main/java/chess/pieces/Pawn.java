package chess.pieces;

import chess.Player;
import chess.moves.MoveDeterminant;
import chess.moves.PawnMoveStrategy;

/**
 * The Pawn
 */
public class Pawn extends Piece {

    public Pawn(Player owner) {
        super(owner, new PawnMoveStrategy(new MoveDeterminant()));
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'p';
    }
}
