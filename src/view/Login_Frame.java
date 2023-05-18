package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class Login_Frame extends JFrame{
    int x,y;
    JLabel users = new JLabel();  //这是用户图标

    JLabel codes = new JLabel();  //这是密码图标
    JButton close = new JButton();  //这是关闭图标
    JLabel bg = new JLabel();   //这是背景图
    JLabel tx = new JLabel();   //这是头像图标
    JLabel zh = new Creat_label("2023",Color.white);
    JTextField userfield = new Create_textfield(10);
    JPasswordField codefield = new Creat_codefield(10);
    static JButton login = new Creat_Login_Button("登录");

    URL url0 = getClass().getResource("/img/登录.png");   //这是用户图标
    URL url1 = getClass().getResource("/img/登录密码.png");   //这是密码图表
    URL url2 = getClass().getResource("/img/登录2.png");   ///这是关闭图标
    URL url3 = getClass().getResource("/img/OIP.jpg");    //这是背景图
    URL url4 = getClass().getResource("/img/登录4.png");     //这是头像图标
    Icon users_icon = new ImageIcon(url0);
    Icon codes_icon = new ImageIcon(url1);
    Icon close_icon = new ImageIcon(url2);
    Icon bg_icon = new ImageIcon(url3);
    Icon tx_icon = new ImageIcon(url4);
    int left,top;

    public static void setLoginButtonListener(ActionListener actionListener) {
        login.addActionListener(actionListener);
    }


    public Login_Frame(){
        setSize(450,300);
        setUndecorated(true);
        setVisible(true);
        //设置透明度
        setOpacity(0.9f);

        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                x = e.getX();
                y = e.getY();
            }
        });
        addMouseListener(new MouseAdapter(){
            public void mouseReleased(MouseEvent e){
                left = getLocation().x;
                top = getLocation().y;
                setLocation(left+e.getX()-x, top+e.getY()-y);
            }
        });
        JPanel jPanel = new JPanel();   //这是主面板
        this.setContentPane(jPanel);   //取代原来的面板

        users.setIcon(users_icon);

        codes.setIcon(codes_icon);
        close.setIcon(close_icon);
        bg.setIcon(bg_icon);
        tx.setIcon(tx_icon);

        jPanel.setBackground(Color.white);
        jPanel.setOpaque(true);
        jPanel.setLayout(new SimpleLayout());

        MatteBorder matteBorder = new MatteBorder(0, 0, 0, 1,Color.gray);
        userfield.setBorder(matteBorder);
        jPanel.add(userfield);
        jPanel.add(users);
        codefield.setBorder( matteBorder);
        jPanel.add(codefield);
        Border border = BorderFactory.createEmptyBorder(1,1,1,1);
        close.setFocusPainted(false);
        close.setPreferredSize(new Dimension(50, 30));

        close.setBackground(new Color(0,131,255));
        close.setBorder(border);
        close.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e){
                close.setBackground(Color.red);
            }
            public void mouseExited(MouseEvent e){
                close.setBackground(new Color(0,131,255));
            }
        });

        close.addActionListener((e)->{
            System.exit(0);
        });

        jPanel.add(close);
        jPanel.add(codes);
        jPanel.add(bg);
        jPanel.add(tx);
        jPanel.add(zh);
        jPanel.add(login);
        login.addActionListener((e)->{
            String names = userfield.getText();
            @SuppressWarnings("deprecation")
            String codes = codefield.getText();
            users []yonghu = new users[5];
            for(int i = 0;i<5;i++){
                yonghu[i] = new users();
            }
            yonghu[0].users="123";
            yonghu[1].users="test2";
            yonghu[2].users="test3";
            yonghu[3].users="test4";
            yonghu[4].users="test5";
            int count = 0;
            for(int i = 0;i<5;i++){
                if(names.equals(yonghu[i].users)&&codes.equals(yonghu[i].codes)){
                    count++;
                }
            }
            if(count==1){
                JOptionPane.showConfirmDialog(jPanel,"登录成功","提示",JOptionPane.DEFAULT_OPTION);
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(jPanel,"登录失败","提示",JOptionPane.WARNING_MESSAGE);

            }

        });


    }



    public class Create_textfield extends JTextField {
        public Create_textfield(int i) {
            super(i);
            setFont(new Font("宋体", 1, 15));
            setPreferredSize(new Dimension(215, 30));

        }
    }
    public class Creat_label extends JLabel {  //这是创建标签的类
        public Creat_label(String name, Color color) {
            super(name);
            setFont(new Font("微软雅黑", 1, 40));
            setForeground(color);  //设置字体颜色
            setHorizontalAlignment(SwingConstants.CENTER);  //设置居中
        }
    }
    public class Creat_codefield extends JPasswordField {
        public Creat_codefield(int i) {
            super(i);
            setFont(new java.awt.Font("宋体", 1, 15));
            setPreferredSize(new java.awt.Dimension(215, 30));
        }
    }


    public static class Creat_Login_Button extends JButton {
        public Creat_Login_Button(String text) {
            super(text);
            setBackground(new Color(0,191,255));
            setPreferredSize(new Dimension(215, 37));
            setFont(new Font("微软雅黑", 1, 15));
            setForeground(Color.white);
            setFocusPainted(false);
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }


    public class SimpleLayout extends Layout{

        @Override
        public void layoutContainer(Container parent){
            Rectangle rect = parent.getBounds();
            if(bg.isVisible()){
                Dimension size = bg.getPreferredSize();
                bg.setBounds(0, -5, size.width, size.height);
            }
            if(users.isVisible()){
                Dimension size = users.getPreferredSize();
                int x = rect.width/5 + 15;
                int y = rect.height/2;
                users.setBounds(x, y, size.width, size.height);
            }
            if(codes.isVisible()){
                Dimension size = codes.getPreferredSize();
                int x = rect.width/5 + 15;
                int y = rect.height/2 + 42;
                codes.setBounds(x, y, size.width, size.height);
            }
            if(userfield.isVisible()){
                Dimension size = userfield.getPreferredSize();
                int x = rect.width/5 + 30;
                int y = rect.height/2 + 1;
                userfield.setBounds(x, y, 215, 20);
            }
            if(codefield.isVisible()){
                Dimension size = codefield.getPreferredSize();
                int x = rect.width/5 + 30;
                int y = rect.height/2 + 39;
                codefield.setBounds(x, y, 215, 20);
            }
            if(tx.isVisible()){
                Dimension size = tx.getPreferredSize();
                int x = (rect.width - size.width)/3 +5;
                int y = 44;
                tx.setBounds(x, y, size.width, size.height);
            }
            if(zh.isVisible()){
                Dimension size = zh.getPreferredSize();
                int x = (rect.width - size.width)/3 + 85;
                int y = 42;
                zh.setBounds(x, y, size.width, size.height);
            }
            if(zh.isVisible()){
                Dimension size = zh.getPreferredSize();
                int x = (rect.width - size.width)/3 + 5;
                int y = 44;
                zh.setBounds(x, y, size.width, size.height);
            }
            if(login.isVisible()){
                Dimension size = login.getPreferredSize();
                int x = (rect.width - size.width)/2 + 12;
                int y = rect.height/2 + 89;
                login.setBounds(x, y, size.width, size.height);
            }
            if(close.isVisible()){
                Dimension size = close.getPreferredSize();
                int x = rect.width - size.width ;
                int y = 0;
                close.setBounds(x, y, size.width, size.height);
            }
        }
    }


}
