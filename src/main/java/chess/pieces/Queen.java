package chess.pieces;

import chess.Player;
import chess.moves.MoveDeterminant;
import chess.moves.QueenMoveStrategy;

/**
 * The Queen
 */
public class Queen extends Piece{

    public Queen(Player owner) {
        super(owner, new QueenMoveStrategy(new MoveDeterminant()));
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'q';
    }
}
