package net.sbfmc.util;

import java.awt.Color;

public class GeneralUtils {
	public static String getHexColor(Color color) {
		return "#" + Integer.toHexString(color.getRed()) + Integer.toHexString(color.getGreen()) + Integer.toHexString(color.getBlue());
	}
}
