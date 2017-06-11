package chess.moves;

import chess.Player;
import chess.Position;
import chess.pieces.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static chess.Position.getNewPosition;

public class TestUtils {

    /*
Create the following setup:

    a   b   c   d   e   f   g   h
  +---+---+---+---+---+---+---+---+
8 | R |   | B |   |   |   | N |   | 8
  +---+---+---+---+---+---+---+---+
7 |   |   | K | N |   |   | P |   | 7
  +---+---+---+---+---+---+---+---+
6 | b |   |   |   | R |   | B |   | 6
  +---+---+---+---+---+---+---+---+
5 |   |   |   | P |   |   |   |   | 5
  +---+---+---+---+---+---+---+---+
4 |   |   | p |   |   | r |   | Q | 4
  +---+---+---+---+---+---+---+---+
3 |   |   |   |   | k |   |   |   | 3
  +---+---+---+---+---+---+---+---+
2 | p |   | n |   |   |   | q |   | 2
  +---+---+---+---+---+---+---+---+
1 | r |   |   |   |   | b |   |   | 1
  +---+---+---+---+---+---+---+---+
    a   b   c   d   e   f   g   h

 */
    public static Map<Position, Piece> createTestBoard() {
        Map<Position, Piece> testBoard = new HashMap<>();

        testBoard.put(getNewPosition("a1"), new Rook(Player.White));
        testBoard.put(getNewPosition("f1"), new Bishop(Player.White));
        testBoard.put(getNewPosition("a2"), new Pawn(Player.White));
        testBoard.put(getNewPosition("c2"), new Knight(Player.White));
        testBoard.put(getNewPosition("g2"), new Queen(Player.White));
        testBoard.put(getNewPosition("e3"), new King(Player.White));
        testBoard.put(getNewPosition("c4"), new Pawn(Player.White));
        testBoard.put(getNewPosition("f4"), new Rook(Player.White));
        testBoard.put(getNewPosition("a6"), new Bishop(Player.White));

        testBoard.put(getNewPosition("a8"), new Rook(Player.Black));
        testBoard.put(getNewPosition("c8"), new Bishop(Player.Black));
        testBoard.put(getNewPosition("g8"), new Knight(Player.Black));
        testBoard.put(getNewPosition("c7"), new King(Player.Black));
        testBoard.put(getNewPosition("d7"), new Knight(Player.Black));
        testBoard.put(getNewPosition("g7"), new Pawn(Player.Black));
        testBoard.put(getNewPosition("e6"), new Rook(Player.Black));
        testBoard.put(getNewPosition("g6"), new Bishop(Player.Black));
        testBoard.put(getNewPosition("d5"), new Pawn(Player.Black));
        testBoard.put(getNewPosition("h4"), new Queen(Player.Black));

        return testBoard;
    }

    public static List<Move> getWhitesPossibleMoves() {
        List<Move> moves = new LinkedList<>();

        moves.add(new Move(getNewPosition("a1"), getNewPosition("b1")));
        moves.add(new Move(getNewPosition("a1"), getNewPosition("c1")));
        moves.add(new Move(getNewPosition("a1"), getNewPosition("d1")));
        moves.add(new Move(getNewPosition("a1"), getNewPosition("e1")));
        moves.add(new Move(getNewPosition("f1"), getNewPosition("e2")));
        moves.add(new Move(getNewPosition("f1"), getNewPosition("d3")));
        moves.add(new Move(getNewPosition("a2"), getNewPosition("a3")));
        moves.add(new Move(getNewPosition("a2"), getNewPosition("a4")));
        moves.add(new Move(getNewPosition("c2"), getNewPosition("a3")));
        moves.add(new Move(getNewPosition("c2"), getNewPosition("b4")));
        moves.add(new Move(getNewPosition("c2"), getNewPosition("d4")));
        moves.add(new Move(getNewPosition("c2"), getNewPosition("e1")));
        moves.add(new Move(getNewPosition("g2"), getNewPosition("g3")));
        moves.add(new Move(getNewPosition("g2"), getNewPosition("g4")));
        moves.add(new Move(getNewPosition("g2"), getNewPosition("g5")));
        moves.add(new Move(getNewPosition("g2"), getNewPosition("g6")));
        moves.add(new Move(getNewPosition("g2"), getNewPosition("h3")));
        moves.add(new Move(getNewPosition("g2"), getNewPosition("h2")));
        moves.add(new Move(getNewPosition("g2"), getNewPosition("h1")));
        moves.add(new Move(getNewPosition("g2"), getNewPosition("g1")));
        moves.add(new Move(getNewPosition("g2"), getNewPosition("f2")));
        moves.add(new Move(getNewPosition("g2"), getNewPosition("e2")));
        moves.add(new Move(getNewPosition("g2"), getNewPosition("d2")));
        moves.add(new Move(getNewPosition("g2"), getNewPosition("f3")));
        moves.add(new Move(getNewPosition("g2"), getNewPosition("e4")));
        moves.add(new Move(getNewPosition("g2"), getNewPosition("d5")));
        moves.add(new Move(getNewPosition("e3"), getNewPosition("e4")));
        moves.add(new Move(getNewPosition("e3"), getNewPosition("f3")));
        moves.add(new Move(getNewPosition("e3"), getNewPosition("f2")));
        moves.add(new Move(getNewPosition("e3"), getNewPosition("e2")));
        moves.add(new Move(getNewPosition("e3"), getNewPosition("d2")));
        moves.add(new Move(getNewPosition("e3"), getNewPosition("d3")));
        moves.add(new Move(getNewPosition("e3"), getNewPosition("d4")));
        moves.add(new Move(getNewPosition("c4"), getNewPosition("c5")));
        moves.add(new Move(getNewPosition("c4"), getNewPosition("d5")));
        moves.add(new Move(getNewPosition("f4"), getNewPosition("f5")));
        moves.add(new Move(getNewPosition("f4"), getNewPosition("f6")));
        moves.add(new Move(getNewPosition("f4"), getNewPosition("f7")));
        moves.add(new Move(getNewPosition("f4"), getNewPosition("f8")));
        moves.add(new Move(getNewPosition("f4"), getNewPosition("g4")));
        moves.add(new Move(getNewPosition("f4"), getNewPosition("h4")));
        moves.add(new Move(getNewPosition("f4"), getNewPosition("f3")));
        moves.add(new Move(getNewPosition("f4"), getNewPosition("f2")));
        moves.add(new Move(getNewPosition("f4"), getNewPosition("e4")));
        moves.add(new Move(getNewPosition("f4"), getNewPosition("d4")));
        moves.add(new Move(getNewPosition("a6"), getNewPosition("b7")));
        moves.add(new Move(getNewPosition("a6"), getNewPosition("c8")));
        moves.add(new Move(getNewPosition("a6"), getNewPosition("b5")));

        return moves;
    }

}
