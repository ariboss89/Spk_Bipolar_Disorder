/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class Background extends JPanel {

    private Image image;

    public Background() {
        image = new ImageIcon(getClass().getResource("/Image/24.jpg")).getImage();
    }

    public static void main(String[] args) {

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics gd = (Graphics2D) g.create();

        gd.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        // menggambar image
        gd.dispose();

    }
}
