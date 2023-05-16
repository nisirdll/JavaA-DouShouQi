package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Register extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4, l5, l6, l7;
    JTextField t1, t2, t3, t4, t5, t6, t7;
    JButton b1, b2;

    public Register() {
        setTitle("注册");
        setVisible(true);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        l1 = new JLabel("注册界面");
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
        l4 = new JLabel("确认密码");
        l4.setBounds(80, 150, 200, 30);
        add(l4);
        l5 = new JLabel("姓名");
        l5.setBounds(80, 190, 200, 30);
        add(l5);
        l6 = new JLabel("性别");
        l6.setBounds(80, 230, 200, 30);
        add(l6);
        l7 = new JLabel("年龄");
        l7.setBounds(80, 270, 200, 30);
        add(l7);
        t1 = new JTextField();
        t1.setBounds(300, 70, 200, 30);
        add(t1);
        t2 = new JTextField();
        t2.setBounds(300, 110, 200, 30);
        add(t2);
        t3 = new JTextField();
        t3.setBounds(300, 150, 200, 30);
        add(t3);
        t4 = new JTextField();
        t4.setBounds(300, 190, 200, 30);
        add(t4);
        t5 = new JTextField();
        t5.setBounds(300, 230, 200, 30);
        add(t5);
        t6 = new JTextField();
        t6.setBounds(300, 270, 200, 30);
        add(t6);
        b1 = new JButton("注册");
        b1.setBounds(50, 320, 100, 30);
        add(b1);
        b2 = new JButton("取消");
        b2.setBounds(170, 320, 100, 30);
        add(b2);
        b1.addActionListener(this);
        b2.addActionListener(this);
        setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = t1.getText();
        String password = t2.getText();
        if(e.getSource() == b1){
            if(username.equals("") || password.equals("")){
                JOptionPane.showMessageDialog(this, "用户名或密码不能为空");
            }else{
                JOptionPane.showMessageDialog(this, "注册成功");
            }
        }else if(e.getSource() == b2){
            t1.setText("");
            t2.setText("");
            t3.setText("");
            t4.setText("");
            t5.setText("");
            t6.setText("");

        }
    }

}
