package model;

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
        this.grid = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];
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

    public void initPieces() {
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant", 8,CellType.Piece,"/img/animals/elephant.png"));
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant", 8,CellType.Piece,"/img/animals/elephant.png"));
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion", 7,CellType.Piece,"/img/animals/lion.png"));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion", 7,CellType.Piece,"/img/animals/lion.png"));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger", 6,CellType.Piece,"/img/animals/tiger.png"));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger", 6,CellType.Piece,"/img/animals/tiger.png"));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard", 5,CellType.Piece,"/img/animals/leopard.png"));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard", 5,CellType.Piece,"/img/animals/leopard.png"));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf", 4,CellType.Piece,"/img/animals/wolf.png"));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf", 4,CellType.Piece,"/img/animals/wolf.png"));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog", 3,CellType.Piece,"/img/animals/dog.png"));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog", 3,CellType.Piece,"/img/animals/dog.png"));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat", 2,CellType.Piece,"/img/animals/cat.png"));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat", 2,CellType.Piece,"/img/animals/cat.png"));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Rat", 1,CellType.Piece,"/img/animals/rat.png"));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat", 1,CellType.Piece,"/img/animals/rat.png"));
    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
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
        if (getChessPieceAt(dest) == null){
            setChessPiece(dest, removeChessPiece(src));
        }
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        // TODO: Finish the method.
        removeChessPiece(dest);
        moveChessPiece(src, dest);// capture the chess piece
    }


    public Cell[][] getGrid() {
        return grid;
    }

    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getChessPieceAt(point).getOwner();
    }

    public boolean isValidJumpSquare(ChessboardPoint src, ChessboardPoint dest) {
        ChessPiece srcPiece = getChessPieceAt(src);
        ChessPiece destPiece = getChessPieceAt(dest);
                if (getChessPieceAt(src) == null ) {
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

//    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
//
//        if (getChessPieceAt(src) == null) {
//            return false;
//        } // if the source is empty , return false
//        if (getChessPieceAt(dest) != null) {
//            if (getChessPieceAt(src).getOwner() == getChessPieceAt(dest).getOwner()) {
//                return false;
//            }
//        }
//        if (dest.getName().equals("River")) {
//            if (!getChessPieceAt(src).getName().equals("Rat")) {
//                return false;
//            }
//        }
//        if (dest.getName().equals("Den")) {
//            if (dest.getRow() == 0 || dest.getRow() == 8) {
//                return false; // Cannot enter own den
//            }
//        }
//        return true; // Move is valid
//    }
public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
    if (getChessPieceAt(src) == null) {
        return false;
    } // if the source is empty , return false
    if (getChessPieceAt(dest) != null) {
        if (getChessPieceAt(src).getOwner() == getChessPieceAt(dest).getOwner()) {
            return false;
        }
    }
    if (dest.getName().equals("River")) {
        if (!getChessPieceAt(src).getName().equals("Rat")) {
            return false;
        }
    }// if the destination is river, only rat can move to river
    if (dest.getName().equals("Den")) {
        if (dest.getPlayerColor() == getChessPieceOwner(src)) {
            return false;
        }
    }
    if (calculateDistance(src, dest) != 1) {
        if (getChessPieceAt(src).getName().equals("Lion") || getChessPieceAt(src).getName().equals("Tiger")) {
            if (dest.getName().equals("River")) {
                return false;
            }
            if (src.getCol() == dest.getCol()) {
                int row = src.getRow();
                int col = src.getCol();
                if (row > dest.getRow()) {
                    row--;
                } else if (row < dest.getRow()) {
                    row++;
                }
                while (row != dest.getRow()) {
                    if (getChessPieceAt(new ChessboardPoint(row, col)) != null) {
                        return false;
                    }
                    if (!Data.chessboardPointMap.get(row * 10 + col).getName().equals("River")) {
                        return false;
                    }
                    if (row > dest.getRow()) {
                        row--;
                    } else {
                        row++;
                    }
                }
                return true;
            }
            if (src.getRow() == dest.getRow()) {
                int row = src.getRow();
                int col = src.getCol();
                if (col > dest.getCol()) {
                    col--;
                } else if (col < dest.getCol()) {
                    col++;
                }
                while (col != dest.getCol()) {
                    if (getChessPieceAt(new ChessboardPoint(row, col)) != null) {
                        return false;
                    }
                    if (!Data.chessboardPointMap.get(row * 10 + col).getName().equals("River")) {
                        return false;
                    }
                    if (col > dest.getCol()) {
                        col--;
                    } else {
                        col++;
                    }
                }
                return true;
            }
        }
    }// if the distance is not 1, only lion and tiger can jump over the river
    return calculateDistance(src, dest) == 1;
}


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {

        if (!isValidMove(src, dest)) {
            return false;
        } // if the move is not valid, return false
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) == null) {
            return false;
        }
        if (getChessPieceAt(src).getOwner() == getChessPieceAt(dest).getOwner()) {
            return false;
        }// if the chess piece is the same color, return false
        if (getChessPieceAt(src).canCapture(getChessPieceAt(dest))) {
            if (src.getName().equals("River") && getChessPieceAt(src).getName().equals("Rat")) {
                return false;
            }// if the source is river and the chess piece is rat, return false
            return true;
        }// if the chess piece can capture the other chess piece, return true
        return false;

    }

