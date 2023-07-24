package view;

import model.Physical;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Camera extends JPanel {
    public void paintComponent(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;

        /* Draw the background */
        g2d.drawImage(Ground.imgTerrain, 0, 0, null);

        for (Display object : Display.displayableMap.values()) {
            object.display(g2d);
        }
    }
}
