package chess;

import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

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
    public void testPawnMovement() {
        state.reset();

        assertEquals(MoveResult.OK, state.movePiece("e2", "e4"));
        assertEquals(MoveResult.OK, state.movePiece("e7", "e5"));
        TestUtil.showBoard(state);
    }

    @Test
    public void testIllegalPawnMovementObstacle() {
        state.reset();

        state.movePiece("e2", "e4");
        state.movePiece("e7", "e5");
        assertEquals(MoveResult.ILLEGAL, state.movePiece("e4", "e5"));
        TestUtil.showBoard(state);
    }

    @Test
    public void testIllegalPawnMoveUnreacheableField() {
        state.reset();

        state.movePiece("e2", "e6");
        state.movePiece("e7", "e4");
        assertEquals(MoveResult.ILLEGAL, state.movePiece("e4", "e5"));
        TestUtil.showBoard(state);
    }

    @Test
    public void testIllegalPawnAttackOwnArmy() {
        state.reset();

        state.movePiece("e2", "e3");
        state.movePiece("e7", "e5");
        state.movePiece("f2", "f4");
        state.movePiece("f7", "f6");
        assertEquals(MoveResult.ILLEGAL, state.movePiece("f3", "f4"));
        assertEquals(MoveResult.ILLEGAL, state.movePiece("f6", "f7"));
        TestUtil.showBoard(state);
    }

    @Test
    public void testIllegalPawnAttackUnreacheableField() {
        state.reset();

        assertEquals(MoveResult.ILLEGAL, state.movePiece("e2", "f7"));
        TestUtil.showBoard(state);
    }

    @Test
    public void testKnightMove() {
        state.reset();

        assertEquals(MoveResult.OK, state.movePiece("g1", "h3"));
        TestUtil.showBoard(state);
    }

    @Test
    public void testKnightIllegalMove() {
        state.reset();
        assertEquals(MoveResult.ILLEGAL, state.movePiece("g1", "h4"));
        TestUtil.showBoard(state);
    }

    @Test
    public void testKnightIllegalMoveAttackOwnArmy() {
        state.reset();

        assertEquals(MoveResult.ILLEGAL, state.movePiece("g1", "e2"));
        TestUtil.showBoard(state);
    }

    @Test
    public void testKnightAttack() {
        state.reset();

        assertEquals(MoveResult.OK, state.movePiece("e2", "e4"));
        assertEquals(MoveResult.OK, state.movePiece("e7", "e5"));
        assertEquals(MoveResult.OK, state.movePiece("g1", "f3"));
        assertEquals(MoveResult.OK, state.movePiece("g8", "f6"));
        assertEquals(MoveResult.OK, state.movePiece("f3", "e5"));
        assertEquals(MoveResult.OK, state.movePiece("f6", "e4"));
        TestUtil.showBoard(state);
    }

    @Test
    public void testBishopAndRook() {
        state.reset();

        assertEquals(MoveResult.OK, state.movePiece("g2", "g4"));
        assertEquals(MoveResult.OK, state.movePiece("a7", "a5"));
        assertEquals(MoveResult.OK, state.movePiece("g4", "g5"));
        assertEquals(MoveResult.OK, state.movePiece("a8", "a6"));
        assertEquals(MoveResult.OK, state.movePiece("f1", "h3"));
        assertEquals(MoveResult.OK, state.movePiece("e7", "e6"));

        // Bishop attack
        assertEquals(MoveResult.OK, state.movePiece("h3", "e6"));
        TestUtil.showBoard(state);
        // illegal Rook attack
        assertEquals(MoveResult.ILLEGAL, state.movePiece("a6", "a5"));
    }


    @Test
    public void testFoolMate() {
        state.reset();

        assertEquals(MoveResult.OK, state.movePiece("f2", "f3"));
        assertFalse(state.isGameOver());
        assertEquals(MoveResult.OK, state.movePiece("e7", "e6"));
        assertFalse(state.isGameOver());
        assertEquals(MoveResult.OK, state.movePiece("g2", "g4"));
        assertFalse(state.isGameOver());

        TestUtil.showBoard(state);

        assertEquals(MoveResult.CHECK_MATE, state.movePiece("d8", "h4"));
        assertTrue(state.isGameOver());
    }

    @Test
    public void testCheck() {
        state.reset();

        state.movePiece("c2", "c3");
        state.movePiece("d7", "d6");
        MoveResult actual = state.movePiece("d1", "a4");

        TestUtil.showBoard(state);
        
        assertEquals(MoveResult.OK, actual);

        TestUtil.showBoard(state);

        assertFalse(state.isGameOver());
    }
}
