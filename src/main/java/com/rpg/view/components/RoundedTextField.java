package com.rpg.view.components;

import javax.swing.*;
import java.awt.*;

public class RoundedTextField extends JTextField  {
     
	private int radius = 15;
	
	public RoundedTextField(int columns) {
		super(columns);
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		setFont(new Font("Arial", Font.PLAIN, 14));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(Color.WHITE);
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
		
		super.paintComponent(g2);
		g2.dispose();
	}
	
	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(Color.BLACK);
		g2.drawRoundRect(0, 0, getWidth() -1, getHeight() -1, radius, radius);
		
		g2.dispose();
		
      }
}