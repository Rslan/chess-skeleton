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

public class KnightMoveStrategyTest {

    private KnightMoveStrategy moveStrategy;
    private GameStateForTesting gameState;

    @Before
    public void setUp() {
        gameState = new GameStateForTesting();
        gameState.setPieces(createTestBoard());
        moveStrategy = new KnightMoveStrategy(new MoveDeterminant());
    }

    @Test
    public void getPositions() {
        List<Position> whiteKnightPositions = moveStrategy.getPositions(GenericMoveStrategy.Direction.FORWARD_LEFT,
                                                                        getNewPosition("c2"),
                                                                        Player.White,
                                                                        gameState);
        List<Position> expectedPositions = createWhiteKnightPositions();

        assertThat(whiteKnightPositions.size(), is(expectedPositions.size()));
        assertTrue(whiteKnightPositions.containsAll(expectedPositions));
    }

    @Test
    public void getPossibleMoves() {
        Position currentPosition = getNewPosition("c2");

        List<Move> actualMoves = moveStrategy.getPossibleMoves(gameState, currentPosition, Player.White);

        List<Move> expectedMoves = new LinkedList<>();
        expectedMoves.add(new Move(currentPosition, getNewPosition("b4")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("a3")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("d4")));
        expectedMoves.add(new Move(currentPosition, getNewPosition("e1")));

        assertThat(actualMoves.size(), is(expectedMoves.size()));
        assertTrue(actualMoves.containsAll(expectedMoves));
    }

    private List<Position> createWhiteKnightPositions() {
        List<Position> positions = new LinkedList<>();
        positions.add(getNewPosition("a3"));
        positions.add(getNewPosition("b4"));
        return positions;
    }

}