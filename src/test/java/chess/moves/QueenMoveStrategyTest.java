package chess.moves;

import chess.Player;
import chess.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static chess.Position.getNewPosition;
import static chess.moves.TestUtils.createTestBoard;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class QueenMoveStrategyTest {

    private QueenMoveStrategy moveStrategy;
    private GameStateForTesting gameState;

    @Before
    public void setUp() {
        gameState = new GameStateForTesting();
        gameState.setPieces(createTestBoard());
        moveStrategy = new QueenMoveStrategy(new MoveDeterminant());
    }

    @Test
    public void getPositionsForForwardMovement() {
        List<Position> actualPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.FORWARD,
                                                                   getNewPosition("g2"),
                                                                   Player.White,
                                                                   gameState);
        List<Position> expectedPositions = new LinkedList<>();
        expectedPositions.add(getNewPosition("g3"));
        expectedPositions.add(getNewPosition("g4"));
        expectedPositions.add(getNewPosition("g5"));
        expectedPositions.add(getNewPosition("g6"));
        expectedPositions.add(getNewPosition("g7"));
        expectedPositions.add(getNewPosition("g8"));

        assertThat(actualPositions.size(), is(expectedPositions.size()));
        assertTrue(actualPositions.containsAll(expectedPositions));
    }

    @Test
    public void getPositionsForBackwardMovement() {
        List<Position> actualPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.BACKWARD,
                                                                   getNewPosition("g2"),
                                                                   Player.White,
                                                                   gameState);
        List<Position> expectedPositions = new LinkedList<>();
        expectedPositions.add(getNewPosition("g1"));

        assertThat(actualPositions.size(), is(expectedPositions.size()));
        assertTrue(actualPositions.containsAll(expectedPositions));
    }

    @Test
    public void getPositionsForLeftMovement() {
        List<Position> actualPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.LEFT,
                                                                   getNewPosition("g2"),
                                                                   Player.White,
                                                                   gameState);
        List<Position> expectedPositions = new LinkedList<>();
        expectedPositions.add(getNewPosition("f2"));
        expectedPositions.add(getNewPosition("e2"));
        expectedPositions.add(getNewPosition("d2"));
        expectedPositions.add(getNewPosition("c2"));
        expectedPositions.add(getNewPosition("b2"));
        expectedPositions.add(getNewPosition("a2"));

        assertThat(actualPositions.size(), is(expectedPositions.size()));
        assertTrue(actualPositions.containsAll(expectedPositions));
    }

    @Test
    public void getPositionsForRightMovement() {
        List<Position> actualPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.RIGHT,
                                                                   getNewPosition("g2"),
                                                                   Player.White,
                                                                   gameState);
        List<Position> expectedPositions = new LinkedList<>();
        expectedPositions.add(getNewPosition("h2"));

        assertThat(actualPositions.size(), is(expectedPositions.size()));
        assertTrue(actualPositions.containsAll(expectedPositions));
    }

    @Test
    public void getPositionsForForwardLeftMovement() {
        List<Position> actualPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.FORWARD_LEFT,
                                                                   getNewPosition("g2"),
                                                                   Player.White,
                                                                   gameState);
        List<Position> expectedPositions = new LinkedList<>();
        expectedPositions.add(getNewPosition("f3"));
        expectedPositions.add(getNewPosition("e4"));
        expectedPositions.add(getNewPosition("d5"));
        expectedPositions.add(getNewPosition("c6"));
        expectedPositions.add(getNewPosition("b7"));
        expectedPositions.add(getNewPosition("a8"));

        assertThat(actualPositions.size(), is(expectedPositions.size()));
        assertTrue(actualPositions.containsAll(expectedPositions));
    }

    @Test
    public void getPositionsForForwardRightMovement() {
        List<Position> actualPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.FORWARD_RIGHT,
                                                                   getNewPosition("g2"),
                                                                   Player.White,
                                                                   gameState);
        List<Position> expectedPositions = new LinkedList<>();
        expectedPositions.add(getNewPosition("h3"));

        assertThat(actualPositions.size(), is(expectedPositions.size()));
        assertTrue(actualPositions.containsAll(expectedPositions));
    }

    @Test
    public void getPositionsForBackwardLeftMovement() {
        List<Position> actualPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.BACKWARD_LEFT,
                                                                   getNewPosition("g2"),
                                                                   Player.White,
                                                                   gameState);
        List<Position> expectedPositions = new LinkedList<>();
        expectedPositions.add(getNewPosition("f1"));

        assertThat(actualPositions.size(), is(expectedPositions.size()));
        assertTrue(actualPositions.containsAll(expectedPositions));
    }

    @Test
    public void getPositionsForBackwardRightMovement() {
        List<Position> actualPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.BACKWARD_RIGHT,
                                                                   getNewPosition("g2"),
                                                                   Player.White,
                                                                   gameState);
        List<Position> expectedPositions = new LinkedList<>();
        expectedPositions.add(getNewPosition("h1"));

        assertThat(actualPositions.size(), is(expectedPositions.size()));
        assertTrue(actualPositions.containsAll(expectedPositions));
    }

    @Test
    public void getPossibleMoves() {
        Position currentPosition = getNewPosition("h4");

        List<Move> actualMoves = moveStrategy.getPossibleMoves(gameState, currentPosition, Player.Black);

        List<Move> expectedMoves = new LinkedList<>();
        expectedMoves.add(new Move(currentPosition, getNewPosition("h5")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("h6")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("h7")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("h8")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("h3")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("h2")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("h1")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("g4")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("f4")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("g5")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("f6")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("e7")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("d8")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("g3")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("f2")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("e1")));

        assertThat(actualMoves.size(), is(expectedMoves.size()));
        assertTrue(actualMoves.containsAll(expectedMoves));
    }
}