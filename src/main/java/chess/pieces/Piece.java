package chess.pieces;

import chess.GameState;
import chess.Player;
import chess.Position;
import chess.moves.Move;
import chess.moves.MoveStrategy;

import java.util.List;

/**
 * A base class for chess pieces
 */
public abstract class Piece {
    private final Player owner;
    private final MoveStrategy moveStrategy;

    protected Piece(Player owner, MoveStrategy moveStrategy) {
        this.owner = owner;
        this.moveStrategy = moveStrategy;
    }

    public char getIdentifier() {
        char id = getIdentifyingCharacter();
        if (owner.equals(Player.White)) {
            return Character.toLowerCase(id);
        } else {
            return Character.toUpperCase(id);
        }
    }

    public List<Move> getPossibleMoves(GameState gameState, Position currentPosition) {
        return moveStrategy.getPossibleMoves(gameState, currentPosition, owner);
    }

    public Player getOwner() {
        return owner;
    }

    protected abstract char getIdentifyingCharacter();
}
