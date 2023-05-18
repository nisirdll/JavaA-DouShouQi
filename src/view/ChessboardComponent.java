package view;


import controller.GameController;
import model.Cell;
import model.ChessPiece;
import model.Chessboard;
import model.ChessboardPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private final Set<ChessboardPoint> densCell = new HashSet<>();
    private final Set<ChessboardPoint> trapCell = new HashSet<>();

    private GameController gameController;

    private Color Brown = new Color (165, 42, 42, 255) ;//Trap的颜色
    private Color Yellow = new Color(255, 255, 0); ;//Den的颜色
    private Color TableColor = new Color(204,102,0);
    private Color RiverColor = new Color(0,128,255);

    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);

        initiateGridComponents();
    }


    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                gridComponents[i][j].removeAll();
                gridComponents[i][j].revalidate();
            }
        }
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                // TODO: Implement the initialization checkerboard
                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    System.out.println(chessPiece.getOwner());
                    gridComponents[i][j].add(new AnimalChessComponent(chessPiece.getOwner(), CHESS_SIZE, grid[i][j].getPiece().getName(), grid[i][j].getPiece().getAddress()));//Where animals find its initial place

                }
            }
        }

    }

    public void initiateGridComponents() {

        riverCell.add(new ChessboardPoint(3,1));
        riverCell.add(new ChessboardPoint(3,2));
        riverCell.add(new ChessboardPoint(4,1));
        riverCell.add(new ChessboardPoint(4,2));
        riverCell.add(new ChessboardPoint(5,1));
        riverCell.add(new ChessboardPoint(5,2));

        riverCell.add(new ChessboardPoint(3,4));
        riverCell.add(new ChessboardPoint(3,5));
        riverCell.add(new ChessboardPoint(4,4));
        riverCell.add(new ChessboardPoint(4,5));
        riverCell.add(new ChessboardPoint(5,4));
        riverCell.add(new ChessboardPoint(5,5));
        trapCell.add(new ChessboardPoint(0, 2));
        trapCell.add(new ChessboardPoint(0, 4));
        trapCell.add(new ChessboardPoint(1, 3));
        trapCell.add(new ChessboardPoint(7, 3));
        trapCell.add(new ChessboardPoint(8, 2));
        trapCell.add(new ChessboardPoint(8, 4));

        densCell.add(new ChessboardPoint(0, 3));
        densCell.add(new ChessboardPoint(8, 3));

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(RiverColor, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else if (trapCell.contains(temp)) {
                    cell = new CellComponent(Brown, calculatePoint(i, j), CHESS_SIZE);
                    cell.isTrap = true;
                    this.add(cell);
                } else if (densCell.contains(temp)) {
                    cell = new CellComponent(Yellow, calculatePoint(i, j), CHESS_SIZE);
                    cell.isDen = true;
                    this.add(cell);
                }else {
                    cell = new CellComponent(TableColor, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, AnimalChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }

    public AnimalChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        AnimalChessComponent chess = (AnimalChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    private CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/CHESS_SIZE +  ", " +point.x/CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/CHESS_SIZE, point.x/CHESS_SIZE);
    }
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (AnimalChessComponent) clickedComponent.getComponents()[0]);
            }
        }
    }
}
//测试git使用

//这段代码定义了一个名为ChessboardComponent的类，它继承自JComponent。该类表示面板上的棋盘组件对象。
// 在构造函数中，根据传入的参数chessSize计算出棋子大小，并设置组件大小和布局方式。initiateGridComponents()方法用于初始化网格单元格并将其添加到面板上。
// initiateChessComponent(Chessboard chessboard)方法用于根据传入的棋盘信息初始化棋子组件并将其添加到相应位置上。
//
//registerController(GameController gameController)方法用于注册游戏控制器以便处理玩家点击事件。
// setChessComponentAtGrid(ChessboardPoint point, ElephantChessComponent chess)和removeChessComponentAtGrid(ChessboardPoint point)方法分别用于向指定位置添加或移除象棋组件。
//
//processMouseEvent(MouseEvent e)方法则是处理鼠标事件的核心部分，当玩家点击某个单元格时会触发此事件，
// 并通过调用gameController.onPlayerClickCell()或gameController.onPlayerClickChessPiece()来通知游戏控制器进行后续操作。
//
//其中还有一些辅助性质的私有成员变量和函数，如riverCell集合表示河流所在位置、getGridComponentAt(ChessboardPoint point)获取指定位置网格单元格等等。
