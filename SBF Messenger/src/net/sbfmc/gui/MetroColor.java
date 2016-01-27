package net.sbfmc.gui;

import java.awt.Color;
import java.util.Random;

public enum MetroColor {
	TEAL(0x008299),

	BLUE(0x2672EC),

	PURPLE(0x8C0095),

	DARK_PURPLE(0x5133AB),

	RED(0xAC193D),

	ORANGE(0xD24726),

	GREEN(0x008A00),

	SKY_BLUE(0x094AB2);

	private int color;

	private MetroColor(int color) {
		this.color = color;
	}

	public int getHexColor() {
		return color;
	}

	public Color getColor() {
		return new Color(color);
	}

	public static MetroColor getRandomColor() {
		return values()[new Random().nextInt(values().length)];
	}
}
