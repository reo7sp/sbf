package net.sbfmc.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

public abstract class MyFrame extends JFrame {
	private static final long serialVersionUID = -7019662710201347836L;

	public MyFrame() {
		super();

		// setting bounds
		setSize(getWidth(), getHeight());
		if (getX() == -1 || getY() == -1) {
			setLocationRelativeTo(null);
		} else {
			setLocation(getX(), getY());
		}

		// setting other
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setBackground(MetroColor.getRandomColor().getColor());
		((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

		// build
		build();
	}

	@Override
	public abstract int getWidth();

	@Override
	public abstract int getHeight();

	@Override
	public int getX() {
		return -1;
	}

	@Override
	public int getY() {
		return -1;
	}

	protected abstract void build();

	@Override
	public void paint(Graphics gRaw) {
		// turning on anti-aliasing
		Graphics2D g = (Graphics2D) gRaw;
		g.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(
				RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		// do that frame want before
		super.paint(gRaw);
	}
}
