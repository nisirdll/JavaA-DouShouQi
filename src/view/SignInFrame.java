package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class SignInFrame extends JFrame implements ActionListener{
    JLabel l1, l2, l3;
    JTextField t1;
    JPasswordField p1;
    JButton b1, b2;
    public SignInFrame() {
        setTitle("登录");
        setVisible(true);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        l1 = new JLabel("登录界面");
        l1.setForeground(Color.blue);
        l1.setFont(new Font("宋体", Font.BOLD, 20));
        l1.setBounds(100, 30, 400, 30);
        add(l1);
        l2 = new JLabel("用户名");
        l2.setBounds(80, 70, 200, 30);
        add(l2);
        l3 = new JLabel("密码");
        l3.setBounds(80, 110, 200, 30);
        add(l3);
        t1 = new JTextField();
        t1.setBounds(300, 70, 200, 30);
        add(t1);
        p1 = new JPasswordField();
        p1.setBounds(300, 110, 200, 30);
        add(p1);
        b1 = new JButton("登录");
        b1.setBounds(50, 160, 100, 30);
        add(b1);
        b2 = new JButton("取消");
        b2.setBounds(170, 160, 100, 30);
        add(b2);
        b1.addActionListener(this);
        b2.addActionListener(this);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            String username = t1.getText();
            String password = p1.getText();
            if (username.equals("admin") && password.equals("admin")) {
                JOptionPane.showMessageDialog(this, "登录成功");
            } else {
                JOptionPane.showMessageDialog(this, "登录失败");
            }
        } else if (e.getSource() == b2) {
            t1.setText("");
            p1.setText("");
            t1.requestFocus();
        }
    }


}


