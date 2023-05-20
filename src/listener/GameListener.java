package listener;

import model.ChessboardPoint;
import view.AnimalChessComponent;
import view.CellComponent;
import view.ChessboardComponent;

public interface GameListener {

    void onPlayerClickCell(ChessboardPoint point, CellComponent component);


    // click an empty cell


    void onPlayerClickChessPiece(ChessboardPoint point, AnimalChessComponent component);

}
