package chess;

import chess.moves.Move;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;
import org.junit.Before;
import org.junit.Test;

import static chess.Position.getNewPosition;
import static junit.framework.Assert.*;

/**
 * Basic unit tests for the GameState class
 */
public class GameStateTest {

    private GameState state;

    @Before
    public void setUp() {
        state = new GameState();
    }

    @Test
    public void testStartsEmpty() {
        // Make sure all the positions are empty
        for (char col = Position.MIN_COLUMN; col <= Position.MAX_COLUMN; col++) {
            for (int row = Position.MIN_ROW; row <= Position.MAX_ROW; row++) {
                assertNull("All pieces should be empty", state.getPieceAt(String.valueOf(col) + row));
            }
        }
    }

    @Test
    public void testInitialGame() {
        // Start the game
        state.reset();

        // White should be the first player
        Player current = state.getCurrentPlayer();
        assertEquals("The initial player should be White", Player.White, current);

        // Spot check a few pieces
        Piece whiteRook = state.getPieceAt("a1");
        assertTrue("A rook should be at a1", whiteRook instanceof Rook);
        assertEquals("The rook at a1 should be owned by White", Player.White, whiteRook.getOwner());


        Piece blackQueen = state.getPieceAt("d8");
        assertTrue("A queen should be at d8", blackQueen instanceof Queen);
        assertEquals("The queen at d8 should be owned by Black", Player.Black, blackQueen.getOwner());
    }

    @Test
    public void getCurrentPlayerKingPosition() {
        state.reset();

        Position actualPosition = state.getCurrentPlayerKingPosition();

        Position expected = getNewPosition("e1");
        assertEquals(expected, actualPosition);
    }

    @Test
    public void movePiece() {
        state.reset();

        Piece pawn = state.getPieceAt("a2");

        state.movePiece(new Move(getNewPosition("a2"), getNewPosition("a4")));

        assertEquals(pawn, state.getPieceAt("a4"));
        assertNull(state.getPieceAt("a2"));
        assertEquals(Player.Black, state.getCurrentPlayer());
    }

    @Test
    public void getCurrentPlayersRival() {
        state.reset();

        assertEquals(Player.Black, state.getRival());
    }

    @Test
    public void getBlackPlayerRival() {
        state.reset();

        assertEquals(Player.White, state.getRival(Player.Black));
    }
}
