package view;

import javax.swing.*;

public class JFrameTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JFrame Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
