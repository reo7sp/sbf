package net.sbfmc.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import net.sbfmc.def.Images;

public class ImageButton extends JButton {
	private static final long serialVersionUID = -4354065754967156461L;
	private Image bgImage = Images.getButton();
	private Image overBgImage = Images.getButtonOver();
	private Image downBgImage = Images.getButtonDown();
	private Image currentBgImage = bgImage;

	public ImageButton() {
		super();

		setFocusable(false);
		setFont(new Font(null, Font.PLAIN, 11));
		setForeground(Color.WHITE);
		setContentAreaFilled(false);
		setBorder(null);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (overBgImage != null) {
					currentBgImage = overBgImage;
				} else if (currentBgImage != bgImage) {
					currentBgImage = bgImage;
				} else {
					return;
				}
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				if (currentBgImage != bgImage) {
					currentBgImage = bgImage;
				} else {
					return;
				}
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				if (downBgImage != null) {
					currentBgImage = downBgImage;
				} else if (currentBgImage != bgImage) {
					currentBgImage = bgImage;
				} else {
					return;
				}
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (currentBgImage != bgImage) {
					currentBgImage = bgImage;
				} else {
					return;
				}
				repaint();
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		if (currentBgImage != null) {
			g.drawImage(currentBgImage, 0, 0, getWidth(), getHeight(), null);
		}
		super.paintComponent(g);
	}

	public Image getBgImage() {
		return bgImage;
	}

	public void setBgImage(Image bgImage) {
		this.bgImage = bgImage;
		this.currentBgImage = bgImage;
		repaint();
	}

	public Image getOverBgImage() {
		return overBgImage;
	}

	public void setOverBgImage(Image overBgImage) {
		this.overBgImage = overBgImage;
		repaint();
	}

	public Image getDownBgImage() {
		return downBgImage;
	}

	public void setDownBgImage(Image downBgImage) {
		this.downBgImage = downBgImage;
		repaint();
	}

	public Image getCurrentBgImage() {
		return currentBgImage;
	}
}
