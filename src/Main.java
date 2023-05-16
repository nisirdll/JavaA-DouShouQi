import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;
import view.Register;
import view.SignInFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
            mainFrame.setVisible(true);
        });
        SignInFrame login = new SignInFrame();
        Register register = new Register();
    }
}
