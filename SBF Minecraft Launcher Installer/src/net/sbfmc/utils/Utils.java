package net.sbfmc.utils;

import java.io.File;

public class Utils {
	public static void deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				File f = new File(dir, children[i]);
				deleteDirectory(f);
			}
			dir.delete();
		} else {
			dir.delete();
		}
	}

	public static String getDesktopLocation() {
		if (new File(System.getProperty("user.home") + File.separator + "Рабочий стол").exists()) {
			return System.getProperty("user.home") + File.separator + "Рабочий стол";
		} else if (new File(System.getProperty("user.home") + File.separator + "Desktop").exists()) {
			return System.getProperty("user.home") + File.separator + "Desktop";
		} else {
			throw new NullPointerException("No desktop exists :(");
		}
	}
}
