import controller.GameController;
import javax.swing.SwingUtilities;
import model.Chessboard;
import view.ChessGameFrame;
import view.Test;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        String musicPath = "D:\\南科大\\Java\\project\\resource\\music\\October.mp3";
        Test.AudioPlay2 audioPlayer = new Test.AudioPlay2(musicPath);
        audioPlayer.start();
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            new GameController(mainFrame.getChessboardComponent(), new Chessboard());
            mainFrame.setVisible(true);
        });
    }
}
