package chess.moves;

import chess.GameState;
import chess.Player;
import chess.Position;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import static chess.Position.getNewPosition;
import static chess.moves.TestUtils.createTestBoard;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GenericMoveStrategyTest {

    private GenericMoveStrategy genericMoveStrategy;
    private GameStateForTesting gameState;

    @Before
    public void setUp() {
        initMocks(this);
        gameState = new GameStateForTesting();
        gameState.setPieces(createTestBoard());

        genericMoveStrategy = new GenericMoveStrategy(new MoveDeterminant()) {
            @Override
            protected List<Position> getPositions(Direction direction, Position currentPosition,
                                                  Player currentPlayer, GameState gameState) {
                List<Position> whiteKnightPositions = new LinkedList<>();
                switch (direction) {
                    case FORWARD_LEFT:
                        whiteKnightPositions.add(getNewPosition("a3"));
                        whiteKnightPositions.add(getNewPosition("b4"));
                        break;
                    case FORWARD_RIGHT:
                        whiteKnightPositions.add(getNewPosition("d4"));
                        whiteKnightPositions.add(getNewPosition("e3"));
                        break;
                    case BACKWARD_RIGHT:
                        whiteKnightPositions.add(getNewPosition("e1"));
                        break;
                }
                return whiteKnightPositions;
            }
        };
    }

    @Test
    public void getPossibleMoves() {
        List<Move> whiteKnightMoves = genericMoveStrategy.getPossibleMoves(gameState, getNewPosition("c2"), Player.White);

        List<Move> expectedWhiteKnightMoves = createWhiteKnightMoves();

        assertThat(whiteKnightMoves.size(), is(expectedWhiteKnightMoves.size()));
        assertTrue(whiteKnightMoves.containsAll(expectedWhiteKnightMoves));
    }

    private static List<Move> createWhiteKnightMoves() {
        List<Move> moves = new LinkedList<>();
        moves.add(new Move(getNewPosition("c2"), getNewPosition("a3")));
        moves.add(new Move(getNewPosition("c2"), getNewPosition("b4")));
        moves.add(new Move(getNewPosition("c2"), getNewPosition("d4")));
        moves.add(new Move(getNewPosition("c2"), getNewPosition("e1")));
        return moves;
    }

}