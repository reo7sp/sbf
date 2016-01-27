package net.sbfmc.def;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Images {
	private static Image icon;
	private static Image mainWindow;
	private static Image optionsWindow;
	private static Image button;
	private static Image buttonOver;
	private static Image buttonDown;
	private static Image cross;
	private static Image crossOver;
	private static Image crossDown;

	public static void loadImages() {
		icon = new ImageIcon(Images.class.getResource("/net/sbfmc/images/icon.png")).getImage();
		mainWindow = new ImageIcon(Images.class.getResource("/net/sbfmc/images/main_window.jpg")).getImage();
		optionsWindow = new ImageIcon(Images.class.getResource("/net/sbfmc/images/options_window.jpg")).getImage();
		button = new ImageIcon(Images.class.getResource("/net/sbfmc/images/button.jpg")).getImage();
		buttonOver = new ImageIcon(Images.class.getResource("/net/sbfmc/images/button_over.jpg")).getImage();
		buttonDown = new ImageIcon(Images.class.getResource("/net/sbfmc/images/button_down.jpg")).getImage();
		cross = new ImageIcon(Images.class.getResource("/net/sbfmc/images/cross.png")).getImage();
		crossOver = new ImageIcon(Images.class.getResource("/net/sbfmc/images/cross_over.png")).getImage();
		crossDown = new ImageIcon(Images.class.getResource("/net/sbfmc/images/cross_down.png")).getImage();
	}

	public static Image getIcon() {
		return icon;
	}

	public static Image getMainWindow() {
		return mainWindow;
	}

	public static Image getOptionsWindow() {
		return optionsWindow;
	}

	public static Image getButton() {
		return button;
	}

	public static Image getButtonOver() {
		return buttonOver;
	}

	public static Image getButtonDown() {
		return buttonDown;
	}

	public static Image getCross() {
		return cross;
	}

	public static Image getCrossOver() {
		return crossOver;
	}

	public static Image getCrossDown() {
		return crossDown;
	}
}
