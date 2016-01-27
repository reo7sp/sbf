package net.sbfmc.util;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.ChatColor;

public class ChatColorer {
	public static String toHtml(final String src) {
		if (src.isEmpty()) {
			return src;
		}

		StringBuilder dst = new StringBuilder(src.replace("<", "&lt;").replace(">", "&gt;")); // also there is replacing some chars!
		HashMap<Integer, ChatColor> colorPositions = new HashMap<Integer, ChatColor>();

		// finding colored strings
		for (int i = 0; i < dst.length() - 1; i++) {
			char curChar = dst.charAt(i);

			if (curChar == ChatColor.COLOR_CHAR) {
				char nextChar = dst.charAt(i + 1);

				for (ChatColor color : ChatColor.values()) {
					if (nextChar == color.getChar()) {
						colorPositions.put(i, color);
						break;
					}
				}
			}
		}

		// transforming to html
		Integer[] positions = colorPositions.keySet().toArray(new Integer[0]);
		Arrays.sort(positions);
		for (int i = 0; i < positions.length; i++) {
			int curPos = positions[i];
			ChatColor color = colorPositions.get(curPos);
			String startHtmlTag = null;

			// getting html tag
			if (color.isColor()) {
				startHtmlTag = "<font color=\"" + color.toHexColor() + "\">";
			} else if (color == ChatColor.BOLD) {
				startHtmlTag = "<b>";
			} else if (color == ChatColor.ITALIC) {
				startHtmlTag = "<i>";
			} else if (color == ChatColor.STRIKETHROUGH) {
				startHtmlTag = "<strike>";
			} else if (color == ChatColor.UNDERLINE) {
				startHtmlTag = "<u>";
			} else if (color == ChatColor.RESET || color == ChatColor.MAGIC) {
				startHtmlTag = "";
			}

			// adding start tag
			if (startHtmlTag != null) {
				dst.replace(curPos, curPos + 2, startHtmlTag);

				// refreshing positions
				for (int j = i + 1; j < positions.length; j++) {
					ChatColor tempColor = colorPositions.get(positions[j]);

					colorPositions.remove(positions[j]);

					positions[j] += startHtmlTag.length() - 2;

					colorPositions.put(positions[j], tempColor);
				}
			}

			// same with end tag
			for (int j = i + 1; j < positions.length + 1; j++) {
				int nextPos;

				if (j != positions.length) {
					nextPos = positions[j];
				} else {
					nextPos = dst.length();
				}

				ChatColor nextColor = colorPositions.get(nextPos);
				String endHtmlTag = null;

				// getting html tag
				if (nextColor == null || !nextColor.isFormat()) {
					if (color.isColor()) {
						endHtmlTag = "</font>";
					} else if (color == ChatColor.BOLD) {
						endHtmlTag = "</b>";
					} else if (color == ChatColor.ITALIC) {
						endHtmlTag = "</i>";
					} else if (color == ChatColor.STRIKETHROUGH) {
						endHtmlTag = "</strike>";
					} else if (color == ChatColor.UNDERLINE) {
						endHtmlTag = "</u>";
					}
				}

				// adding it!
				if (endHtmlTag != null) {
					dst.insert(nextPos, endHtmlTag);

					// refreshing positions
					for (int k = j; k < positions.length; k++) {
						ChatColor tempColor = colorPositions.get(positions[k]);

						colorPositions.remove(positions[k]);

						positions[k] += endHtmlTag.length();

						colorPositions.put(positions[k], tempColor);
					}

					break;
				}
			}
		}

		return dst.toString();
	}

	public static String toMinecraftStyle(final String src) {
		String dst = new String(src); // safe editing

		// transforming to minecraft style
		for (ChatColor color : ChatColor.values()) {
			dst = dst.replace("<font color=\"" + color.toHexColor() + "\">", "" + color);
		}
		dst = dst.
				replace("<b>", "" + ChatColor.BOLD).
				replace("<i>", "" + ChatColor.ITALIC).
				replace("<u>", "" + ChatColor.UNDERLINE).
				replace("<strike>", "" + ChatColor.STRIKETHROUGH).
				replace("</b>", "").
				replace("</i>", "").
				replace("</u>", "").
				replace("</strike>", "").
				replace("</font>", "");

		return dst;
	}
}
