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
	private static Image load;

	public static void loadImages() {
		icon = new ImageIcon(Images.class.getResource("/net/sbfmc/images/icon.png")).getImage();
		mainWindow = new ImageIcon(Images.class.getResource("/net/sbfmc/images/main_window.jpg")).getImage();
		optionsWindow = new ImageIcon(Images.class.getResource("/net/sbfmc/images/options_window.jpg")).getImage();
		button = new ImageIcon(Images.class.getResource("/net/sbfmc/images/button.jpg")).getImage();
		buttonOver = new ImageIcon(Images.class.getResource("/net/sbfmc/images/button_over.jpg")).getImage();
		buttonDown = new ImageIcon(Images.class.getResource("/net/sbfmc/images/button_down.jpg")).getImage();
		load = new ImageIcon(Images.class.getResource("/net/sbfmc/images/load.gif")).getImage();
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

	public static Image getLoad() {
		return load;
	}
}
