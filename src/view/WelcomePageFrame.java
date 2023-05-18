package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePageFrame extends JFrame {
    public WelcomePageFrame() {
        setTitle("欢迎页面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // 创建欢迎标语标签
        JLabel welcomeLabel = new JLabel("欢迎进入棋类游戏");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // 创建新游戏按钮
        JButton newGameButton = new JButton("新游戏");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 切换到新游戏页面
                dispose(); // 关闭当前页面
                NewGamePageFrame newGamePageFrame = new NewGamePageFrame();
                newGamePageFrame.setVisible(true);
            }
        });

        // 创建查看棋谱按钮
        JButton viewRecordsButton = new JButton("查看棋谱");
        viewRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 显示本地的游玩牌谱
                // 实现逻辑根据需求进行
                JOptionPane.showMessageDialog(WelcomePageFrame.this, "显示游玩牌谱");
            }
        });

        // 创建观战按钮
        JButton watchGameButton = new JButton("观战");
        watchGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 进入观战列表页面
                // 实现逻辑根据需求进行
                JOptionPane.showMessageDialog(WelcomePageFrame.this, "进入观战列表");
            }
        });

        // 创建退出游戏按钮
        JButton exitButton = new JButton("退出游戏");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 退出程序
                System.exit(0);
            }
        });

        // 创建面板，并设置布局和添加组件
        JPanel panel = new JPanel(new GridLayout(6, 1));
        panel.add(welcomeLabel);
        panel.add(newGameButton);
        panel.add(viewRecordsButton);
        panel.add(watchGameButton);
        panel.add(exitButton);

        // 将面板添加到窗口中心
        add(panel, BorderLayout.CENTER);
    }
}
