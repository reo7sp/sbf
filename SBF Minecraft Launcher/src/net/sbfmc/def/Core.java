package net.sbfmc.def;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sbfmc.graphics.ImageButton;
import net.sbfmc.graphics.ImagePanel;
import net.sbfmc.graphics.panels.AdditionalPanel;
import net.sbfmc.graphics.panels.LogPanel;
import net.sbfmc.graphics.panels.UpdatePanel;
import net.sbfmc.utils.Updater;
import net.sbfmc.utils.Utils;

public class Core {
	private static JFrame frame = new JFrame();
	private static ImagePanel mainPanel = new ImagePanel();
	private static String[] nicks = { "Player", "", "", "", "" };
	private static int ram = 512;
	private static int OS = -1;
	private static final String IP = "sbfmc.net";
	private static String filesLocation;
	private static String launcherFilesLocation;
	private static boolean serverAlive;
	private static boolean showLog;

	public static void main(String[] args) {
		load();
		makeFrame();
	}

	private Core() {
	}

	private static void load() {
		// checking server status
		new Thread("server-status") {
			@Override
			public void run() {
				try {
					Socket socket = new Socket();
					socket.setSoTimeout(3000);
					socket.setTcpNoDelay(true);
					socket.setTrafficClass(18);
					socket.connect(new InetSocketAddress(IP, 25565), 3000);
					socket.close();

					serverAlive = true;
				} catch (IOException err) {
				}
			}
		}.start();

		// finding OS id
		if (System.getProperty("os.name").contains("Linux")) {
			OS = 0;
		} else if (System.getProperty("os.name").contains("Win")) {
			OS = 1;
		} else if (System.getProperty("os.name").contains("Mac")) {
			OS = 2;
		}

		// finding files location
		if (OS == 0 || OS == 2) {
			filesLocation = System.getProperty("user.home");
			launcherFilesLocation = filesLocation + File.separator + ".SBF";
		} else if (OS == 1) {
			filesLocation = System.getenv("APPDATA");
			launcherFilesLocation = filesLocation + File.separator + "SBF";
		}

		// reading config file
		try {
			BufferedReader buffR = new BufferedReader(new FileReader(launcherFilesLocation + File.separator
					+ "config.txt"));
			String s, key, value;
			while ((s = buffR.readLine()) != null) {
				key = s.split("=")[0];
				value = s.split("=")[1];

				if (key.equalsIgnoreCase("nick0")) {
					nicks[0] = value;
				} else if (key.equalsIgnoreCase("nick1")) {
					nicks[1] = value;
				} else if (key.equalsIgnoreCase("nick2")) {
					nicks[2] = value;
				} else if (key.equalsIgnoreCase("nick3")) {
					nicks[3] = value;
				} else if (key.equalsIgnoreCase("nick4")) {
					nicks[4] = value;
				} else if (key.equalsIgnoreCase("ram")) {
					ram = Integer.parseInt(value);
				}

				key = value = null;
			}
			buffR.close();
		} catch (Exception err) {
		}

		// loading images
		Images.loadImages();
	}

	private static void makeFrame() {
		// setting frame and main panel
		frame.setResizable(false);
		frame.setTitle("SBF Minecraft Launcher");
		frame.setIconImage(Images.getIcon());
		frame.setSize(700, 475);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel.setMainImage(true);
		mainPanel.setLayout(null);
		frame.setContentPane(mainPanel);

		// adding components
		// for left panel
		JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		leftPanel.setLayout(null);
		leftPanel.setBounds(50, 50, 175, 55);

		final JComboBox nickComboBox = new JComboBox(nicks);
		nickComboBox.setFont(new Font(null, Font.PLAIN, 12));
		nickComboBox.setBounds(0, 0, 175, 25);
		nickComboBox.setEditable(true);
		leftPanel.add(nickComboBox);

		ImageButton playButton = new ImageButton();
		playButton.setBounds(0, 30, 75, 25);
		playButton.setText("Играть!");
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread("update") {
					@Override
					public void run() {
						Updater.getInstance().updateClient();
						Updater.getInstance().updateLauncher();
					}
				}.start();

				new Thread("start-game") {
					@Override
					public void run() {
						UpdatePanel.getInstance().changeToThis();
						while (Updater.getInstance().isUpdateRunning()) {
							if (Updater.getInstance().getCurrentDownload() != null) {
								UpdatePanel.getInstance().updateLabel(Updater.getInstance().getCurrentDownload().getDownloaded());
							}
							try {
								Thread.sleep(100);
							} catch (InterruptedException err) {
								err.printStackTrace();
							}
						}

						Utils.addNewNick(nickComboBox.getSelectedItem().toString());
						Utils.saveConfig();

						Utils.runMinecraft();

						if (showLog) {
							LogPanel.getInstance().changeToThis();
						} else {
							System.exit(0);
						}
					}
				}.start();
			}
		});
		leftPanel.add(playButton);

		ImageButton additionalButton = new ImageButton();
		additionalButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AdditionalPanel.getInstance().changeToThis();
			}
		});
		additionalButton.setBounds(80, 30, 75, 25);
		additionalButton.setText("Дополнительно");
		leftPanel.add(additionalButton);

		mainPanel.add(leftPanel);

		// for right panel
		JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		rightPanel.setLayout(null);
		rightPanel.setBounds(500, 50, 200, 50);

		final JLabel serverStatus = new JLabel();
		serverStatus.setText("Загрузка...");
		serverStatus.setBounds(0, 0, 200, 25);
		serverStatus.setForeground(Color.WHITE);
		serverStatus.setFont(new Font(null, Font.PLAIN, 12));
		rightPanel.add(serverStatus);

		new Thread("server-status-label") {
			private long startTime = System.currentTimeMillis();

			@Override
			public void run() {
				while (System.currentTimeMillis() - startTime < 3000) {
					if (serverAlive) {
						break;
					}
				}

				serverStatus.setText("<html>Сервер " +
						(serverAlive ? "<font color=\"lime\">включен" : "<font color=\"red\">выключен"));
				serverStatus.repaint();
			}
		}.start();

		mainPanel.add(rightPanel);

		// frame show
		frame.setVisible(true);

		// setting right size
		frame.setSize(frame.getWidth() * 2 - mainPanel.getWidth(), frame.getHeight() * 2 - mainPanel.getHeight());
	}

	public static JFrame getFrame() {
		return frame;
	}

	public static String[] getNicks() {
		return nicks;
	}

	public static int getRam() {
		return ram;
	}

	public static int getOS() {
		return OS;
	}

	public static String getIP() {
		return IP;
	}

	public static String getDownloadFolder() {
		return "http://" + IP + "/files/";
	}

	public static String getFilesLocation() {
		return filesLocation;
	}

	public static String getLauncherFilesLocation() {
		return launcherFilesLocation;
	}

	public static boolean isShowLog() {
		return showLog;
	}

	public static ImagePanel getMainPanel() {
		return mainPanel;
	}

	public static boolean isServerAlive() {
		return serverAlive;
	}

	public static int getWidth() {
		return frame.getContentPane().getWidth();
	}

	public static int getHeight() {
		return frame.getContentPane().getHeight();
	}

	public static void setRam(int ram) {
		Core.ram = ram;
	}

	public static void setShowLog(boolean showLog) {
		Core.showLog = showLog;
	}
}
