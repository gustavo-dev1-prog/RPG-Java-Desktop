package com.rpg.view.components;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BackgroundPanel extends JPanel {

    private Image backgroundImage;

    public BackgroundPanel(String path) {
        URL location = getClass().getResource(path);

        if (location == null) {
            System.err.println("❌ IMAGEM NÃO ENCONTRADA: " + path);
        } else {
            System.out.println("✅ IMAGEM CARREGADA: " + location);
            backgroundImage = new ImageIcon(location).getImage();
        }

        setLayout(new BorderLayout());
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            Graphics2D g2 = (Graphics2D) g.create();

            
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

          
            g2.setComposite(AlphaComposite.SrcOver.derive(0.45f));
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.dispose();
        }
    }
}