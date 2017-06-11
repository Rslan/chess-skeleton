package chess;

import java.util.Optional;

/**
 * Describes a position on the Chess Board
 */
public class Position {
    public static final int MIN_ROW = 1;
    public static final int MAX_ROW = 8;
    public static final char MIN_COLUMN = 'a';
    public static final char MAX_COLUMN = 'h';
    public static final Position EMPTY = new Position('Z', -1);

    private int row;
    private char column;

    /**
     * Create a new Position object by parsing the string
     * @param colrow The column and row to use.  I.e. "a1", "h7", etc.
     */
    public static Position getNewPosition(String colrow) {
        return getNewPosition(colrow.toCharArray()[0], Character.digit(colrow.toCharArray()[1], 10));
    }

    /**
     * Create a new position object
     *
     * @param column The column
     * @param row The row
     */
    public static Position getNewPosition(char column, int row) {
        if (isColumnValid(column) && isRowValid(row)) {
            return new Position(column, row);
        } else {
            return EMPTY;
        }
    }

    private Position(char column, int row) {
        this.row = row;
        this.column = column;
    }

    private static boolean isColumnValid(char column) {
        return column >= MIN_COLUMN && column <= MAX_COLUMN;
    }

    private static boolean isRowValid(int row) {
        return row >= MIN_ROW && row <= MAX_ROW;
    }

    public int getRow() {
        return row;
    }

    public char getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (column != position.column) return false;

        //noinspection RedundantIfStatement
        if (row != position.row) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + (int) column;
        return result;
    }

    @Override
    public String toString() {
        return "" + column + row;
    }

}
