package view;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel {
    private Color background;
    public boolean isTrap = false;
    public boolean isDen = false;

    public boolean canStep;

    public CellComponent(Color background, Point location, int size) {
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
        this.background = background;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(background);
        g.fillRect(1, 1, this.getWidth()-1, this.getHeight()-1);
    }
    private void initLabels(){
        for(int i = 0; i<9; i++){
            for(int j = 0; j<7; j++){
                //创建JLabel对象
            }
        }
    }
    public void setBackground(Color background) {
        this.background = background;
    }
    public Color getBackground() {
        return background;
    }

}
