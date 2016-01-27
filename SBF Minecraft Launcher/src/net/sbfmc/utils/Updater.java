package net.sbfmc.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.sbfmc.def.Core;

public class Updater {
	private static Updater instance;

	private Download currentDownload;

	private boolean updateRunning;
	private boolean updateLauncher;
	private boolean updateClient;
	private boolean updateAllClient;
	private boolean updateBin;
	private boolean updateNatives;
	private boolean updateResources;
	private boolean updateMinecraftJar;
	private boolean updateMods;

	public static Updater getInstance() {
		if (instance == null) {
			instance = new Updater();
		}
		return instance;
	}

	public void updateClient() {
		if (updateRunning || !updateClient) {
			return;
		}

		updateRunning = true;

		updateAllClient = updateBin && updateNatives && updateResources && updateMinecraftJar;

		try {
			if (updateAllClient) {
				updateAllClient();
			} else {
				if (updateMinecraftJar) {
					updateMinecraftJar();
				}
				if (updateResources) {
					updateResources();
				}
				if (updateBin) {
					updateBin();
				}
				if (updateNatives) {
					updateNatives();
				}
			}
			if (updateMods) {
				updateMods();
			}
		} catch (Exception err) {
			err.printStackTrace();
		}

		currentDownload = null;
		updateRunning = false;
	}

	private void updateAllClient() throws MalformedURLException, IOException {
		// download
		if (!updateRunning) {
			return;
		}
		currentDownload = new Download(
				new URL(Core.getDownloadFolder() + "client/client_mc.zip"),
				Core.getLauncherFilesLocation() + File.separator + "temp" + File.separator + "client_mc.zip");
		currentDownload.startDownload();

		if (!updateRunning) {
			return;
		}
		UnZip.start(
				Core.getLauncherFilesLocation() + File.separator + "temp" + File.separator + "client_mc.zip",
				Core.getFilesLocation());
	}

	private void updateBin() throws MalformedURLException, IOException {
		// download
		if (updateRunning) {
			currentDownload = new Download(
					new URL(Core.getDownloadFolder() + "client/bin_mc.zip"),
					Core.getLauncherFilesLocation() + File.separator + "temp" + File.separator + "bin_mc.zip");
			currentDownload.startDownload();
		}

		// unzip
		if (updateRunning) {
			UnZip.start(
					Core.getLauncherFilesLocation() + File.separator + "temp" + File.separator + "bin_mc.zip",
					Core.getFilesLocation() + File.separator + ".minecraft");
		}
	}

	private void updateNatives() throws MalformedURLException, IOException {
		// download
		if (updateRunning) {
			currentDownload = new Download(
					new URL(Core.getDownloadFolder() + "client/natives_mc.zip"),
					Core.getLauncherFilesLocation() + File.separator + "temp" + File.separator + "natives_mc.zip");
			currentDownload.startDownload();
		}

		// unzip
		if (updateRunning) {
			UnZip.start(
					Core.getLauncherFilesLocation() + File.separator + "temp" + File.separator + "natives_mc.zip",
					Core.getFilesLocation() + File.separator + ".minecraft" + File.separator + "bin");
		}
	}

	private void updateResources() throws MalformedURLException, IOException {
		// download
		if (updateRunning) {
			currentDownload = new Download(
					new URL(Core.getDownloadFolder() + "client/resourses_mc.zip"),
					Core.getLauncherFilesLocation() + File.separator + "temp" + File.separator + "resourses_mc.zip");
			currentDownload.startDownload();
		}

		// unzip
		if (updateRunning) {
			UnZip.start(
					Core.getLauncherFilesLocation() + File.separator + "temp" + File.separator + "resourses_mc.zip",
					Core.getFilesLocation() + File.separator + ".minecraft");
		}
	}

	private void updateMinecraftJar() throws MalformedURLException, IOException {
		// download
		if (updateRunning) {
			currentDownload = new Download(
					new URL(Core.getDownloadFolder() + "client/minecraft.jar"),
					Core.getFilesLocation() + File.separator + ".minecraft" + File.separator + "bin" + File.separator + "minecraft.jar");
			currentDownload.startDownload();
		}
	}

