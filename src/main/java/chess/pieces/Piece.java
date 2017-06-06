package chess.pieces;

import chess.GameState;
import chess.Player;
import chess.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A base class for chess pieces
 */
public abstract class Piece implements Serializable {
    public enum Type {
        PAWN('p'), KNIGHT('n'), BISHOP('b'), ROOK('r'), QUEEN('q'), KING('k');
        private final char id;

        Type(char id) {
            this.id = id;
        }

        public char getId() {
            return id;
        }
    }

    public static final int OFFSET_ONE = 1;
    public static final int OFFSET_TWO = 2;
    public static final int OFFSET_UNBOUNDED = -1;

    private final Player owner;

    protected Piece(Player owner) {
        this.owner = owner;
    }

    public char getIdentifier() {
        char id = getIdentifyingCharacter();
        if (owner.equals(Player.White)) {
            return Character.toLowerCase(id);
        } else {
            return Character.toUpperCase(id);
        }
    }

    private char getLowerIdentifier() {
        return getIdentifyingCharacter();
    }

    public Player getOwner() {
        return owner;
    }

    public abstract List<Position> getValidMovements(GameState state, Position position, Player player);

    public void saveDirectionMovements(Player player, GameState state, Direction direction, List<Position> movement,
                                       Position position, int offset, boolean canAttack) {
        if (offset == 0 || !Position.isOnBoard(position.getColumn(), position.getRow())) {
            return;
        }

        Position testPosition = getDirectionStepPosition(direction, position);

        if (!checkAndSaveIfMovement(player, state, movement, testPosition, canAttack)) {
            return;
        }
        saveDirectionMovements(player, state, direction, movement, testPosition, offset - 1, canAttack);
    }

    private Position getDirectionStepPosition(Direction direction, Position position) {
        char col = position.getColumn();
        int row = position.getRow();

        if (direction == Direction.LEFT) {
            col--;
        } else if (direction == Direction.RIGHT) {
            col++;
        } else if (direction == Direction.UP) {
            row++;
        } else if (direction == Direction.DOWN) {
            row--;
        } else if (direction == Direction.RIGHT_UP) {
            col++;
            row++;
        } else if (direction == Direction.RIGHT_DOWN) {
            col++;
            row--;
        } else if (direction == Direction.LEFT_UP) {
            col--;
            row++;
        } else if (direction == Direction.LEFT_DOWN) {
            col--;
            row--;
        }

        return new Position(col, row);
    }

    private void saveDirectionMovements(Player player, List<Position> movement, GameState state, Direction direction, Position starting, int offset) {
        saveDirectionMovements(player, state, direction, movement, starting, offset, true);
    }

    public void checkAndSaveIfMovement(Player player, GameState state, List<Position> movement, Position pos) {
        checkAndSaveIfMovement(player, state, movement, pos, true);
    }

    public List<Position> getDiagonalMovements(Player player, GameState state, Position position, int offset) {
        List<Position> movement = new ArrayList<>();

        saveDirectionMovements(player, movement, state, Direction.LEFT_UP, position, offset);
        saveDirectionMovements(player, movement, state, Direction.RIGHT_UP, position, offset);
        saveDirectionMovements(player, movement, state, Direction.LEFT_DOWN, position, offset);
        saveDirectionMovements(player, movement, state, Direction.RIGHT_DOWN, position, offset);

        return movement;
    }

    public List<Position> getLineMovements(Player player, GameState state, Position position, int offset) {
        List<Position> movement = new ArrayList<>();

        saveDirectionMovements(player, movement, state, Direction.LEFT, position, offset);
        saveDirectionMovements(player, movement, state, Direction.RIGHT, position, offset);
        saveDirectionMovements(player, movement, state, Direction.UP, position, offset);
        saveDirectionMovements(player, movement, state, Direction.DOWN, position, offset);

        return movement;
    }

    public boolean checkAndSaveIfMovement(Player player, GameState state, List<Position> movement,
                                          Position toPos, boolean canAttack) {
        if (!Position.isOnBoard(toPos.getColumn(), toPos.getRow())) {
            return false;
        }

        Piece piece = state.getPieceAt(toPos);
        if (piece != null) {
            if (canAttack && piece.getOwner() != player) {
                movement.add(toPos);
            }
            return false;
        }
        movement.add(toPos);

        return true;
    }

    protected abstract char getIdentifyingCharacter();

    public boolean isKing() {
        return getLowerIdentifier() == Piece.Type.KING.getId();
    }

}
