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

public class PawnMoveStrategyTest {

    private PawnMoveStrategy moveStrategy;
    private GameStateForTesting gameState;

    @Before
    public void setUp() {
        gameState = new GameStateForTesting();
        gameState.setPieces(createTestBoard());
        moveStrategy = new PawnMoveStrategy(new MoveDeterminant());
    }

    @Test
    public void getPositionsForForwardMovement() {
        List<Position> forwardPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.FORWARD,
                                                                    getNewPosition("a2"),
                                                                    Player.White,
                                                                    gameState);
        List<Position> expectedPositions = new LinkedList<>();
        expectedPositions.add(getNewPosition("a3"));
        expectedPositions.add(getNewPosition("a4"));

        assertThat(forwardPositions.size(), is(expectedPositions.size()));
        assertTrue(forwardPositions.containsAll(expectedPositions));
    }

    @Test
    public void getPositionsForForwardRightMovement() {
        List<Position> forwardPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.FORWARD_RIGHT,
                                                                    getNewPosition("d5"),
                                                                    Player.Black,
                                                                    gameState);

        List<Position> expectedPositions = new LinkedList<>();
        expectedPositions.add(getNewPosition("c4"));

        assertThat(forwardPositions.size(), is(expectedPositions.size()));
        assertTrue(forwardPositions.containsAll(expectedPositions));
    }

    @Test
    public void getPositionsForForwardLeftMovement() {
        List<Position> forwardPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.FORWARD_LEFT,
                                                                    getNewPosition("c4"),
                                                                    Player.White,
                                                                    gameState);

        assertTrue(forwardPositions.isEmpty());
    }

    @Test
    public void getPossibleMoves() {
        Position currentPosition = getNewPosition("c4");

        List<Move> actualMoves = moveStrategy.getPossibleMoves(gameState, currentPosition, Player.White);

        List<Move> expectedMoves = new LinkedList<>();
        expectedMoves.add(new Move(currentPosition, getNewPosition("c5")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("d5")));

        assertThat(actualMoves.size(), is(expectedMoves.size()));
        assertTrue(actualMoves.containsAll(expectedMoves));
    }
}