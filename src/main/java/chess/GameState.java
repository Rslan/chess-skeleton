package chess;


import chess.pieces.*;

import java.io.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Class that represents the current state of the game.  Basically, what pieces are in which positions on the
 * board.
 */
public class GameState implements Serializable {

    private Player currentPlayer = Player.White;
    private Map<Position, Piece> positionToPieceMap;

    private boolean isGameOver;

    public GameState() {
        positionToPieceMap = new HashMap<>();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Call to initialize the game state into the starting positions
     */
    public void reset() {
        // White Pieces
        isGameOver = false;
        placePiece(new Rook(Player.White), new Position("a1"));
        placePiece(new Knight(Player.White), new Position("b1"));
        placePiece(new Bishop(Player.White), new Position("c1"));
        placePiece(new Queen(Player.White), new Position("d1"));
        placePiece(new King(Player.White), new Position("e1"));
        placePiece(new Bishop(Player.White), new Position("f1"));
        placePiece(new Knight(Player.White), new Position("g1"));
        placePiece(new Rook(Player.White), new Position("h1"));
        placePiece(new Pawn(Player.White), new Position("a2"));
        placePiece(new Pawn(Player.White), new Position("b2"));
        placePiece(new Pawn(Player.White), new Position("c2"));
        placePiece(new Pawn(Player.White), new Position("d2"));
        placePiece(new Pawn(Player.White), new Position("e2"));
        placePiece(new Pawn(Player.White), new Position("f2"));
        placePiece(new Pawn(Player.White), new Position("g2"));
        placePiece(new Pawn(Player.White), new Position("h2"));

        // Black Pieces
        placePiece(new Rook(Player.Black), new Position("a8"));
        placePiece(new Knight(Player.Black), new Position("b8"));
        placePiece(new Bishop(Player.Black), new Position("c8"));
        placePiece(new Queen(Player.Black), new Position("d8"));
        placePiece(new King(Player.Black), new Position("e8"));
        placePiece(new Bishop(Player.Black), new Position("f8"));
        placePiece(new Knight(Player.Black), new Position("g8"));
        placePiece(new Rook(Player.Black), new Position("h8"));
        placePiece(new Pawn(Player.Black), new Position("a7"));
        placePiece(new Pawn(Player.Black), new Position("b7"));
        placePiece(new Pawn(Player.Black), new Position("c7"));
        placePiece(new Pawn(Player.Black), new Position("d7"));
        placePiece(new Pawn(Player.Black), new Position("e7"));
        placePiece(new Pawn(Player.Black), new Position("f7"));
        placePiece(new Pawn(Player.Black), new Position("g7"));
        placePiece(new Pawn(Player.Black), new Position("h7"));
    }

    public Map<Position, List<Position>> getPossibleMovements() {
        return getPossibleMovements(currentPlayer);
    }

    public Map<Position, List<Position>> getPossibleMovements(Player player) {
        Map<Position, List<Position>> totalMoves = new HashMap<>();

        for (Map.Entry<Position, Piece> pair : positionToPieceMap.entrySet()) {
            Piece currentPiece = pair.getValue();
            if (currentPiece.getOwner() == player) {
                List<Position> possibleMoves = getValidMovementsWithoutCheck(player, currentPiece, pair.getKey());

                if (possibleMoves != null && !possibleMoves.isEmpty()) {
                    totalMoves.put(pair.getKey(), possibleMoves);
                }
            }
        }

        return totalMoves;
    }

    private List<Position> getValidMovementsWithoutCheck(Player player, Piece currentPiece, Position fromPos) {
        List<Position> possibleMoves = currentPiece.getValidMovements(this, fromPos, player);
        return possibleMoves.stream()
                .filter(toPos -> !isKingUnderAttack(player))
                .collect(Collectors.toList());
    }

    public MoveResult movePiece(String posFrom, String posTo) {
        Position from = new Position(posFrom);
        Position to = new Position(posTo);
        Piece piece = getPieceAt(from);

        if (piece != null && piece.getOwner() == currentPlayer) {
            List<Position> possibleMoves = getValidMovementsWithoutCheck(getCurrentPlayer(), piece, from);
            boolean myKingUnderAttackAfter = isMyKingUnderAttackAfter(from, to);
            if (possibleMoves.contains(to) && !myKingUnderAttackAfter) {
                positionToPieceMap.remove(from);
                positionToPieceMap.put(to, piece);

                Player opponent = getOpponent();

                Map<Position, List<Position>> possibleOpponentMovements = getPossibleMovements(opponent);
                boolean noOpponentMovements = possibleOpponentMovements.isEmpty();
                boolean opponentKingUnderAttack = isKingUnderAttack(opponent);

                isGameOver = (noOpponentMovements && opponentKingUnderAttack) || noOpponentMovements;
                if (noOpponentMovements && opponentKingUnderAttack) {
                    return MoveResult.CHECK_MATE;
                }

                if (noOpponentMovements) {
                    return MoveResult.DRAW;
                }

                setCurrentPlayer(opponent);

                if (opponentKingUnderAttack) {
                    return MoveResult.CHECK;
                }

                return MoveResult.OK;
            }
        }

        return MoveResult.ILLEGAL;
    }

    public Player getOpponent() {
        return getOpponent(this.currentPlayer);
    }

    public Player getOpponent(Player player) {
        return player == Player.Black ? Player.White : Player.Black;
    }

    public boolean isMyKingUnderAttackAfter(Position from, Position to) {
        return isKingUnderAttackAfter(currentPlayer, from, to);
    }

    public boolean isKingUnderAttackAfter(Player player, Position from, Position to) {
        GameState state = deepClone(this);
        state.setCurrentPlayer(getOpponent(player));
        Piece fromPiece = state.positionToPieceMap.remove(from);
        state.positionToPieceMap.put(to, fromPiece);
        return state.isKingUnderAttack(player);

    }

    public boolean isKingUnderAttack(Player player) {
        Position currentKingPosition = getKingPosition(player);
        for (Map.Entry<Position, Piece> movement : positionToPieceMap.entrySet()) {
            Player opponent = getOpponent(player);
            if (movement.getValue().getOwner() == opponent) {
                Piece piece = movement.getValue();
                List<Position> pieceValidMovements = piece.getValidMovements(this, movement.getKey(), opponent);
                if (pieceValidMovements.contains(currentKingPosition)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Set<Position> getKingBeatedPositions(Player player, List<Position> kingValidMovements, List<Position> opKingMovements) {
        Set<Position> resPositions = new HashSet<>();
        resPositions.retainAll(opKingMovements);

        for (Map.Entry<Position, Piece> movement : positionToPieceMap.entrySet()) {
            if (movement.getValue().getOwner() != getCurrentPlayer()) {
                Piece piece = movement.getValue();
                if (!piece.isKing()) {
                    List<Position> validMovements = piece.getValidMovements(this, movement.getKey(), player);
                    
                    validMovements.retainAll(kingValidMovements);
                    resPositions.addAll(validMovements);
                }
            }
        }
        return resPositions;
    }

    public Position getKingPosition(Player player) {
        for (Map.Entry<Position, Piece> pieceEntry : positionToPieceMap.entrySet()) {
            Piece piece = pieceEntry.getValue();
            if ((piece.isKing()) && piece.getOwner() == player) {
                return pieceEntry.getKey();
            }
        }
        return null;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Get the piece at the position specified by the String
     *
     * @param colrow The string indication of position; i.e. "d5"
     * @return The piece at that position, or null if it does not exist.
     */
    public Piece getPieceAt(String colrow) {
        Position position = new Position(colrow);
        return getPieceAt(position);
    }

    /**
     * Get the piece at a given position on the board
     *
     * @param position The position to inquire about.
     * @return The piece at that position, or null if it does not exist.
     */
    public Piece getPieceAt(Position position) {
        return positionToPieceMap.get(position);
    }

    /**
     * Method to place a piece at a given position
     *
     * @param piece    The piece to place
     * @param position The position
     */
    private void placePiece(Piece piece, Position position) {
        positionToPieceMap.put(position, piece);
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public static GameState deepClone(GameState object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (GameState) ois.readObject();
        }
        catch (Exception e) {
            throw new RuntimeException("Game state cloning exception");

        }}
}
