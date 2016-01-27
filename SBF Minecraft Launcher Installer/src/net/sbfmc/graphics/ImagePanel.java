package net.sbfmc.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import net.sbfmc.def.Images;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 4334097189911707775L;
	private Image image;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setMainImage(boolean main) {
		if (main) {
			image = Images.getMainWindow();
		} else {
			image = Images.getOptionsWindow();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(
				RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		if (image != null) {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		}
	}
}