	private void updateMods() throws MalformedURLException, IOException {
		String[] mods = null;

		// download
		if (updateRunning) {
			currentDownload = new Download(
					new URL(Core.getDownloadFolder() + "mods_list.txt"),
					Core.getLauncherFilesLocation() + File.separator + "temp" + File.separator + "mods_list.txt");
			currentDownload.startDownload();
		}

		// parse mods list
		if (updateRunning) {
			String modsRaw = "";

			BufferedReader buffR = new BufferedReader(new FileReader(currentDownload.getFile()));
			String s;
			while ((s = buffR.readLine()) != null) {
				modsRaw += s + "\n";
			}
			buffR.close();

			mods = modsRaw.split("\n");
		}

		// download mods
		for (String urlString : mods) {
			if (updateRunning) {
				String[] urlParts = urlString.split("/");
				String modName = urlParts[urlParts.length - 1];
				URL url = new URL(urlString);

				currentDownload = new Download(
						url,
						Core.getFilesLocation() + File.separator + ".minecraft" + File.separator + "mods" + File.separator + modName);
				currentDownload.startDownload();
			}
		}
	}

	public void stopUpdateClient() {
		if (!updateRunning) {
			return;
		}
		updateRunning = false;

		if (currentDownload != null && currentDownload.isRunning()) {
			currentDownload.stopDownload();
		}
	}

	public void updateLauncher() {
		if (updateRunning || !updateLauncher) {
			return;
		}

		updateRunning = true;

		try {
			currentDownload = new Download(
					new URL(Core.getDownloadFolder() + "launcher.zip"),
					Core.getLauncherFilesLocation() + File.separator + "temp" + File.separator + "launcher.zip");
			currentDownload.startDownload();

			if (updateRunning) {
				UnZip.start(
						Core.getLauncherFilesLocation() + File.separator + "temp" + File.separator + "launcher.zip",
						Core.getFilesLocation());
			}

			if (updateRunning) {
				currentDownload = new Download(
						new URL(Core.getDownloadFolder() + "SBF Minecraft Launcher." + (Core.getOS() == 1 ? "exe" : "jar")),
						Utils.getDesktopLocation() + File.separator + "SBF Minecraft Launcher." + (Core.getOS() == 1 ? "exe" : "jar"));
				currentDownload.startDownload();
			}
		} catch (Exception err) {
			err.printStackTrace();
		}

		updateRunning = false;
	}

	public void stopUpdateLauncher() {
		if (!updateRunning) {
			return;
		}
		updateRunning = false;

		if (currentDownload != null && currentDownload.isRunning()) {
			currentDownload.stopDownload();
		}
	}

	public boolean isUpdateRunning() {
		return updateRunning;
	}

	public void setUpdateRunning(boolean updateRunning) {
		this.updateRunning = updateRunning;
	}

	public boolean isUpdateLauncher() {
		return updateLauncher;
	}

	public void setUpdateLauncher(boolean updateLauncher) {
		this.updateLauncher = updateLauncher;
	}

	public boolean isUpdateClient() {
		return updateClient;
	}

	public void setUpdateClient(boolean updateClient) {
		this.updateClient = updateClient;

		String minecraftFolder = Core.getFilesLocation() + File.separator + ".minecraft";
		if (!new File(minecraftFolder).exists()) {
			updateAllClient = true;
		} else {
			updateBin = !new File(minecraftFolder + File.separator + "bin").exists();
			updateNatives = !new File(minecraftFolder + File.separator + "bin" + File.separator + "natives").exists();
			updateResources = !new File(minecraftFolder + File.separator + "resources").exists();
			updateMinecraftJar = true;
			updateMods = true;

			updateAllClient = updateBin && updateNatives && updateResources && updateMinecraftJar && updateMods;
		}
	}

	public boolean isUpdateAllClient() {
		return updateAllClient;
	}

	public void setUpdateAllClient(boolean updateAllClient) {
		this.updateAllClient = updateAllClient;
	}

	public boolean isUpdateBin() {
		return updateBin;
	}

	public void setUpdateBin(boolean updateBin) {
		this.updateBin = updateBin;
	}

	public boolean isUpdateNatives() {
		return updateNatives;
	}

	public void setUpdateNatives(boolean updateNatives) {
		this.updateNatives = updateNatives;
	}

	public boolean isUpdateResources() {
		return updateResources;
	}

	public void setUpdateResources(boolean updateResources) {
		this.updateResources = updateResources;
	}

	public boolean isUpdateMinecraftJar() {
		return updateMinecraftJar;
	}

	public void setUpdateMinecraftJar(boolean updateMinecraftJar) {
		this.updateMinecraftJar = updateMinecraftJar;
	}

	public boolean isUpdateMods() {
		return updateMods;
	}

	public void setUpdateMods(boolean updateMods) {
		this.updateMods = updateMods;
	}

	public Download getCurrentDownload() {
		return currentDownload;
	}
}