//    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
//        riverCell.add(new ChessboardPoint(3, 1));
//        riverCell.add(new ChessboardPoint(3, 2));
//        riverCell.add(new ChessboardPoint(4, 1));
//        riverCell.add(new ChessboardPoint(4, 2));
//        riverCell.add(new ChessboardPoint(5, 1));
//        riverCell.add(new ChessboardPoint(5, 2));
//
//        riverCell.add(new ChessboardPoint(3, 4));
//        riverCell.add(new ChessboardPoint(3, 5));
//        riverCell.add(new ChessboardPoint(4, 4));
//        riverCell.add(new ChessboardPoint(4, 5));
//        riverCell.add(new ChessboardPoint(5, 4));
//        riverCell.add(new ChessboardPoint(5, 5));
//        // TODO:Fix this method
//        ChessPiece srcPiece = getChessPieceAt(src);
//        ChessPiece destPiece = getChessPieceAt(dest);
//        //If the piece is null,it's not valid
//        if (src == null || dest == null) {
//            return false;
//        }
//        // If the pieces belong to the same player, the capture is not valid.
//        if (srcPiece.getOwner() == destPiece.getOwner()) {
//            return false;
//        }
//        //If the method canCapture returns true......
//        if (srcPiece.canCapture(destPiece)) {
//            //tiger and lion jump across the river to catch the animals
//            if (srcPiece.getName().equals("Tiger") || srcPiece.getName().equals("Lion")) {
//                if (isValidMove(src, dest) && !riverCell.contains("Rat")) {
//                    return true;
//                }
//            }
//            // Rat cannot capture Elephant on land and cannot be captured on water
//            if (srcPiece.getName().equals("Rat")&&riverCell.contains("Rat")&&destPiece.getName().equals("Elephant")) {
//                return false;
//            }
//            if (destPiece.getName().equals("Rat")&&riverCell.contains("rat")){
//                return false;
//            }
//        }
//        return true;
//
//    }
//        private ChessTimer timer;
//
//        public void ChessBoard() {
//            timer = new ChessTimer();
//        }
//
//        public void startGame() {
//            timer.start();
//            while (true) {
//                // game logic here
//                // switch player when necessary
//                timer.switchPlayer();
//            }
//        }
        public boolean isDensOccupied(PlayerColor playerColor){
            ChessPiece DensPiece = null;
            // Find the ChessPiece in the dens based on the playerColor
            if (playerColor==PlayerColor.RED){
                DensPiece = grid[0][3].getPiece();
            } else if (playerColor == PlayerColor.BLUE) {
                DensPiece = grid[8][3].getPiece();
            }
            // Check if the dens is occupied by the player's ChessPiece
            return DensPiece!=null&&DensPiece.getType() == CellType.Dens;
        }
    public boolean isPlayerStuck(PlayerColor playerColor) {
        // Iterate through all cells on the chessboard
        for (int row = 0; row < Constant.CHESSBOARD_ROW_SIZE.getNum(); row++) {
            for (int col = 0; col < Constant.CHESSBOARD_COL_SIZE.getNum(); col++) {
                ChessPiece chessPiece = grid[row][col].getPiece();

                // Check if the cell contains a ChessPiece owned by the specified playerColor
                if (chessPiece != null && chessPiece.getOwner() == playerColor) {
                    // Check if the ChessPiece can make a valid move or capture
                    ChessboardPoint src = new ChessboardPoint(row, col);
                    for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                        for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                            ChessboardPoint dest = new ChessboardPoint(i, j);
                            if (isValidMove(src, dest) || isValidCapture(src, dest)) {
                                // There is at least one valid move or capture, player is not stuck
                                return false;
                            }
                        }
                    }
                }
            }
        }

        // No valid moves or captures found for any of the player's ChessPieces, player is stuck
        return true;
    }
    public boolean areAllPiecesCaptured(PlayerColor playerColor) {
        // Iterate through all cells on the chessboard
        for (int row = 0; row < Constant.CHESSBOARD_ROW_SIZE.getNum(); row++) {
            for (int col = 0; col < Constant.CHESSBOARD_COL_SIZE.getNum(); col++) {
                ChessPiece chessPiece = grid[row][col].getPiece();

                // Check if the cell contains a ChessPiece owned by the specified playerColor
                if (chessPiece != null && chessPiece.getOwner() == playerColor) {
                    // At least one ChessPiece of the player is still on the chessboard
                    return false;
                }
            }
        }

        // No ChessPiece of the player found on the chessboard
        return true;
    }
    public void restart() {
        this.grid = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];
        initGrid();
        initPieces();
    }
}

