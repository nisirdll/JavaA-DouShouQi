package controller;


import listener.GameListener;
import model.Chessboard;
import model.ChessboardPoint;
import model.Constant;
import model.PlayerColor;
import view.CellComponent;
import view.ChessboardComponent;
import view.ElephantChessComponent;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener {



    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;
        // Register the controller to the view
        // so that the view can call the controller's method
        // when the view receives the user's request
        // (in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece())


        view.registerController(this);
        // Initialize the chessboard
        initialize();
        view.initiateChessComponent(model);

        view.repaint();

    }

    private void initialize() {
        //这是一个初始化的private 函数
        //
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {


            }
        }
    }

    // after a valid move swap the player
    private void swapColor() {


        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        //
    }

    private boolean win() {
        // TODO: Check the board if there is a winner
        //check whether the game is over
        //if the game is over, return true
        //else

        return false;
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {

        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
           // System.out.println("valid move");
            //如果这个点是valid move
            //那么就把这个点的棋子移动到这个点
            //然后把这个点的棋子的component移动到这个点
            //然后把selectedPoint设为null
            //然后swapColor
            //然后repaint

            model.moveChessPiece(selectedPoint, point);

            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            view.repaint();
            // TODO: if the chess enter Dens or Traps and so on

        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ElephantChessComponent component) {

        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                component.repaint();
            }
            //如果这个点是空的
            //那么就把这个点设为selectedPoint
            //然后把这个点的棋子的component设为selected
            //然后repaint
            //然后return

        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            //如果这个点是selectedPoint
            //那么就把selectedPoint设为null
            //然后把这个点的棋子的component设为unselected
            //然后repaint
            //然后return

        }
        // TODO: Implement capture function


    }
}
