package net.sbfmc.def;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;

import net.sbfmc.graphics.ImageButton;
import net.sbfmc.graphics.ImagePanel;
import net.sbfmc.graphics.TransparentPanel;
import net.sbfmc.utils.Installer;

public class Core {
	private static String IP = "sbfmc.net";
	private static byte OS = -1;
	private static String filesLocation;
	private static String launcherFilesLocation;
	private static JFrame frame = new JFrame();
	private static ImagePanel mainPanel = new ImagePanel();

	public static void main(String[] args) {
		load();
		makeFrame();
	}

	private static void load() {
		// finding OS id
		if (System.getProperty("os.name").startsWith("Linux")) {
			OS = 0;
		} else if (System.getProperty("os.name").startsWith("Windows")) {
			OS = 1;
		} else if (System.getProperty("os.name").startsWith("Mac")) {
			OS = 2;
		}

		// finding files location
		if (OS == 1) {
			filesLocation = System.getenv("APPDATA");
			launcherFilesLocation = filesLocation + File.separator + "SBF";
		} else {
			filesLocation = System.getProperty("user.home");
			launcherFilesLocation = filesLocation + File.separator + ".SBF";
		}

		// loading images
		Images.loadImages();
	}

	private static void makeFrame() {
		// setting frame and main panel
		frame.setIconImage(Images.getIcon());
		frame.setTitle("SBF Launcher Installer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 475);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		mainPanel.setLayout(null);
		mainPanel.setMainImage(false);
		frame.setContentPane(mainPanel);

		// adding components
		TransparentPanel panel = new TransparentPanel();
		panel.setLayout(null);
		panel.setBounds(225, 0, 475, 475);

		// labels
		JLabel installLabel = new JLabel("Установка");
		installLabel.setBounds(20, 40, 200, 20);
		installLabel.setFont(new Font(null, Font.PLAIN, 24));
		installLabel.setForeground(Color.WHITE);
		panel.add(installLabel);

		JLabel sbfMCLauncherLabel = new JLabel("SBF Minecraft Launcher");
		sbfMCLauncherLabel.setBounds(20, 80, 200, 20);
		sbfMCLauncherLabel.setFont(new Font(null, Font.PLAIN, 16));
		sbfMCLauncherLabel.setForeground(Color.WHITE);
		panel.add(sbfMCLauncherLabel);

		// process
		final JLabel processLabel = new JLabel("Установка...");
		processLabel.setBounds(20, 160, 200, 20);
		processLabel.setFont(new Font(null, Font.PLAIN, 14));
		processLabel.setForeground(Color.WHITE);
		processLabel.setVisible(false);
		panel.add(processLabel);

		final JLabel downloadLabel = new JLabel("Загружено 0 МБ");
		downloadLabel.setBounds(20, 190, 200, 20);
		downloadLabel.setFont(new Font(null, Font.PLAIN, 14));
		downloadLabel.setForeground(Color.WHITE);
		downloadLabel.setVisible(false);
		panel.add(downloadLabel);

		// buttons
		final ImageButton installButton = new ImageButton();
		installButton.setText("Установить");
		installButton.setBounds(20, 420, 90, 30);
		installButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Installer.install();

				processLabel.setVisible(true);
				downloadLabel.setVisible(true);

				new Thread("download-status-updater") {
					@Override
					public void run() {
						while (true) {
							if (Installer.getCurrentDownload() != null) {
								downloadLabel.setText("Загружено " + Installer.getCurrentDownload().getDownloaded() + " МБ");
							}
							try {
								Thread.sleep(500);
							} catch (InterruptedException err) {
								err.printStackTrace();
							}
						}
					}
				}.start();
			}
		});
		panel.add(installButton);

		mainPanel.add(panel);

		// frame show
		frame.setVisible(true);

		// setting right size
		frame.setSize(frame.getWidth() * 2 - mainPanel.getWidth(), frame.getHeight() * 2 - mainPanel.getHeight());
	}

	public static byte getOS() {
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

	public static JFrame getFrame() {
		return frame;
	}

	public static ImagePanel getMainPanel() {
		return mainPanel;
	}
}
