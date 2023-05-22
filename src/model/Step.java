package model;

public class Step {
    public ChessboardPoint src;
    public ChessboardPoint dest;
    public PlayerColor color;
    public ChessPiece captured;


    public Step(ChessboardPoint src, ChessboardPoint dest, PlayerColor color) {
        this.src = src;
        this.dest = dest;
        this.color = color;
        captured = null;
    }

    public Step(ChessboardPoint src, ChessboardPoint dest, PlayerColor color, ChessPiece captured) {
        this.src = src;
        this.dest = dest;
        this.color = color;
        this.captured = captured;
    }

    @Override
    public String toString() {
        if (captured == null)
            return (color == PlayerColor.BLUE ? "b " : "r ") +
                    "(" + src.getRow() + "," + src.getCol() + ") " +
                    "(" + dest.getRow() +"," + dest.getCol() + ") " +
                    "null";
        else
            return (color == PlayerColor.BLUE ? "b " : "r ") +
                    "(" + src.getRow() + "," + src.getCol() + ") " +
                    "(" + dest.getRow() +"," + dest.getCol() + ") " +
                    captured.getName();
    }
}