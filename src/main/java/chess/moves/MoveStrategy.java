package chess.moves;

import chess.GameState;
import chess.Player;
import chess.Position;

import java.util.List;

/**
 * A strategy of a Piece movement.
 */
public interface MoveStrategy {

    List<Move> getPossibleMoves(GameState gameState, Position currentPosition, Player currentPlayer);
}
