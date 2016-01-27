package org.bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * All supported color values for chat
 */
public enum ChatColor {
	/**
	 * Represents black
	 */
	BLACK('0', 0x00),
	/**
	 * Represents dark blue
	 */
	DARK_BLUE('1', 0x1),
	/**
	 * Represents dark green
	 */
	DARK_GREEN('2', 0x2),
	/**
	 * Represents dark blue (aqua)
	 */
	DARK_AQUA('3', 0x3),
	/**
	 * Represents dark red
	 */
	DARK_RED('4', 0x4),
	/**
	 * Represents dark purple
	 */
	DARK_PURPLE('5', 0x5),
	/**
	 * Represents gold
	 */
	GOLD('6', 0x6),
	/**
	 * Represents gray
	 */
	GRAY('7', 0x7),
	/**
	 * Represents dark gray
	 */
	DARK_GRAY('8', 0x8),
	/**
	 * Represents blue
	 */
	BLUE('9', 0x9),
	/**
	 * Represents green
	 */
	GREEN('a', 0xA),
	/**
	 * Represents aqua
	 */
	AQUA('b', 0xB),
	/**
	 * Represents red
	 */
	RED('c', 0xC),
	/**
	 * Represents light purple
	 */
	LIGHT_PURPLE('d', 0xD),
	/**
	 * Represents yellow
	 */
	YELLOW('e', 0xE),
	/**
	 * Represents white
	 */
	WHITE('f', 0xF),
	/**
	 * Represents magical characters that change around randomly
	 */
	MAGIC('k', 0x10, true),
	/**
	 * Makes the text bold.
	 */
	BOLD('l', 0x11, true),
	/**
	 * Makes a line appear through the text.
	 */
	STRIKETHROUGH('m', 0x12, true),
	/**
	 * Makes the text appear underlined.
	 */
	UNDERLINE('n', 0x13, true),
	/**
	 * Makes the text italic.
	 */
	ITALIC('o', 0x14, true),
	/**
	 * Resets all previous chat colors or formats.
	 */
	RESET('r', 0x15);

	/**
	 * The special character which prefixes all chat colour codes. Use this if you need to dynamically
	 * convert colour codes from your custom format.
	 */
	public static final char COLOR_CHAR = '\u00A7';
	private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf(COLOR_CHAR) + "[0-9A-FK-OR]");

	private final int intCode;
	private final char code;
	private final boolean isFormat;
	private final String toString;
	private final static Map<Integer, ChatColor> BY_ID = new HashMap<Integer, ChatColor>(); // SBF: Maps.newHashMap() -> new HashMap<Integer, ChatColor>()
	private final static Map<Character, ChatColor> BY_CHAR = new HashMap<Character, ChatColor>(); // SBF: Maps.newHashMap() -> new HashMap<Character, ChatColor>()

	private ChatColor(char code, int intCode) {
		this(code, intCode, false);
	}

	private ChatColor(char code, int intCode, boolean isFormat) {
		this.code = code;
		this.intCode = intCode;
		this.isFormat = isFormat;
		this.toString = new String(new char[] { COLOR_CHAR, code });
	}

	/**
	 * Gets the char value associated with this color
	 * 
	 * @return A char value of this color code
	 */
	public char getChar() {
		return code;
	}

	@Override
	public String toString() {
		return toString;
	}

	/**
	 * Checks if this code is a format code as opposed to a color code.
	 */
	public boolean isFormat() {
		return isFormat;
	}

	/**
	 * Checks if this code is a color code as opposed to a format code.
	 */
	public boolean isColor() {
		return !isFormat && this != RESET;
	}

	/**
	 * Gets the color represented by the specified color code
	 * 
	 * @param code
	 *            Code to check
	 * @return Associative {@link org.bukkit.ChatColor} with the given code, or null if it doesn't exist
	 */
	public static ChatColor getByChar(char code) {
		return BY_CHAR.get(code);
	}

	/**
	 * Gets the color represented by the specified color code
	 * 
	 * @param code
	 *            Code to check
	 * @return Associative {@link org.bukkit.ChatColor} with the given code, or null if it doesn't exist
	 */
	public static ChatColor getByChar(String code) {
		// SBF start
		if (code == null) {
			throw new IllegalArgumentException("Code cannot be null");
		}
		if (code.length() < 1) {
			throw new IllegalArgumentException("Code must have at least one char");
		}
		// SBF end

		return BY_CHAR.get(code.charAt(0));
	}

	/**
	 * Strips the given message of all color codes
	 * 
	 * @param input
	 *            String to strip of color
	 * @return A copy of the input string, without any coloring
	 */
	public static String stripColor(final String input) {
		if (input == null) {
			return null;
		}

		return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
	}

	/**
	 * Translates a string using an alternate color code character into a string that uses the internal
	 * ChatColor.COLOR_CODE color code character. The alternate color code character will only be replaced
	 * if it is immediately followed by 0-9, A-F, a-f, K-O, k-o, R or r.
	 * 
	 * @param altColorChar
	 *            The alternate color code character to replace. Ex: &
	 * @param textToTranslate
	 *            Text containing the alternate color code character.
	 * @return Text containing the ChatColor.COLOR_CODE color code character.
	 */
	public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
		char[] b = textToTranslate.toCharArray();
		for (int i = 0; i < b.length - 1; i++) {
			if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
				b[i] = ChatColor.COLOR_CHAR;
				b[i + 1] = Character.toLowerCase(b[i + 1]);
			}
		}
		return new String(b);
	}

	/**
	 * Gets the ChatColors used at the end of the given input string.
	 * 
	 * @param input
	 *            Input string to retrieve the colors from.
	 * @return Any remaining ChatColors to pass onto the next line.
	 */
	public static String getLastColors(String input) {
		String result = "";
		int length = input.length();

		// Search backwards from the end as it is faster
		for (int index = length - 1; index > -1; index--) {
			char section = input.charAt(index);
			if (section == COLOR_CHAR && index < length - 1) {
				char c = input.charAt(index + 1);
				ChatColor color = getByChar(c);

				if (color != null) {
					result = color.toString() + result;

					// Once we find a color or reset we can stop searching
					if (color.isColor() || color.equals(RESET)) {
						break;
					}
				}
			}
		}

		return result;
	}

	// SBF start
	public String toHexColor() {
		if (intCode == 0x0) {
			return "#000000";
		} else if (intCode == 0x1) {
			return "#16499A";
		} else if (intCode == 0x2) {
			return "#004A00";
		} else if (intCode == 0x3) {
			return "#008E8E";
		} else if (intCode == 0x4) {
			return "#9E1716";
		} else if (intCode == 0x5) {
			return "#7200AC";
		} else if (intCode == 0x6) {
			return "#FF981D";
		} else if (intCode == 0x7) {
			return "#AAAAAA";
		} else if (intCode == 0x8) {
			return "#555555";
		} else if (intCode == 0x9) {
			return "#1B58B8";
		} else if (intCode == 0xA) {
			return "#00C13F";
		} else if (intCode == 0xB) {
			return "#1FAEFF";
		} else if (intCode == 0xC) {
			return "#FF2E12";
		} else if (intCode == 0xD) {
			return "#AA40FF";
		} else if (intCode == 0xE) {
			return "#F3B200";
		} else if (intCode == 0xF) {
			return "#FFFFFF";
		}
		return null;
	}

	// SBF end

	static {
		for (ChatColor color : values()) {
			BY_ID.put(color.intCode, color);
			BY_CHAR.put(color.code, color);
		}
	}
}