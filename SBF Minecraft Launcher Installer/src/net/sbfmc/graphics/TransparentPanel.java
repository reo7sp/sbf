package net.sbfmc.graphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TransparentPanel extends JPanel {
	private static final long serialVersionUID = 8843414030434695333L;

	private Color color = new Color(0, 0, 0, 0.5f);

	public TransparentPanel() {
		setOpaque(false);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
