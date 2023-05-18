package view;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class AnimalChessComponent extends JComponent {
    private PlayerColor owner;
    private boolean selected;
    private String name;
    private String address;
    private ImageIcon pngImage;

    public AnimalChessComponent(PlayerColor owner, int size, String name, String address) {
        this.owner = owner;
        this.selected = false;
        this.name = name;
        this.address = address;
        pngImage = new ImageIcon(getClass().getResource(address));
        setSize(size / 2, size / 2);
        setLocation(0, 0);
        setVisible(true);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the PNG image
        if (pngImage != null) {
            if (owner.getColor() == Color.BLUE)
                g.setColor(new Color(41, 6, 199, 255));
            else
                g.setColor(new Color(255, 0, 0, 255));
            g.fillOval(0, 0, getWidth(), getWidth());
            g.drawImage(pngImage.getImage(), 11, 11, getWidth() - 22, getHeight() - 22, this);
        }
    }
}
