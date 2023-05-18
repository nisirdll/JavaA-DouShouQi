package model;
import java.util.ArrayList;
import java.util.List;

public class ChessNotation {
    private List<String> moves;

    public ChessNotation() {
        moves = new ArrayList<>();
    }

    public void addMove(String move) {
        moves.add(move);
    }

    public List<String> getMoves() {
        return moves;
    }

    public void printNotation() {
        for (int i = 0; i < moves.size(); i++) {
            int moveNumber = i + 1;
            String move = moves.get(i);
            System.out.println(moveNumber + ". " + move);
        }
    }
}
