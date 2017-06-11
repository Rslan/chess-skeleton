package chess.moves;

import chess.GameState;
import chess.Player;
import chess.Position;
import chess.pieces.*;
import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static chess.Position.getNewPosition;
import static chess.moves.TestUtils.createTestBoard;
import static chess.moves.TestUtils.getWhitesPossibleMoves;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MoveDeterminantTest {

    private MoveDeterminant moveDeterminant;
    private GameStateForTesting gameState;

    @Before
    public void setUp() {
        gameState = new GameStateForTesting();
        gameState.setPieces(createTestBoard());
        moveDeterminant = new MoveDeterminant();
    }

    @Test
    public void getPossibleMoves() {
        List<Move> whitesPossibleMoves = moveDeterminant.getPossibleMoves(gameState);

        List<Move> expectedMoves = getWhitesPossibleMoves();
        assertThat(whitesPossibleMoves.size(), is(expectedMoves.size()));
        assertTrue(whitesPossibleMoves.containsAll(expectedMoves));
    }

    @Test
    public void getNewPositionWithIncrementedColumn() {
        Position current = getNewPosition("a1");

        Position newPosition = moveDeterminant.incCol(current);

        assertThat(newPosition, not(Position.EMPTY));
        assertEquals(getNewPosition("b1"), newPosition);
    }

    @Test
    public void returnsNoPositionAsEndOfBoardIsReached() {
        Position current = getNewPosition("h1");

        Position newPosition = moveDeterminant.incCol(current);

        assertThat(newPosition, is(Position.EMPTY));
    }

}