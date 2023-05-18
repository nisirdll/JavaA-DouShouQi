package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGamePageFrame extends JFrame {
    public NewGamePageFrame() {
        setTitle("新游戏");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // 创建联网对局按钮
        JButton onlineGameButton = new JButton("联网对局");
        onlineGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 处理联网对局逻辑
                // 实现逻辑根据需求进行
                JOptionPane.showMessageDialog(NewGamePageFrame.this, "开始联网对局");
            }
        });

        // 创建人机对战按钮
        JButton vsAIButton = new JButton("人机对战");
        vsAIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 处理人机对战逻辑
                // 实现逻辑根据需求进行
                JOptionPane.showMessageDialog(NewGamePageFrame.this, "开始人机对战");
            }
        });

        // 创建友人对战按钮
        JButton vsFriendButton = new JButton("友人对战");
        vsFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 处理友人对战逻辑
                // 实现逻辑根据需求进行
                JOptionPane.showMessageDialog(NewGamePageFrame.this, "开始友人对战");
            }
        });

        // 创建面板，并设置布局和添加组件
        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(new JLabel("请选择对局类型："));
        panel.add(onlineGameButton);
        panel.add(vsAIButton);
        panel.add(vsFriendButton);

        // 将面板添加到窗口中心
        add(panel, BorderLayout.CENTER);
    }
}
