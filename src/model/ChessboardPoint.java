package model;

/**
 * This class represents positions on the checkerboard, such as (0, 0), (0, 7), and so on
 * Where, the upper left corner is (0, 0), the lower left corner is (7, 0), the upper right corner is (0, 7), and the lower right corner is (7, 7).
 */
public class ChessboardPoint {
    private final int row;
    private final int col;
    private final String name;
    public String getName() {
        return name;
    }
    public PlayerColor getPlayerColor() {
        return playerColor;
    }
    private PlayerColor playerColor = PlayerColor.BLUE;
    public ChessboardPoint(int row, int col) {
        this.row = row;
        this.col = col;
        if ((this.row == 4 || this.row == 5 || this.row == 3) && (this.col == 1 || this.col == 2 || this.col == 5 || this.col == 4)) {
            this.name = "River";
        } else if ((this.row == 0 && (this.col == 2 || this.col == 4)) || (this.row == 1 && (this.col == 3)) || (this.row == 8
                && (this.col == 2 || this.col == 4)) || (this.row == 7 && (this.col == 3))) {
            this.name = "Trap";
            if (this.row < 3) {
                this.playerColor = PlayerColor.BLUE;
            } else {
                this.playerColor = PlayerColor.RED;
            }
        } else if ((this.row == 0 || this.row == 8) && (this.col == 3)) {
            this.name = "Den";
            if (this.row == 8) {
                this.playerColor = PlayerColor.RED;
            }
        } else {
            this.name = "Land";
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public int hashCode() {
        return row + col;
    }

    @Override
    @SuppressWarnings("ALL")
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        ChessboardPoint temp = (ChessboardPoint) obj;
        return (temp.getRow() == this.row) && (temp.getCol() == this.col);
    }

    @Override
    public String toString() {
        return "("+row + ","+col+") " + "on the chessboard is clicked!";
    }
}
