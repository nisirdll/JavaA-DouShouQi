import controller.GameController;
import javax.swing.SwingUtilities;
import model.Chessboard;
import view.ChessGameFrame;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            new GameController(mainFrame.getChessboardComponent(), new Chessboard());
            mainFrame.setVisible(true);
        });
    }
}
