package chess.moves;

import chess.Player;
import chess.Position;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static chess.Position.getNewPosition;
import static chess.moves.TestUtils.createTestBoard;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class RookMoveStrategyTest {

    private RookMoveStrategy moveStrategy;
    private GameStateForTesting gameState;

    @Before
    public void setUp() {
        gameState = new GameStateForTesting();
        gameState.setPieces(createTestBoard());
        moveStrategy = new RookMoveStrategy(new MoveDeterminant());
    }

    @Test
    public void getPositionsForForwardMovement() {
        List<Position> actualPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.FORWARD,
                                                                   getNewPosition("a8"),
                                                                   Player.Black,
                                                                   gameState);
        assertTrue(actualPositions.isEmpty());
    }

    @Test
    public void getPositionsForBackwardMovement() {
        List<Position> actualPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.BACKWARD,
                                                                   getNewPosition("a8"),
                                                                   Player.Black,
                                                                   gameState);
        List<Position> expectedPositions = new LinkedList<>();
        expectedPositions.add(getNewPosition("a7"));
        expectedPositions.add(getNewPosition("a6"));
        expectedPositions.add(getNewPosition("a5"));
        expectedPositions.add(getNewPosition("a4"));
        expectedPositions.add(getNewPosition("a3"));
        expectedPositions.add(getNewPosition("a2"));
        expectedPositions.add(getNewPosition("a1"));

        assertThat(actualPositions.size(), is(expectedPositions.size()));
        assertTrue(actualPositions.containsAll(expectedPositions));
    }

    @Test
    public void getPositionsForLeftMovement() {
        List<Position> actualPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.LEFT,
                                                                   getNewPosition("a8"),
                                                                   Player.Black,
                                                                   gameState);
        assertTrue(actualPositions.isEmpty());
    }

    @Test
    public void getPositionsForRightMovement() {
        List<Position> actualPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.RIGHT,
                                                                   getNewPosition("a8"),
                                                                   Player.Black,
                                                                   gameState);
        List<Position> expectedPositions = new LinkedList<>();
        expectedPositions.add(getNewPosition("b8"));
        expectedPositions.add(getNewPosition("c8"));
        expectedPositions.add(getNewPosition("d8"));
        expectedPositions.add(getNewPosition("e8"));
        expectedPositions.add(getNewPosition("f8"));
        expectedPositions.add(getNewPosition("g8"));
        expectedPositions.add(getNewPosition("h8"));

        assertThat(actualPositions.size(), is(expectedPositions.size()));
        assertTrue(actualPositions.containsAll(expectedPositions));
    }

    @Test
    public void getPossibleMoves() {
        Position currentPosition = getNewPosition("f4");

        List<Move> actualMoves = moveStrategy.getPossibleMoves(gameState, currentPosition, Player.White);

        List<Move> expectedMoves = new LinkedList<>();
        expectedMoves.add(new Move(currentPosition, getNewPosition("f5")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("f6")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("f7")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("f8")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("g4")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("h4")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("f3")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("f2")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("e4")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("d4")));

        assertThat(actualMoves.size(), is(expectedMoves.size()));
        assertTrue(actualMoves.containsAll(expectedMoves));
    }
}