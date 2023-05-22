package model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard implements Serializable {
    private Cell[][] grid;
    public ArrayList<Step> steps;

    public ArrayList<ChessPiece> blueDead;
    public ArrayList<ChessPiece> redDead;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();

    public Chessboard() {
        this.grid = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];
        initGrid();
        initPieces();
        this.steps=new ArrayList<>();
        this.redDead=new ArrayList<>();
        this.blueDead= new ArrayList<>();
    }

    public void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public void initPieces() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].removePiece();
            }
        }
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant", 8, CellType.Piece, "/img/animals/elephant.png"));
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant", 8, CellType.Piece, "/img/animals/elephant.png"));
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion", 7, CellType.Piece, "/img/animals/lion.png"));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion", 7, CellType.Piece, "/img/animals/lion.png"));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger", 6, CellType.Piece, "/img/animals/tiger.png"));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger", 6, CellType.Piece, "/img/animals/tiger.png"));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard", 5, CellType.Piece, "/img/animals/leopard.png"));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard", 5, CellType.Piece, "/img/animals/leopard.png"));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf", 4, CellType.Piece, "/img/animals/wolf.png"));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf", 4, CellType.Piece, "/img/animals/wolf.png"));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog", 3, CellType.Piece, "/img/animals/dog.png"));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog", 3, CellType.Piece, "/img/animals/dog.png"));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat", 2, CellType.Piece, "/img/animals/cat.png"));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat", 2, CellType.Piece, "/img/animals/cat.png"));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Rat", 1, CellType.Piece, "/img/animals/rat.png"));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat", 1, CellType.Piece, "/img/animals/rat.png"));
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
        if (getChessPieceAt(dest) == null) {
            ChessPiece chess= this.removeChessPiece(src);
            setChessPiece(dest, chess);
            steps.add(new Step(src,dest,chess.getOwner()));
        }
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!this.isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        } else {
            ChessPiece eater= this.removeChessPiece(src);
            ChessPiece dead= this.removeChessPiece(dest);
            this.setChessPiece(dest,eater);
            if(dead.getOwner()==PlayerColor.BLUE){
                blueDead.add(dead);
            }else {
                redDead.add(dead);
            }
            steps.add(new Step(src, dest, eater.getOwner(), dead));

        }
    }


    public Cell[][] getGrid() {
        return grid;
    }

    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getChessPieceAt(point).getOwner();
    }
    public PlayerColor getChessPieceOwner(int x,int y) {
        ChessboardPoint point=new ChessboardPoint(x,y);
        return this.getGridAt(point).getPiece().getOwner();
    }


