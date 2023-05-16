package view;

import java.awt.*;

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
            int x = rect.width*1/5 + 15;
            int y = rect.height*1/5 + 15;
            users.setBounds(x, y, size.width, size.height);
        }
        if(codes.isVisible()){
            Dimension size = codes.getPreferredSize();
            int x = rect.width*1/5 + 15;
            int y = rect.height*1/2 + 42;
            codes.setBounds(x, y, size.width, size.height);
        }
        if(userfield.isVisible()){
            Dimension size = userfield.getPreferredSize();
            int x = rect.width*1/5 + 30;
            int y = rect.height*1/2 + 1;
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
        if(button.isVisible()){
            Dimension size = button.getPreferredSize();
            int x = (rect.width - size.width)/2 + 12;
            int y = rect.height*1/2 + 89;
            button.setBounds(x, y, size.width, size.height);
        }
        if(close.isVisible()){
            Dimension size = close.getPreferredSize();
            int x = rect.width - size.width ;
            int y = 0;
            close.setBounds(x, y, size.width, size.height);
        }
    }
}
