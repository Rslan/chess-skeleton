package chess.pieces;

import chess.GameState;
import chess.Player;
import chess.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * The Knight class
 */
public class Knight extends Piece {
    public Knight(Player owner) {
        super(owner);
    }

    @Override
    public List<Position> getValidMovements(GameState state, Position position, Player player) {
        List<Position> movements = new ArrayList<>();

        char col = position.getColumn();
        int row = position.getRow();

        checkAndSaveIfMovement(player, state, movements, new Position((char)(col + 2), row + 1));
        checkAndSaveIfMovement(player, state, movements, new Position((char)(col + 2), row - 1));

        checkAndSaveIfMovement(player, state, movements, new Position((char)(col - 2), row + 1));
        checkAndSaveIfMovement(player, state, movements, new Position((char)(col - 2), row - 1));

        checkAndSaveIfMovement(player, state, movements, new Position((char) (col + 1), row + 2));
        checkAndSaveIfMovement(player, state, movements, new Position((char) (col + 1), row - 2));

        checkAndSaveIfMovement(player, state, movements, new Position((char) (col - 1), row + 2));
        checkAndSaveIfMovement(player, state, movements, new Position((char) (col - 1), row - 2));

        return movements;
    }

    @Override
    protected char getIdentifyingCharacter() {
        return Type.KNIGHT.getId();
    }
}
