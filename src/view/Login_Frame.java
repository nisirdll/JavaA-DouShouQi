package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class Login_Frame extends JFrame{
    int x,y;
    JLabel users = new JLabel();
    JLabel codes = new JLabel();
    JButton close = new JButton();
    JLabel bg = new JLabel();
    JLabel tx = new JLabel();
    JLabel zh = new Creat_label("2023",Color.white);
    JTextField userfield = new Create_textfield(10);
    JPasswordField codefield = new Creat_codefield(10);
    JButton login = new Creat_Login_Button("登录");

    URL url0 = getClass().getResource("/img/登录.png");
    URL url1 = getClass().getResource("/img/登录1.png");
    URL url2 = getClass().getResource("/img/登录2.png");
    URL url3 = getClass().getResource("/img/登录3.png");
    URL url4 = getClass().getResource("/img/登录4.png");
    Icon users_icon = new ImageIcon(url0);
    Icon users_icon1 = new ImageIcon(url1);
    Icon users_icon2 = new ImageIcon(url2);
    Icon users_icon3 = new ImageIcon(url3);
    Icon users_icon4 = new ImageIcon(url4);
    int left,top;
    public Login_Frame(){
        setSize(425,330);
        setUndecorated(true);
        setVisible(true);

        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                x = e.getX();
                y = e.getY();
            }
        });
        addMouseListener(new MouseAdapter(){
            public void mouseReleased(MouseEvent e){
                left = e.getXOnScreen();
                top = e.getYOnScreen();
                setLocation(left-x,top-y);
            }
        });
        JPanel jPanel = new JPanel();
        this.setContentPane(jPanel);

        users.setIcon(users_icon);

        codes.setIcon(users_icon1);
        close.setIcon(users_icon2);
        bg.setIcon(users_icon3);
        tx.setIcon(users_icon4);

        jPanel.setBackground(Color.white);
        jPanel.setOpaque(true);
        jPanel.setLayout(new SimpleLayout());

        MatteBorder matteBorder = new MatteBorder(0, 0, 0, 1,Color.gray);
        userfield.setBorder(matteBorder);
        jPanel.add(userfield);
        jPanel.add(users);
        codefield.setBorder((Border) matteBorder);
        jPanel.add(codefield);
        Border border = BorderFactory.createEmptyBorder(1,1,1,1);
        close.setFocusPainted(false);
        close.setPreferredSize(new Dimension(50, 30));
        close.setBorder(border);
        close.setBackground(new Color(0,131,255));
        close.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e){
                close.setBackground(Color.red);
            }
            public void mouseExited(MouseEvent e){
                close.setBackground(new Color(0,131,255));
            }
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
    public class SimpleLayout extends Layout{

        @Override
        public void layoutContainer(Container parent){
            Rectangle rect = parent.getBounds();
            if(bg.isVisible()){
                Dimension size = bg.getPreferredSize();
                bg.setBounds(0, 0, rect.width, rect.height);
            }
            if(users.isVisible()){
                Dimension size = users.getPreferredSize();
                int x = rect.width* 1/5 + 15;
                int y = rect.height* 1/5 + 15;
                users.setBounds(x, y, size.width, size.height);
            }
            if(codes.isVisible()){
                Dimension size = codes.getPreferredSize();
                int x = rect.width* 1/5 + 15;
                int y = rect.height* 1/2 + 42;
                codes.setBounds(x, y, size.width, size.height);
            }
            if(userfield.isVisible()){
                Dimension size = userfield.getPreferredSize();
                int x = rect.width* 1/5 + 30;
                int y = rect.height* 1/2 + 1;
                userfield.setBounds(x, y, 215, 20);
            }
            if(codefield.isVisible()){
                Dimension size = codefield.getPreferredSize();
                int x = rect.width*1/5 + 30;
                int y = rect.height*1/2 + 39;
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
            if(login.isVisible()){
                Dimension size = login.getPreferredSize();
                int x = (rect.width - size.width)/2 + 12;
                int y = rect.height*1/2 + 89;
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
