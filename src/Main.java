import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;
import view.Login_Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        // 显示登录界面
        SwingUtilities.invokeLater(() -> {
            Login_Frame loginFrame = new Login_Frame();
            loginFrame.setVisible(true);
        });

        // 登录成功后显示主界面
        Login_Frame.setLoginButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建主窗口
                SwingUtilities.invokeLater(() -> {
                    ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                    GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
                    mainFrame.setVisible(true);
                });
            }
        });
    }
}