//    public boolean isValidJumpSquare(ChessboardPoint src, ChessboardPoint dest) {
//        ChessPiece srcPiece = getChessPieceAt(src);
//        ChessPiece destPiece = getChessPieceAt(dest);
//        if (getChessPieceAt(src) == null) {
//            return false;
//        }
//
//        int srcRow = src.getRow();
//        int srcCol = src.getCol();
//        int destRow = dest.getRow();
//        int destCol = dest.getCol();
//        // Check if the source and destination are on the same row or column
//        if (srcRow == destRow) {
//            // Check if there are any water squares between the source and destination column
//            int minCol = Math.min(srcCol, destCol);
//            int maxCol = Math.max(srcCol, destCol);
//            for (int col = minCol + 1; col < maxCol; col++) {
//                ChessboardPoint point = new ChessboardPoint(srcRow, col);
//                if (riverCell.contains(point)) {
//                    // There is a rat on the water square, so the jump is blocked
//                    return false;
//                }
//            }
//        } else if (srcCol == destCol) {
//            // Check if there are any water squares between the source and destination row
//            int minRow = Math.min(srcRow, destRow);
//            int maxRow = Math.max(srcRow, destRow);
//            for (int row = minRow + 1; row < maxRow; row++) {
//                ChessboardPoint point = new ChessboardPoint(row, srcCol);
//                if (riverCell.contains(point)) {
//                    // There is a rat on the water square, so the jump is blocked
//                    return false;
//                }
//            }
//        }
//
//        // No water squares blocking the jump
//        return true;
//
//    }

    public void saveGame(String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);

            objectOut.close();
            fileOut.close();

            System.out.println("The game was successfully saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Chessboard loadGame(String fileName) {
        Chessboard loadedChessboard = null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            loadedChessboard = (Chessboard) objectIn.readObject();

            objectIn.close();
            fileIn.close();

            System.out.println("The game was successfully loaded");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loadedChessboard;
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
//public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
//    if (getChessPieceAt(src) == null) {
//        return false;
//    } // if the source is empty , return false
//    if (getChessPieceAt(dest) != null) {
//        if (getChessPieceAt(src).getOwner() == getChessPieceAt(dest).getOwner()) {
//            return false;
//        }
//    }
//    if (dest.getName().equals("River")) {
//        if (!getChessPieceAt(src).getName().equals("Rat")) {
//            return false;
//        }
//    }// if the destination is river, only rat can move to river
//    if (dest.getName().equals("Den")) {
//        if (dest.getPlayerColor() == getChessPieceOwner(src)) {
//            return false;
//        }
//    }
//    if (calculateDistance(src, dest) != 1) {
//        if (getChessPieceAt(src).getName().equals("Lion") || getChessPieceAt(src).getName().equals("Tiger")) {
//            if (dest.getName().equals("River")) {
//                return false;
//            }
//            if (src.getCol() == dest.getCol()) {
//                int row = src.getRow();
//                int col = src.getCol();
//                if (row > dest.getRow()) {
//                    row--;
//                } else if (row < dest.getRow()) {
//                    row++;
//                }
//                while (row != dest.getRow()) {
//                    if (getChessPieceAt(new ChessboardPoint(row, col)) != null) {
//                        return false;
//                    }
//                    if (!Data.chessboardPointMap.get(row * 10 + col).getName().equals("River")) {
//                        return false;
//                    }
//                    if (row > dest.getRow()) {
//                        row--;
//                    } else {
//                        row++;
//                    }
//                }
//                return true;
//            }
//            if (src.getRow() == dest.getRow()) {
//                int row = src.getRow();
//                int col = src.getCol();
//                if (col > dest.getCol()) {
//                    col--;
//                } else if (col < dest.getCol()) {
//                    col++;
//                }
//                while (col != dest.getCol()) {
//                    if (getChessPieceAt(new ChessboardPoint(row, col)) != null) {
//                        return false;
//                    }
//                    if (!Data.chessboardPointMap.get(row * 10 + col).getName().equals("River")) {
//                        return false;
//                    }
//                    if (col > dest.getCol()) {
//                        col--;
//                    } else {
//                        col++;
//                    }
//                }
//                return true;
//            }
//        }
//    }// if the distance is not 1, only lion and tiger can jump over the river
//    return calculateDistance(src, dest) == 1;
//}

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if (this.getChessPieceAt(src) != null && this.getChessPieceAt(dest) == null && ValidMove(src, dest))
            return true;
        else return false;
    }
    public int typeGrid(int i, int j) {
        int k = 0;
        //0:common grid,1:river,2:blue trap,3:red trap;4:blue home,5:red home
        if (i < 6 && i > 2) {
            if (j == 1 || j == 2 || j == 4 || j == 5)
                k = 1;
        }
        if ((i == 0 & j == 2) || (i == 0 & j == 4) || (i == 1 & j == 3))
            k = 2;
        if ((i == 0 & j == 3))
            k = 3;
        if ((i == 7 & j == 3) || (i == 8 & j == 2) || (i == 8 & j == 4))
            k = 4;
        if ((i == 8 & j == 3))
            k = 5;
        return k;
    }

    public boolean ValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if (!(getChessPieceOwner(src) == PlayerColor.BLUE && typeGrid(dest.getRow(), dest.getCol()) == 5) &&
                !(getChessPieceOwner(src) == PlayerColor.RED && typeGrid(dest.getRow(), dest.getCol()) == 3)) {
            if (this.calculateDistance(src, dest) == 1) {
                if (getChessPieceAt(src).getName().equals("Rat"))
                    return true;
                else {
                    if (this.typeGrid(src.getRow(), src.getCol()) == 1 || this.typeGrid(dest.getRow(), dest.getCol()) == 1)
                        return false;
                    else return true;
                }
            } else {
                if (this.getChessPieceAt(src).getName().equals("Lion")  || this.getChessPieceAt(src).getName().equals("Tiger")) {
                    if (jumpPoint(src, dest) == 0)
                        return false;
                    else return true;
                } else return false;
            }
        } else {
            return false;
        }
    }

    private ChessPiece getChessPieceAt(int x, int y) {
        if(this.getGrid()[x][y].getPiece()==null){
            ChessPiece temp= new ChessPiece(null,"null",0);
            return temp;
        }else {
            return this.getGrid()[x][y].getPiece();
        }
    }

    public int jumpPoint(ChessboardPoint src, ChessboardPoint dest) {
        int k = 0;
        int X = src.getRow();
        int Y = src.getCol();
        int x = dest.getRow();
        int y = dest.getCol();
        //0:can't jump, 1:jump to right, 2:jump to left, 3:jump down, 4:jump up)
        if (X == x && X > 2 && X < 6) {
            if (((Y == 0 & y == 3) && getChessPieceAt(x, 1).getOwner() == null && getChessPieceAt(x, 2).getOwner() == null) ||
                    (Y == 3 & y == 6) && getChessPieceAt(x, 4).getOwner() == null && getChessPieceAt(x, 5).getOwner() == null)
                k = 1;
            if (((Y == 3 & y == 0) && getChessPieceAt(x, 1).getOwner() == null && getChessPieceAt(x, 2).getOwner() == null) ||
                    (Y == 6 & y == 3) && getChessPieceAt(x, 4).getOwner() == null && getChessPieceAt(x, 5).getOwner() == null)
                k = 2;
        }
        if (Y == y && (y == 1 || y == 2 || y == 4 || y == 5)) {
            if ((X == 2 & x == 6) && getChessPieceAt(3, y).getOwner() == null && getChessPieceAt(4, y).getOwner() == null && getChessPieceAt(5, y) .getOwner()== null)
                k = 3;
            if ((X == 6 & x == 2) && getChessPieceAt(3, y).getOwner() == null && getChessPieceAt(4, y).getOwner() == null && getChessPieceAt(5, y) .getOwner()== null)
                k = 4;
        }
        return k;
    }
