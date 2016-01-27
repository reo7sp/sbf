package net.sbfmc.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import net.sbfmc.def.Core;
import net.sbfmc.graphics.panels.LogPanel;

public class Utils {
	public static void saveConfig() {
		try {
			BufferedWriter buffW = new BufferedWriter(new FileWriter(Core.getLauncherFilesLocation()
					+ File.separator + "config.txt"));
			for (int i = 0; i < Core.getNicks().length; i++) {
				buffW.write("nick" + i + "=" + Core.getNicks()[i] + "\n");
			}
			buffW.write("ram=" + Core.getRam() + "\n");
			buffW.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	public static void runMinecraft() {
		StringBuilder cp = new StringBuilder();
		for (String p : new String[] { "minecraft.jar", "lwjgl.jar", "lwjgl_util.jar", "jinput.jar" }) {
			cp.append(Core.getFilesLocation() + File.separator + ".minecraft" + File.separator + "bin");
			cp.append("/");
			cp.append(p);
			cp.append(File.pathSeparatorChar);
		}

		ProcessBuilder processBuilder = new ProcessBuilder(
				"java",
				"-cp", cp.toString(),
				"-Djava.library.path=" + new File(Core.getFilesLocation() + File.separator + ".minecraft" + File.separator + "bin", "natives").getPath(),
				"-Xmx" + Core.getRam() + "M",
				"-Xms" + Math.min(Core.getRam(), 128) + "M",
				"net.minecraft.client.Minecraft",
				Core.getNicks()[0]);

		processBuilder.redirectErrorStream(true);
		processBuilder.directory(new File(Core.getFilesLocation() + File.separator + ".minecraft" + File.separator + "bin"));

		try {
			final Process process = processBuilder.start();
			if (Core.isShowLog()) {
				if (process != null) {
					new Thread("log-listener") {
						@Override
						public void run() {
							try {
								BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
								String line;
								while ((line = input.readLine()) != null) {
									LogPanel.addLine(line);
								}
								process.waitFor();
								LogPanel.addLine("Minecraft exited with status " + process.exitValue());
							} catch (IOException err) {
								err.printStackTrace();
							} catch (InterruptedException err) {
								err.printStackTrace();
							}
						}
					}.start();
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	public static void addNewNick(String nick) {
		ArrayList<String> nicks = new ArrayList<String>();

		for (int i = 0; i < Core.getNicks().length; i++) {
			if (!nick.equalsIgnoreCase(Core.getNicks()[i]) &&
					Core.getNicks()[i] != null &&
					!Core.getNicks()[i].equals("") &&
					!Core.getNicks()[i].equalsIgnoreCase("null")) {
				nicks.add(Core.getNicks()[i]);
			}
		}

		for (int i = 0; i < nicks.size() && i < Core.getNicks().length - 1; i++) {
			Core.getNicks()[i + 1] = nicks.get(i);
		}

		Core.getNicks()[0] = nick;
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

	public static void drawCreeper() {
		System.out.println();
		System.out.println("+-------------+");
		System.out.println("|  _       _  |");
		System.out.println("| |_|     |_| |");
		System.out.println("|      _      |");
		System.out.println("|    _| |_    |");
		System.out.println("|   |  _  |   |");
		System.out.print("|   |_| |_|   |");
		System.out.println("  <-- creeper");
		System.out.println("+-------------+");
	}
}
