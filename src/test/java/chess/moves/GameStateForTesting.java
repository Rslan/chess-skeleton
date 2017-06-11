package chess.moves;

import chess.GameState;
import chess.Position;
import chess.pieces.Piece;

import java.util.Map;

public class GameStateForTesting extends GameState {

    @Override
    public void setPieces(Map<Position, Piece> pieces) {
        super.setPieces(pieces);
    }

}
