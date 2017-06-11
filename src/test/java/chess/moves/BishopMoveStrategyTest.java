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

public class BishopMoveStrategyTest {

    private BishopMoveStrategy moveStrategy;
    private GameStateForTesting gameState;

    @Before
    public void setUp() {
        gameState = new GameStateForTesting();
        gameState.setPieces(createTestBoard());
        moveStrategy = new BishopMoveStrategy(new MoveDeterminant());
    }

    @Test
    public void getPositionsForForwardLeftMovement() {
        List<Position> actualPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.FORWARD_LEFT,
                                                                   getNewPosition("f1"),
                                                                   Player.White,
                                                                   gameState);
        List<Position> expectedPositions = new LinkedList<>();
        expectedPositions.add(getNewPosition("e2"));
        expectedPositions.add(getNewPosition("d3"));
        expectedPositions.add(getNewPosition("c4"));
        expectedPositions.add(getNewPosition("b5"));
        expectedPositions.add(getNewPosition("a6"));

        assertThat(actualPositions.size(), is(expectedPositions.size()));
        assertTrue(actualPositions.containsAll(expectedPositions));
    }

    @Test
    public void getPositionsForForwardRightMovement() {
        List<Position> actualPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.FORWARD_RIGHT,
                                                                   getNewPosition("f1"),
                                                                   Player.White,
                                                                   gameState);
        List<Position> expectedPositions = new LinkedList<>();
        expectedPositions.add(getNewPosition("g2"));
        expectedPositions.add(getNewPosition("h3"));

        assertThat(actualPositions.size(), is(expectedPositions.size()));
        assertTrue(actualPositions.containsAll(expectedPositions));
    }

    @Test
    public void getPositionsForBackwardLeftMovement() {
        List<Position> actualPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.BACKWARD_LEFT,
                                                                   getNewPosition("f1"),
                                                                   Player.White,
                                                                   gameState);
        assertTrue(actualPositions.isEmpty());
    }

    @Test
    public void getPositionsForBackwardRightMovement() {
        List<Position> actualPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.BACKWARD_RIGHT,
                                                                   getNewPosition("f1"),
                                                                   Player.White,
                                                                   gameState);
        assertTrue(actualPositions.isEmpty());
    }

    @Test
    public void getPossibleMoves() {
        Position currentPosition = getNewPosition("f1");
        List<Move> actualMoves = moveStrategy.getPossibleMoves(gameState, currentPosition, Player.White);

        List<Move> expectedMoves = new LinkedList<>();
        expectedMoves.add(new Move(currentPosition, getNewPosition("e2")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("d3")));

        assertThat(actualMoves.size(), is(expectedMoves.size()));
        assertTrue(actualMoves.containsAll(expectedMoves));
    }
}