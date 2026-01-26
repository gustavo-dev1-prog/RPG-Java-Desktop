package com.rpg.view.components;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
	
	private int radius = 20;
	
	public RoundedButton(String text) {
		super(text);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setForeground(Color.WHITE);
		setFont(new Font("Arial", Font.BOLD, 14));
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setPreferredSize(new Dimension(120, 35));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(new Color(40, 40, 40));
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
		
		super.paintComponent(g2);
		g2.dispose();
		
	}

}