//    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
//
//        if (!isValidMove(src, dest)) {
//            return false;
//        } // if the move is not valid, return false
//        if (getChessPieceAt(src) == null || getChessPieceAt(dest) == null) {
//            return false;
//        }
//        if (getChessPieceAt(src).getOwner() == getChessPieceAt(dest).getOwner()) {
//            return false;
//        }// if the chess piece is the same color, return false
//        if (getChessPieceAt(src).canCapture(getChessPieceAt(dest))) {
//            if (src.getName().equals("River") && getChessPieceAt(src).getName().equals("Rat")) {
//                return false;
//            }// if the source is river and the chess piece is rat, return false
//            return true;
//        }// if the chess piece can capture the other chess piece, return true
//        return false;
//
//    }

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

    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        if (this.getChessPieceAt(src) != null && this.getChessPieceAt(dest) != null
                && this.getChessPieceOwner(src) != this.getChessPieceOwner(dest)) {
            if (!this.ValidMove(src, dest)) {
                return false;
            } else {
                if ((typeGrid(dest.getRow(), dest.getCol()) == 2 && getChessPieceOwner(dest) == PlayerColor.RED)
                        || (typeGrid(dest.getRow(), dest.getCol()) == 4 && getChessPieceOwner(dest) == PlayerColor.BLUE)) {
                    return true;
                } else {
                    if (getChessPieceAt(src).getName().equals("Rat") && getChessPieceAt(dest).getName().equals("Elephant")) {
                        if (this.typeGrid(src.getRow(), src.getCol()) == 1)
                            return false;
                        else return true;
                    } else {
                        if (getChessPieceAt(src).getName().equals("Elephant") && getChessPieceAt(dest).getName().equals("Rat"))
                            return false;
                        else {
                            if (this.grid[src.getRow()][src.getCol()].getPiece().
                                    canCapture(this.grid[dest.getRow()][dest.getCol()].getPiece())) {
                                return true;
                            } else return false;
                        }
                    }
                }
            }
        } else return false;
    }

    public boolean isValidCapture(int i,int j,int x,int y) {
        if(i>-1&&i<9&&j>-1&&j<7&&x>-1&&x<9&&y>-1&&y<7){
            ChessboardPoint src=new ChessboardPoint(i,j);
            ChessboardPoint dest=new ChessboardPoint(x,y);
            return isValidCapture(src,dest);}
        else return false;
    }
    public boolean isValidMove(int i,int j,int x,int y) {
        if(i>-1&&i<9&&j>-1&&j<7&&x>-1&&x<9&&y>-1&&y<7){
            ChessboardPoint src = new ChessboardPoint(i, j);
            ChessboardPoint dest = new ChessboardPoint(x, y);
            return isValidMove(src, dest);}
        else return false;
    }
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

    private boolean ifUnderThreat(int x,int y){
        int k=0;
        for(int i=0;i<9;i++) {
            for (int j = 0; j < 7; j++) {
                if (getChessPieceAt(x, y).getOwner() != null && getChessPieceAt(i, j).getOwner() != null) {
                    if (getChessPieceAt(i, j).getOwner()!=getChessPieceAt(x,y).getOwner()&&
                            isValidCapture(i, j, x, y))
                        k = 1;
                }
            }
        }
        return k !=0;
    }
    private int ifUnderThreatX(int x,int y){
        int k=-1;
        for(int i=0;i<9;i++) {
            if(k!=-1)
                break;
            for (int j = 0; j < 7; j++) {
                if(k!=-1)
                    break;
                if (getChessPieceAt(x, y).getOwner() != null && getChessPieceAt(i, j).getOwner() != null) {
                    if (getChessPieceAt(i, j).getOwner()!=getChessPieceAt(x,y).getOwner()&&
                            isValidCapture(i, j, x, y))
                        k = i;

                }
            }
        }
        return k;
    }
    private int ifUnderThreatY(int x,int y){
        int k=-1;
        for(int i=0;i<9;i++){
            if(k!=-1)
                break;
            for(int j=0;j<7;j++) {
                if(k!=-1)
                    break;
                if (getChessPieceAt(x, y).getOwner() != null && getChessPieceAt(i, j).getOwner() != null) {
                    if (getChessPieceAt(i, j).getOwner()!=getChessPieceAt(x,y).getOwner()&&
                            isValidCapture(i, j, x, y))
                        k = j;

                }
            }
        }
        return k;
    }
    private  boolean ifGoingUnderThreat(int X,int Y,int x,int y) {
        int k = 0;
        if (isValidMove(X, Y, x, y)||isValidCapture(X,Y,x,y)) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 7; j++) {
                    if (getChessPieceAt(X, Y).getOwner() != null && getChessPieceAt(i, j).getOwner() != null) {
                        if (getChessPieceAt(i, j).getOwner() != getChessPieceAt(X, Y).getOwner() &&
                                isValidMove(i, j, x, y)) {
                            if (((typeGrid(x, y) == 2 && getChessPieceOwner(X, Y) == PlayerColor.RED)
                                    || (typeGrid(x, y) == 4 && getChessPieceOwner(X, Y) == PlayerColor.BLUE)))
                                k = 1;
                            else if (getChessPieceAt(i, j).getName().equals("Rat") && getChessPieceAt(X, Y).getName().equals("Elephant")) {
                                if (this.typeGrid(i, j) == 0)
                                    k = 1;
                            } else if (!(getChessPieceAt(i, j).getName().equals("Elephant") && getChessPieceAt(X, Y).getName().equals("Rat"))) {
                                if ((this.grid[i][j].getPiece().canCapture(this.grid[X][Y].getPiece())))
                                    k = 1;
                            }

                        }
                    }
                }
            }
        }
        return k ==1;
    }
    private boolean ifShouldMove(int X,int Y,int x,int y){
        return ((isValidCapture(X, Y, x, y) || isValidMove(X, Y, x, y)) && !ifGoingUnderThreat(X, Y, x, y));
    }
    public int[] AI() {
        int X = 0, Y = 0, x = 0, y = 0;
        //1:if i'm winning
        if (getChessPieceAt(8, 2).getOwner() == PlayerColor.RED) {
            X = 8;
            Y = 2;
            x = 8;
            y = 3;
        } else if (getChessPieceAt(7, 3).getOwner() == PlayerColor.RED) {
            X = 7;
            Y = 3;
            x = 8;
            y = 3;
        } else if (getChessPieceAt(8, 4).getOwner() == PlayerColor.RED) {
            X = 8;
            Y = 4;
            x = 8;
            y = 3;
        } else
            //2:if enemy pieces are in my traps
            if (getChessPieceAt(0, 2).getOwner() == PlayerColor.BLUE && ifUnderThreat(0, 2)) {
                X = ifUnderThreatX(0, 2);
                Y = ifUnderThreatY(0, 2);
                x = 0;
                y = 2;
            } else if (getChessPieceAt(1, 3).getOwner() == PlayerColor.BLUE && ifUnderThreat(1, 3)) {
                X = ifUnderThreatX(1, 3);
                Y = ifUnderThreatY(1, 3);
                x = 1;
                y = 3;
            } else if (getChessPieceAt(0, 4).getOwner() == PlayerColor.BLUE && ifUnderThreat(0, 4)) {
                X = ifUnderThreatX(0, 4);
                Y = ifUnderThreatY(0, 4);
                x = 0;
                y = 4;
            } else
                //3:move my pieces to the enemy's traps
                if (getChessPieceAt(8, 1).getOwner() == PlayerColor.RED && ifShouldMove(8, 1, 8, 2)) {
                    X = 8;
                    Y = 1;
                    x = 8;
                    y = 2;
                } else if (getChessPieceAt(7, 2).getOwner() == PlayerColor.RED && ifShouldMove(7, 2, 8, 2)) {
                    X = 7;
                    Y = 2;
                    x = 8;
                    y = 2;
                } else if (getChessPieceAt(7, 2).getOwner() == PlayerColor.RED && ifShouldMove(7, 2, 7, 3)) {
                    X = 7;
                    Y = 2;
                    x = 7;
                    y = 3;
                } else if (getChessPieceAt(6, 3).getOwner() == PlayerColor.RED && ifShouldMove(6, 3, 7, 3)) {
                    X = 6;
                    Y = 3;
                    x = 7;
                    y = 3;
                } else if (getChessPieceAt(7, 4).getOwner() == PlayerColor.RED && ifShouldMove(7, 4, 7, 3)) {
                    X = 7;
                    Y = 4;
                    x = 7;
                    y = 3;
                } else if (getChessPieceAt(7, 4).getOwner() == PlayerColor.RED && ifShouldMove(7, 4, 8, 4)) {
                    X = 7;
                    Y = 4;
                    x = 8;
                    y = 4;
                } else if (getChessPieceAt(8, 5).getOwner() == PlayerColor.RED && ifShouldMove(8, 5, 8, 4)) {
                    X = 8;
                    Y = 5;
                    x = 8;
                    y = 4;
                } else
                    //4:attack enemy's pieces near my traps
                    if (getChessPieceAt(0, 1).getOwner() == PlayerColor.BLUE && ifUnderThreat(0, 1)) {
                        X = ifUnderThreatX(0, 1);
                        Y = ifUnderThreatY(0, 1);
                        x = 0;
                        y = 1;
                    } else if (getChessPieceAt(1, 2).getOwner() == PlayerColor.BLUE && ifUnderThreat(1, 2)) {
                        X = ifUnderThreatX(1, 2);
                        Y = ifUnderThreatY(1, 2);
                        x = 1;
                        y = 2;
                    } else if (getChessPieceAt(2, 3).getOwner() == PlayerColor.BLUE && ifUnderThreat(2, 3)) {
                        X = ifUnderThreatX(2, 3);
                        Y = ifUnderThreatY(2, 3);
                        x = 2;
                        y = 3;
                    } else if (getChessPieceAt(1, 4).getOwner() == PlayerColor.BLUE && ifUnderThreat(1, 4)) {
                        X = ifUnderThreatX(1, 4);
                        Y = ifUnderThreatY(1, 4);
                        x = 1;
                        y = 4;
                    } else if (getChessPieceAt(0, 5).getOwner() == PlayerColor.BLUE && ifUnderThreat(0, 5)) {
                        X = ifUnderThreatX(0, 5);
                        Y = ifUnderThreatY(0, 5);
                        x = 0;
                        y = 5;
                    } else{
                        int c=0;
                        //8:use rat to block the jump of enemy's lion and tiger
                        if (getChessPieceAt(6, 1).getOwner() == PlayerColor.BLUE &&
                                (getChessPieceAt(6, 1).getName().equals("Lion") || getChessPieceAt(6, 1).getName().equals("Tiger"))) {
                            if (getChessPieceAt(3, 0).getOwner() == PlayerColor.RED && getChessPieceAt(3, 0).getName().equals("Rat")) {
                                X = 3;
                                Y = 0;
                                x = 3;
                                y = 1;
                                c = 1;
                            } else if (getChessPieceAt(3, 2).getOwner() == PlayerColor.RED && getChessPieceAt(3, 2).getName().equals("Rat")) {
                                X = 3;
                                Y = 2;
                                x = 3;
                                y = 1;
                                c = 1;
                            } else if (getChessPieceAt(4, 0).getOwner() == PlayerColor.RED && getChessPieceAt(4, 0).getName().equals("Rat")) {
                                X = 4;
                                Y = 0;
                                x = 4;
                                y = 1;
                                c = 1;
                            } else if (getChessPieceAt(4, 2).getOwner() == PlayerColor.RED && getChessPieceAt(4, 2).getName().equals("Rat")) {
                                X = 4;
                                Y = 2;
                                x = 4;
                                y = 1;
                                c = 1;
                            } else if (getChessPieceAt(5, 0).getOwner() == PlayerColor.RED && getChessPieceAt(5, 0).getName().equals("Rat")) {
                                X = 5;
                                Y = 0;
                                x = 5;
                                y = 1;
                                c = 1;
                            } else if (getChessPieceAt(5, 2).getOwner() == PlayerColor.RED && getChessPieceAt(5, 2).getName().equals("Rat")) {
                                X = 5;
                                Y = 2;
                                x = 5;
                                y = 1;
                                c = 1;
                            } else if (getChessPieceAt(2, 1).getOwner() == PlayerColor.RED && getChessPieceAt(2, 1).getName().equals("Rat")) {
                                X = 2;
                                Y = 1;
                                x = 3;
                                y = 1;
                                c = 1;
                            }
                        }
                        if (getChessPieceAt(6, 2).getOwner() == PlayerColor.BLUE &&
                                (getChessPieceAt(6, 2).getName().equals("Lion") || getChessPieceAt(6, 2).getName().equals("Tiger"))) {
                            if (getChessPieceAt(3, 1).getOwner() == PlayerColor.RED && getChessPieceAt(3, 1).getName().equals("Rat")) {
                                X = 3;
                                Y = 1;
                                x = 3;
                                y = 2;
                                c = 1;
                            } else if (getChessPieceAt(3, 3).getOwner() == PlayerColor.RED && getChessPieceAt(3, 3).getName().equals("Rat")) {
                                X = 3;
                                Y = 3;
                                x = 3;
                                y = 2;
                                c = 1;
                            } else if (getChessPieceAt(4, 1).getOwner() == PlayerColor.RED && getChessPieceAt(4, 1).getName().equals("Rat")) {
                                X = 4;
                                Y = 1;
                                x = 4;
                                y = 2;
                                c = 1;
                            } else if (getChessPieceAt(4, 3).getOwner() == PlayerColor.RED && getChessPieceAt(4, 3).getName().equals("Rat")) {
                                X = 4;
                                Y = 3;
                                x = 4;
                                y = 2;
                                c = 1;
                            } else if (getChessPieceAt(5, 1).getOwner() == PlayerColor.RED && getChessPieceAt(5, 1).getName().equals("Rat")) {
                                X = 5;
                                Y = 1;
                                x = 5;
                                y = 2;
                                c = 1;
                            } else if (getChessPieceAt(5, 3).getOwner() == PlayerColor.RED && getChessPieceAt(5, 3).getName().equals("Rat")) {
                                X = 5;
                                Y = 3;
                                x = 5;
                                y = 2;
                                c = 1;
                            } else if (getChessPieceAt(2, 2).getOwner() == PlayerColor.RED && getChessPieceAt(2, 2).getName().equals("Rat")) {
                                X = 2;
                                Y = 2;
                                x = 3;
                                y = 2;
                                c = 1;
                            }
                        }
                        if (getChessPieceAt(6, 4).getOwner() == PlayerColor.BLUE &&
                                (getChessPieceAt(6, 4).getName().equals("Lion") || getChessPieceAt(6, 4).getName().equals("Tiger"))) {
                            if (getChessPieceAt(3, 3).getOwner() == PlayerColor.RED && getChessPieceAt(3, 3).getName().equals("Rat")) {
                                X = 3;
                                Y = 3;
                                x = 3;
                                y = 4;
                                c = 1;
                            } else if (getChessPieceAt(3, 5).getOwner() == PlayerColor.RED && getChessPieceAt(3, 5).getName().equals("Rat")) {
                                X = 3;
                                Y = 5;
                                x = 3;
                                y = 4;
                                c = 1;
                            } else if (getChessPieceAt(4, 3).getOwner() == PlayerColor.RED && getChessPieceAt(4, 3).getName().equals("Rat")) {
                                X = 4;
                                Y = 3;
                                x = 4;
                                y = 4;
                                c = 1;
                            } else if (getChessPieceAt(4, 5).getOwner() == PlayerColor.RED && getChessPieceAt(4, 5).getName().equals("Rat")) {
                                X = 4;
                                Y = 5;
                                x = 4;
                                y = 4;
                                c = 1;
                            } else if (getChessPieceAt(5, 3).getOwner() == PlayerColor.RED && getChessPieceAt(5, 3).getName().equals("Rat")) {
                                X = 5;
                                Y = 3;
                                x = 5;
                                y = 4;
                                c = 1;
                            } else if (getChessPieceAt(5, 5).getOwner() == PlayerColor.RED && getChessPieceAt(5, 5).getName().equals("Rat")) {
                                X = 5;
                                Y = 5;
                                x = 5;
                                y = 4;
                                c = 1;
                            } else if (getChessPieceAt(2, 4).getOwner() == PlayerColor.RED && getChessPieceAt(2, 4).getName().equals("Rat")) {
                                X = 2;
                                Y = 4;
                                x = 3;
                                y = 4;
                                c = 1;
                            }
                        }
                        if (getChessPieceAt(6, 5).getOwner() == PlayerColor.BLUE &&
                                (getChessPieceAt(6, 5).getName().equals("Lion") || getChessPieceAt(6, 5).getName().equals("Tiger"))) {
                            if (getChessPieceAt(3, 4).getOwner() == PlayerColor.RED && getChessPieceAt(3, 4).getName().equals("Rat")) {
                                X = 3;
                                Y = 4;
                                x = 3;
                                y = 5;
                                c = 1;
                            } else if (getChessPieceAt(3, 6).getOwner() == PlayerColor.RED && getChessPieceAt(3, 6).getName().equals("Rat")) {
                                X = 3;
                                Y = 6;
                                x = 3;
                                y = 5;
                                c = 1;
                            } else if (getChessPieceAt(4, 4).getOwner() == PlayerColor.RED && getChessPieceAt(4, 4).getName().equals("Rat")) {
                                X = 4;
                                Y = 4;
                                x = 4;
                                y = 5;
                                c = 1;
                            } else if (getChessPieceAt(4, 6).getOwner() == PlayerColor.RED && getChessPieceAt(4, 6).getName().equals("Rat")) {
                                X = 4;
                                Y = 6;
                                x = 4;
                                y = 5;
                                c = 1;
                            } else if (getChessPieceAt(5, 4).getOwner() == PlayerColor.RED && getChessPieceAt(5, 4).getName().equals("Rat")) {
                                X = 5;
                                Y = 4;
                                x = 5;
                                y = 5;
                                c = 1;
                            } else if (getChessPieceAt(5, 6).getOwner() == PlayerColor.RED && getChessPieceAt(5, 6).getName().equals("Rat")) {
                                X = 5;
                                Y = 6;
                                x = 5;
                                y = 5;
                                c = 1;
                            } else if (getChessPieceAt(2, 5).getOwner() == PlayerColor.RED && getChessPieceAt(2, 5).getName().equals("Rat")) {
                                X = 2;
                                Y = 5;
                                x = 3;
                                y = 5;
                                c = 1;
                            }
                        }
                        //5:move my chess from enemy's attack/attack enemy's chess(sort by rank)
                        if(c==0){
                            int k = 0;


                            if (k == 0) {
                                for (int i = 0; i < 9; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        if (getChessPieceAt(i, j).getOwner() == PlayerColor.BLUE &&
                                                getChessPieceAt(i, j).getName().equals("Elephant")) {
                                            if (ifUnderThreat(i, j)&&!ifGoingUnderThreat(ifUnderThreatX(i, j),ifUnderThreatY(i, j),i,j)) {
                                                X = ifUnderThreatX(i, j);
                                                Y = ifUnderThreatY(i, j);
                                                x = i;
                                                y = j;
                                                k = 1;
                                            }
                                        }
                                        if (k == 1)
                                            break;
                                    }
                                    if (k == 1)
                                        break;
                                }
                            }

                            if(k==0) {
                                for (int i = 0; i < 9; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        if (getChessPieceAt(i, j).getOwner() == PlayerColor.RED &&
                                                getChessPieceAt(i, j).getName().equals("Elephant")) {
                                            if (ifUnderThreat(i, j)) {
                                                for (int m = 8; m > -1; m--) {
                                                    for (int n = 0; n < 7; n++) {
                                                        if (ifShouldMove(i, j, m, n)) {
                                                            X = i;
                                                            Y = j;
                                                            x = m;
                                                            y = n;
                                                            k = 1;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (k == 1)
                                            break;
                                    }
                                    if (k == 1)
                                        break;
                                }
                            }
                            if (k == 0) {
                                for (int i = 0; i < 9; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        if (getChessPieceAt(i, j).getOwner() == PlayerColor.BLUE &&
                                                getChessPieceAt(i, j).getName().equals("Lion")) {
                                            if (ifUnderThreat(i, j)&&!ifGoingUnderThreat(ifUnderThreatX(i, j),ifUnderThreatY(i, j),i,j)) {
                                                X = ifUnderThreatX(i, j);
                                                Y = ifUnderThreatY(i, j);
                                                x = i;
                                                y = j;
                                                k = 1;
                                            }
                                        }
                                        if (k == 1)
                                            break;
                                    }
                                    if (k == 1)
                                        break;
                                }
                            }
                            if (k == 0) {
                                for (int i = 0; i < 9; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        if (getChessPieceAt(i, j).getOwner() == PlayerColor.RED &&
                                                getChessPieceAt(i, j).getName().equals("Lion")) {
                                            if (ifUnderThreat(i, j)) {
                                                for (int m = 8; m >-1; m--) {
                                                    for (int n = 0; n < 7; n++) {
                                                        if (ifShouldMove(i, j, m, n)) {
                                                            X = i;
                                                            Y = j;
                                                            x = m;
                                                            y = n;
                                                            k = 1;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (k == 1)
                                            break;
                                    }
                                    if (k == 1)
                                        break;
                                }
                            }

                            if (k == 0) {
                                for (int i = 0; i < 9; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        if (getChessPieceAt(i, j).getOwner() == PlayerColor.BLUE &&
                                                getChessPieceAt(i, j).getName().equals("Tiger")) {
                                            if (ifUnderThreat(i, j)&&!ifGoingUnderThreat(ifUnderThreatX(i, j),ifUnderThreatY(i, j),i,j)) {
                                                X = ifUnderThreatX(i, j);
                                                Y = ifUnderThreatY(i, j);
                                                x = i;
                                                y = j;
                                                k = 1;
                                            }
                                        }
                                        if (k == 1)
                                            break;
                                    }
                                    if (k == 1)
                                        break;
                                }
                            }
                            if (k == 0) {
                                for (int i = 0; i < 9; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        if (getChessPieceAt(i, j).getOwner() == PlayerColor.RED &&
                                                getChessPieceAt(i, j).getName().equals("Tiger")) {
                                            if (ifUnderThreat(i, j)) {
                                                for (int m = 8; m >-1; m--) {
                                                    for (int n = 0; n < 7; n++) {
                                                        if (ifShouldMove(i, j, m, n)) {
                                                            X = i;
                                                            Y = j;
                                                            x = m;
                                                            y = n;
                                                            k = 1;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (k == 1)
                                            break;
                                    }
                                    if (k == 1)
                                        break;
                                }
                            }

                            if (k == 0) {
                                for (int i = 0; i < 9; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        if (getChessPieceAt(i, j).getOwner() == PlayerColor.BLUE &&
                                                getChessPieceAt(i, j).getName().equals("Leopard")) {
                                            if (ifUnderThreat(i, j)&&!ifGoingUnderThreat(ifUnderThreatX(i, j),ifUnderThreatY(i, j),i,j)) {
                                                X = ifUnderThreatX(i, j);
                                                Y = ifUnderThreatY(i, j);
                                                x = i;
                                                y = j;
                                                k = 1;
                                            }
                                        }
                                        if (k == 1)
                                            break;
                                    }
                                    if (k == 1)
                                        break;
                                }
                            }
                            if (k == 0) {
                                for (int i = 0; i < 9; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        if (getChessPieceAt(i, j).getOwner() == PlayerColor.RED &&
                                                getChessPieceAt(i, j).getName().equals("Leopard")) {
                                            if (ifUnderThreat(i, j)) {
                                                for (int m = 8; m >-1; m--) {
                                                    for (int n = 0; n < 7; n++) {
                                                        if (ifShouldMove(i, j, m, n)) {
                                                            X = i;
                                                            Y = j;
                                                            x = m;
                                                            y = n;
                                                            k = 1;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (k == 1)
                                            break;
                                    }
                                    if (k == 1)
                                        break;
                                }
                            }

                            if (k == 0) {
                                for (int i = 0; i < 9; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        if (getChessPieceAt(i, j).getOwner() == PlayerColor.BLUE &&
                                                getChessPieceAt(i, j).getName().equals("Rat")) {
                                            if (ifUnderThreat(i, j)&&!ifGoingUnderThreat(ifUnderThreatX(i, j),ifUnderThreatY(i, j),i,j)) {
                                                X = ifUnderThreatX(i, j);
                                                Y = ifUnderThreatY(i, j);
                                                x = i;
                                                y = j;
                                                k = 1;
                                            }
                                        }
                                        if (k == 1)
                                            break;
                                    }
                                    if (k == 1)
                                        break;
                                }
                            }
                            if (k == 0) {
                                for (int i = 0; i < 9; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        if (getChessPieceAt(i, j).getOwner() == PlayerColor.RED &&
                                                getChessPieceAt(i, j).getName().equals("Rat")) {
                                            if (ifUnderThreat(i, j)) {
                                                for (int m = 8; m >-1; m--) {
                                                    for (int n = 0; n < 7; n++) {
                                                        if (ifShouldMove(i, j, m, n)) {
                                                            X = i;
                                                            Y = j;
                                                            x = m;
                                                            y = n;
                                                            k = 1;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (k == 1)
                                            break;
                                    }
                                    if (k == 1)
                                        break;
                                }
                            }

                            if (k == 0) {
                                for (int i = 0; i < 9; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        if (getChessPieceAt(i, j).getOwner() == PlayerColor.BLUE &&
                                                getChessPieceAt(i, j).getName().equals("Wolf")) {
                                            if (ifUnderThreat(i, j)&&!ifGoingUnderThreat(ifUnderThreatX(i, j),ifUnderThreatY(i, j),i,j)) {
                                                X = ifUnderThreatX(i, j);
                                                Y = ifUnderThreatY(i, j);
                                                x = i;
                                                y = j;
                                                k = 1;
                                            }
                                        }
                                        if (k == 1)
                                            break;
                                    }
                                    if (k == 1)
                                        break;
                                }
                            }
                            if (k == 0) {
                                for (int i = 0; i < 9; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        if (getChessPieceAt(i, j).getOwner() == PlayerColor.RED &&
                                                getChessPieceAt(i, j).getName().equals("Wolf")) {
                                            if (ifUnderThreat(i, j)) {
                                                for (int m = 8; m >-1; m--) {
                                                    for (int n = 0; n < 7; n++) {
                                                        if (ifShouldMove(i, j, m, n)) {
                                                            X = i;
                                                            Y = j;
                                                            x = m;
                                                            y = n;
                                                            k = 1;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (k == 1)
                                            break;
                                    }
                                    if (k == 1)
                                        break;
                                }
                            }

                            if (k == 0) {
                                for (int i = 0; i < 9; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        if (getChessPieceAt(i, j).getOwner() == PlayerColor.BLUE &&
                                                getChessPieceAt(i, j).getName().equals("Dog")) {
                                            if (ifUnderThreat(i, j)&&!ifGoingUnderThreat(ifUnderThreatX(i, j),ifUnderThreatY(i, j),i,j)) {
                                                X = ifUnderThreatX(i, j);
                                                Y = ifUnderThreatY(i, j);
                                                x = i;
                                                y = j;
                                                k = 1;
                                            }
                                        }
                                        if (k == 1)
                                            break;
                                    }
                                    if (k == 1)
                                        break;
                                }
                            }
                            if (k == 0) {
                                for (int i = 0; i < 9; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        if (getChessPieceAt(i, j).getOwner() == PlayerColor.RED &&
                                                getChessPieceAt(i, j).getName().equals("Dog")) {
                                            if (ifUnderThreat(i, j)) {
                                                for (int m = 8; m >-1; m--) {
                                                    for (int n = 0; n < 7; n++) {
                                                        if (ifShouldMove(i, j, m, n)) {
                                                            X = i;
                                                            Y = j;
                                                            x = m;
                                                            y = n;
                                                            k = 1;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (k == 1)
                                            break;
                                    }
                                    if (k == 1)
                                        break;
                                }
                            }

                            if (k == 0) {
                                for (int i = 0; i < 9; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        if (getChessPieceAt(i, j).getOwner() == PlayerColor.BLUE &&
                                                getChessPieceAt(i, j).getName().equals("Cat")) {
                                            if (ifUnderThreat(i, j)&&!ifGoingUnderThreat(ifUnderThreatX(i, j),ifUnderThreatY(i, j),i,j)) {
                                                X = ifUnderThreatX(i, j);
                                                Y = ifUnderThreatY(i, j);
                                                x = i;
                                                y = j;
                                                k = 1;
                                            }
                                        }
                                        if (k == 1)
                                            break;
                                    }
                                    if (k == 1)
                                        break;
                                }
                            }
                            if (k == 0) {
                                for (int i = 0; i < 9; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        if (getChessPieceAt(i, j).getOwner() == PlayerColor.RED &&
                                                getChessPieceAt(i, j).getName().equals("Cat")) {
                                            if (ifUnderThreat(i, j)) {
                                                for (int m = 8; m >-1; m--) {
                                                    for (int n = 0; n < 7; n++) {
                                                        if (ifShouldMove(i, j, m, n)) {
                                                            X = i;
                                                            Y = j;
                                                            x = m;
                                                            y = n;
                                                            k = 1;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (k == 1)
                                            break;
                                    }
                                    if (k == 1)
                                        break;
                                }
                            }
                            if (k == 0) {
                                //7:Move my cat,dog,wolf,leopard to protect my trap
                                if (getChessPieceAt(0, 1).getOwner() == null && getChessPieceAt(1, 2).getOwner() == null) {
                                    int s = 0;
                                    for (int i = 0; i < 9; i++) {
                                        for (int j = 0; j < 4; j++) {
                                            if (getChessPieceAt(i, j).getName().equals("Cat") || getChessPieceAt(i, j).getName().equals("Dog")
                                                    || getChessPieceAt(i, j).getName().equals("Wolf") || getChessPieceAt(i, j).getName().equals("Leopard")) {
                                                if (i > 1) {
                                                    if (ifShouldMove(i, j, i - 1, j)) {
                                                        X = i;
                                                        Y = j;
                                                        x = i - 1;
                                                        y = j;
                                                        s = 1;
                                                    }
                                                } else if (j < 2) {
                                                    if (ifShouldMove(i, j, i, j + 1)) {
                                                        X = i;
                                                        Y = j;
                                                        x = i;
                                                        y = j + 1;
                                                        s = 1;
                                                    }
                                                } else if (ifShouldMove(i, j, i, j - 1)) {
                                                    X = i;
                                                    Y = j;
                                                    x = i;
                                                    y = j - 1;
                                                    s = 1;
                                                }
                                            }
                                            if (s == 1)
                                                break;
                                        }
                                        if (s == 1)
                                            break;
                                    }
                                } else if (getChessPieceAt(0, 5).getOwner() == null && getChessPieceAt(1, 4).getOwner() == null) {
                                    int s = 0;
                                    for (int i = 0; i < 9; i++) {
                                        for (int j = 6; j > 2; j--) {
                                            if (getChessPieceAt(i, j).getName().equals("Cat") || getChessPieceAt(i, j).getName().equals("Dog")
                                                    || getChessPieceAt(i, j).getName().equals("Wolf") || getChessPieceAt(i, j).getName().equals("Leopard")) {
                                                if (i > 1) {
                                                    if (ifShouldMove(i, j, i - 1, j)) {
                                                        X = i;
                                                        Y = j;
                                                        x = i - 1;
                                                        y = j;
                                                        s = 1;
                                                    }
                                                } else if (j > 4) {
                                                    if (ifShouldMove(i, j, i, j - 1)) {
                                                        X = i;
                                                        Y = j;
                                                        x = i;
                                                        y = j - 1;
                                                        s = 1;
                                                    }
                                                } else if (ifShouldMove(i, j, i, j + 1)) {
                                                    X = i;
                                                    Y = j;
                                                    x = i;
                                                    y = j + 1;
                                                    s = 1;
                                                }
                                            }
                                            if (s == 1)
                                                break;
                                        }
                                        if (s == 1)
                                            break;
                                    }
                                } else if (getChessPieceAt(1, 2).getOwner() == null && getChessPieceAt(1, 4).getOwner() == null) {
                                    int s = 0;
                                    for (int i = 1; i < 9; i++) {
                                        for (int j = 0; j < 7; j++) {
                                            if (getChessPieceAt(i, j).getName().equals("Cat") || getChessPieceAt(i, j).getName().equals("Dog")
                                                    || getChessPieceAt(i, j).getName().equals("Wolf") || getChessPieceAt(i, j).getName().equals("Leopard")) {
                                                if (i > 1) {
                                                    if (ifShouldMove(i, j, i - 1, j)) {
                                                        X = i;
                                                        Y = j;
                                                        x = i - 1;
                                                        y = j;
                                                        s = 1;
                                                    }
                                                } else if (j > 3) {
                                                    if (ifShouldMove(i, j, i, j - 1)) {
                                                        X = i;
                                                        Y = j;
                                                        x = i;
                                                        y = j - 1;
                                                        s = 1;
                                                    }
                                                } else if (ifShouldMove(i, j, i, j + 1)) {
                                                    X = i;
                                                    Y = j;
                                                    x = i;
                                                    y = j + 1;
                                                    s = 1;
                                                }
                                            }
                                            if (s == 1)
                                                break;
                                        }
                                        if (s == 1)
                                            break;
                                    }
                                } else {


                                    //9:move tiger and lion across the river

                                    int d = 0;
                                    if (getChessPieceAt(2, 1).getOwner() == PlayerColor.RED &&
                                            (getChessPieceAt(2, 1).getName().equals("Lion") || getChessPieceAt(2, 1).getName().equals("Tiger"))) {
                                        if (ifShouldMove(2, 1, 6, 1)) {
                                            X = 2;
                                            Y = 1;
                                            x = 6;
                                            y = 1;
                                            d = 1;
                                        }
                                    }
                                    if (getChessPieceAt(2, 2).getOwner() == PlayerColor.RED && d == 0 &&
                                            (getChessPieceAt(2, 2).getName().equals("Lion") || getChessPieceAt(2, 2).getName().equals("Tiger"))) {
                                        if (ifShouldMove(2, 2, 6, 2)) {
                                            X = 2;
                                            Y = 2;
                                            x = 6;
                                            y = 2;
                                            d = 1;
                                        }
                                    }
                                    if (getChessPieceAt(2, 4).getOwner() == PlayerColor.RED && d == 0 &&
                                            (getChessPieceAt(2, 4).getName().equals("Lion") || getChessPieceAt(2, 4).getName().equals("Tiger"))) {
                                        if (ifShouldMove(2, 4, 6, 4)) {
                                            X = 2;
                                            Y = 4;
                                            x = 6;
                                            y = 4;
                                            d = 1;
                                        }
                                    }
                                    if (getChessPieceAt(2, 5).getOwner() == PlayerColor.RED && d == 0 &&
                                            (getChessPieceAt(2, 5).getName().equals("Lion") || getChessPieceAt(2, 5).getName().equals("Tiger"))) {
                                        if (ifShouldMove(2, 5, 6, 5)) {
                                            X = 2;
                                            Y = 5;
                                            x = 6;
                                            y = 5;
                                            d = 1;
                                        }
                                    }
                                    //10:move rat forward
                                    if (d == 0) {
                                        int e = 0;
                                        for (int j = 0; j < 7; j++) {
                                            if (e == 1)
                                                break;
                                            if (getChessPieceAt(2, j).getOwner() == PlayerColor.RED && getChessPieceAt(2, j).getName().equals("Rat")) {
                                                if (ifShouldMove(2, j, 3, j)) {
                                                    X = 2;
                                                    Y = j;
                                                    x = 3;
                                                    y = j;
                                                    e = 1;
                                                } else if (ifShouldMove(2, j, 2, j + 1)) {
                                                    X = 2;
                                                    Y = j;
                                                    x = 2;
                                                    y = j + 1;
                                                    e = 1;
                                                } else if (ifShouldMove(2, j, 2, j - 1)) {
                                                    X = 2;
                                                    Y = j;
                                                    x = 2;
                                                    y = j - 1;
                                                    e = 1;
                                                }
                                            }
                                        }


                                        //11:move lion,tiger,leopard forward

                                        if (e == 0) {
                                            int s = 0;
                                            for (int i = 0; i < 9; i++) {
                                                if (s == 1)
                                                    break;
                                                for (int j = 0; j < 7; j++) {
                                                    if (s == 1)
                                                        break;
                                                    if (getChessPieceAt(i, j).getOwner() == PlayerColor.RED && (
                                                            getChessPieceAt(i, j).getName().equals("Lion") ||
                                                                    getChessPieceAt(i, j).getName().equals("Tiger") ||
                                                                    getChessPieceAt(i, j).getName().equals("Leopard"))
                                                    ) {
                                                        if (ifShouldMove(i, j, i + 1, j)) {
                                                            X = i;
                                                            Y = j;
                                                            x = i + 1;
                                                            y = j;
                                                            s = 1;
                                                        } else if (ifShouldMove(i, j, i, j + 1)) {
                                                            X = i;
                                                            Y = j;
                                                            x = i;
                                                            y = j + 1;
                                                            s = 1;
                                                        } else if (ifShouldMove(i, j, i, j - 1)) {
                                                            X = i;
                                                            Y = j;
                                                            x = i;
                                                            y = j - 1;
                                                            s = 1;
                                                        }else if (ifShouldMove(i, j, i, j + 3)) {
                                                            X = i;
                                                            Y = j;
                                                            x = i;
                                                            y = j + 3;
                                                            s = 1;
                                                        } else if (ifShouldMove(i, j, i, j - 3)) {
                                                            X = i;
                                                            Y = j;
                                                            x = i;
                                                            y = j - 3;
                                                            s = 1;
                                                        }
                                                    }

                                                }

                                            }
                                            //12:Random
                                            if (s == 0) {
                                                int t = 0;
                                                while (t == 0) {
                                                    int w = 0;
                                                    w = (int) (Math.random() * 8);
                                                    for (int j = 0; j < 7; j++) {
                                                        if (getChessPieceAt(w, j).getOwner() == PlayerColor.RED) {
                                                            for (int m = 0; m < 9; m++) {
                                                                for (int n = 0; n < 7; n++) {
                                                                    if (ifShouldMove(w, j, m, n)) {
                                                                        X = w;
                                                                        Y = j;
                                                                        x = m;
                                                                        y = n;
                                                                        t = 1;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (t == 1)
                                                            break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

        ChessboardPoint src=new ChessboardPoint(X,Y);
        ChessboardPoint dest=new ChessboardPoint(x,y);
        int judge=0;
        if(isValidCapture(src,dest)) {
            captureChessPiece(src, dest);
            judge=1;
        }
        else {
            moveChessPiece(src, dest);
        }
        int[] Q= {X,Y,x,y,judge};
        for(int i=0;i<Q.length;i++){
            System.out.println(Q[i]);
        }
        return  Q;
    }



}

