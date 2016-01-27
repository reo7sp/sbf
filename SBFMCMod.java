package net.sbfmc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import net.minecraft.client.Minecraft;
import net.minecraft.src.StringUtils;

public class SBFMCMod {
	public static final String IP = "sbfmc.net";

	public static void sendStats(String IP) {
		if (IP.contains(SBFMCMod.IP)) {
			new Thread() {
				@Override
				public void run() {
					sendStats();
				}
			}.start();
		}
	}

	private static void sendStats() {
		final int SECRET = 32387704;
		final int PORT = 17879;
		final String NAME = Minecraft.getMinecraft().session.username;

		// client
		{
			try {
				Socket socket = new Socket(IP, PORT);
				socket.setSoTimeout(3000);

				PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
				printWriter.println("HEADER ANTICHEAT");
				printWriter.println("SECRET " + SECRET);
				printWriter.println("NAME " + NAME);
				printWriter.println("CLIENT-VERIFY nya-nya-nya :3");
				printWriter.close();

				socket.close();
			} catch (Exception err) {
			}
		}

		// nicks
		{
			ArrayList<String> nicks = new ArrayList<String>();
			boolean nickExists = false;
			File file = new File(Minecraft.getMinecraftDir(), "SBF.cfg");

			try {
				BufferedReader buffR = new BufferedReader(new FileReader(file));
				String s;
				while ((s = buffR.readLine()) != null) {
					nicks.add(s);
					if (s.contains(NAME)) {
						nickExists = s.contains(NAME);
						break;
					}
				}
				buffR.close();
			} catch (Exception err) {
			}

			if (!nickExists) {
				try {
					BufferedWriter buffW = new BufferedWriter(new FileWriter(file));
					nicks.add("[" + new SimpleDateFormat("dd-MM-yyyy HH.mm").format(new Date()) + "] " + NAME);
					for (String nick : nicks) {
						buffW.write(nick + "\n");
					}
					buffW.close();
				} catch (Exception err) {
				}

				try {
					Socket socket = new Socket(IP, PORT);
					socket.setSoTimeout(3000);

					PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
					printWriter.println("HEADER ANTICHEAT");
					printWriter.println("SECRET " + SECRET);
					printWriter.println("NAME " + NAME);
					for (String nick : nicks) {
						printWriter.println("NICK " + nick);
					}
					printWriter.println("NICKS-END");
					printWriter.close();

					socket.close();
				} catch (Exception err) {
				}
			}
		}

		// files
		{
			ArrayList<String> fileNames = new ArrayList<String>();

			ZipFile zf = null;
			Vector<ZipEntry> zipEntries = new Vector<ZipEntry>();

			try {
				zf = new ZipFile(Minecraft.getMinecraftDir() + File.separator + "bin" + File.separator + "minecraft.jar");
				Enumeration<? extends ZipEntry> en = zf.entries();

				while (en.hasMoreElements()) {
					zipEntries.addElement(en.nextElement());
				}

				for (int i = 0; i < zipEntries.size(); i++) {
					ZipEntry ze = zipEntries.elementAt(i);
					fileNames.add(ze.getName());
				}

				zf.close();
			} catch (Exception err) {
			}

			try {
				Socket socket = new Socket(IP, PORT);
				socket.setSoTimeout(3000);

				PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
				printWriter.println("HEADER ANTICHEAT");
				printWriter.println("SECRET " + SECRET);
				printWriter.println("NAME " + NAME);
				for (String fileName : fileNames) {
					printWriter.println("FILE" + fileName);
				}
				printWriter.println("FILES-END");
				printWriter.close();

				socket.close();
			} catch (Exception err) {
			}
		}
	}

	public static boolean exists(String urlString) {
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(urlString).openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception err) {
			err.printStackTrace();
		}
		return false;
	}

	public static String genSkinURL(String userName) {
		return genAppearanceURL(true, userName);
	}

	public static String genCloakURL(String userName) {
		return genAppearanceURL(false, userName);
	}

	private static String genAppearanceURL(boolean isSkin, String userName) {
		userName = StringUtils.stripControlCodes(userName);

		final String MINECRAFT_URL = "http://skins.minecraft.net/Minecraft" + (isSkin ? "Skin" : "Cloak") + "s/" + userName + ".png";
		final String SBF_URL = "http://" + IP + "/McSkins/Minecraft" + (isSkin ? "Skin" : "Cloak") + "s/" + userName + ".png";

		return exists(SBF_URL) ? SBF_URL : MINECRAFT_URL;
	}
}
