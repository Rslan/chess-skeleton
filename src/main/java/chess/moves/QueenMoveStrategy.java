package chess.moves;

import chess.GameState;
import chess.Player;
import chess.Position;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A strategy of the Queen movement.
 */
public class QueenMoveStrategy extends GenericMoveStrategy {

    public QueenMoveStrategy(MoveDeterminant moveDeterminant) {
        super(moveDeterminant);
    }

    @Override
    protected List<Position> getPositions(Direction direction, Position currentPosition, Player currentPlayer, GameState gameState) {
        return Stream.concat(new RookMoveStrategy(moveDeterminant).getPositions(direction, currentPosition, currentPlayer, gameState)
                                                                  .stream(),
                             new BishopMoveStrategy(moveDeterminant).getPositions(direction, currentPosition, currentPlayer, gameState)
                                                                    .stream())
                     .collect(Collectors.toList());
    }

}
