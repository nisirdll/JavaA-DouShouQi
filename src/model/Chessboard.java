package model;

import controller.ChessTimer;

import java.util.HashSet;
import java.util.Set;
/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();


    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19

        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void initPieces() {
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant", 8));
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant", 8));
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion", 7));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion", 7));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger", 6));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger", 6));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard", 5));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard", 5));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf", 4));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf", 4));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog", 3));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog", 3));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat", 2));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat", 2));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Rat", 1));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat", 1));
    }

    private ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (isValidCapture(src, dest)||isValidJumpSquare(src,dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        // TODO: Finish the method.
        setChessPiece(dest,removeChessPiece(src));
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidJumpSquare(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }

        int srcRow = src.getRow();
        int srcCol = src.getCol();
        int destRow = dest.getRow();
        int destCol = dest.getCol();

        // Check if the source and destination are on the same row or column
        if (srcRow == destRow) {
            // Check if there are any water squares between the source and destination column
            int minCol = Math.min(srcCol, destCol);
            int maxCol = Math.max(srcCol, destCol);
            for (int col = minCol + 1; col < maxCol; col++) {
                ChessboardPoint point = new ChessboardPoint(srcRow, col);
                if (riverCell.contains(point)) {
                    // There is a rat on the water square, so the jump is blocked
                    return false;
                }
            }
        } else if (srcCol == destCol) {
            // Check if there are any water squares between the source and destination row
            int minRow = Math.min(srcRow, destRow);
            int maxRow = Math.max(srcRow, destRow);
            for (int row = minRow + 1; row < maxRow; row++) {
                ChessboardPoint point = new ChessboardPoint(row, srcCol);
                if (riverCell.contains(point)) {
                    // There is a rat on the water square, so the jump is blocked
                    return false;
                }
            }
        }

        // No water squares blocking the jump
        return true;
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        ChessPiece srcPiece = getChessPieceAt(src);
        ChessPiece destPiece = getChessPieceAt(dest);

        if (srcPiece == null || destPiece != null) {
            return false;
        }

        int srcRow = src.getRow();
        int srcCol = src.getCol();
        int destRow = dest.getRow();
        int destCol = dest.getCol();

        // Check if the source and destination are adjacent horizontally or vertically
        if (Math.abs(srcRow - destRow) + Math.abs(srcCol - destCol) != 1) {
            return false;
        }
        if (destPiece.getType()==CellType.Dens){
            if (destPiece.getOwner()==srcPiece.getOwner()){
                return false;
            }else {
                destPiece.setRank(0);
            }
        }
        return true;
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        riverCell.add(new ChessboardPoint(3, 1));
        riverCell.add(new ChessboardPoint(3, 2));
        riverCell.add(new ChessboardPoint(4, 1));
        riverCell.add(new ChessboardPoint(4, 2));
        riverCell.add(new ChessboardPoint(5, 1));
        riverCell.add(new ChessboardPoint(5, 2));

        riverCell.add(new ChessboardPoint(3, 4));
        riverCell.add(new ChessboardPoint(3, 5));
        riverCell.add(new ChessboardPoint(4, 4));
        riverCell.add(new ChessboardPoint(4, 5));
        riverCell.add(new ChessboardPoint(5, 4));
        riverCell.add(new ChessboardPoint(5, 5));
        // TODO:Fix this method
        ChessPiece srcPiece = getChessPieceAt(src);
        ChessPiece destPiece = getChessPieceAt(dest);
        //If the piece is null,it's not valid
        if (src == null || dest == null) {
            return false;
        }
        // If the pieces belong to the same player, the capture is not valid.
        if (srcPiece.getOwner() == destPiece.getOwner()) {
            return false;
        }
        //If the method canCapture returns true......
        if (srcPiece.canCapture(destPiece)) {
            //tiger and lion jump across the river to catch the animals
            if (srcPiece.getName().equals("Tiger") || srcPiece.getName().equals("Lion")) {
                if (isValidJumpSquare(src, dest) && !riverCell.contains("Rat")) {
                    return true;
                }
            }
                // Rat cannot capture Elephant on land and cannot be captured on water
                if (srcPiece.getName().equals("Rat")) {
                    if (!riverCell.contains("Rat") && !destPiece.getName().equals("Elephant")) {
                        return true;
                    }
                }
            }
            return false;

    }
        private ChessTimer timer;

        public void ChessBoard() {
            timer = new ChessTimer();
        }

        public void startGame() {
            timer.start();
            while (true) {
                // game logic here
                // switch player when necessary
                timer.switchPlayer();
            }
        }

}

