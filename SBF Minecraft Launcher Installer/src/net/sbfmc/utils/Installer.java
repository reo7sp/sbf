package net.sbfmc.utils;

import java.io.File;
import java.net.URL;

import javax.swing.JOptionPane;

import net.sbfmc.def.Core;

public class Installer extends Thread {
	private static Download currentDownload;

	public static void install() {
		new Installer().start();
	}

	@Override
	public void run() {
		try {
			// creating installer folder
			File folder = new File(Core.getFilesLocation() + File.separator + "SBF_Installer");
			folder.mkdirs();

			// downloading launcher files
			currentDownload = new Download(
					new URL(Core.getDownloadFolder() + "launcher.zip"),
					new File(folder, "launcher.zip"));
			currentDownload.startDownload();

			// unziping launcher files
			UnZip.start(
					folder.getAbsolutePath() + File.separator + "launcher.zip",
					Core.getLauncherFilesLocation());

			// loading launcher
			currentDownload = new Download(
					new URL(Core.getDownloadFolder() + "SBF Minecraft Launcher." + (Core.getOS() == 1 ? "exe" : "jar")),
					Utils.getDesktopLocation() + File.separator + "SBF Minecraft Launcher." + (Core.getOS() == 1 ? "exe" : "jar"));
			currentDownload.startDownload();

			// cleaning up
			Utils.deleteDirectory(folder);

			// saying what all is good
			JOptionPane.showMessageDialog(null, "Установка завершена!");
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, "Ошибка! " + err);
		}
		System.exit(0);
	}

	public static Download getCurrentDownload() {
		return currentDownload;
	}
}
