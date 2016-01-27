package net.sbfmc.graphics.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.sbfmc.def.Core;
import net.sbfmc.graphics.ImageButton;
import net.sbfmc.graphics.MenuPanel;
import net.sbfmc.utils.Updater;

public class AdditionalPanel extends MenuPanel {
	private static final long serialVersionUID = -1319663358921844705L;
	private static AdditionalPanel instance;

	public static AdditionalPanel getInstance() {
		if (instance == null) {
			instance = new AdditionalPanel();
		}
		return instance;
	}

	private AdditionalPanel() {
		super(260, 210, true);
	}

	@Override
	public void makePanel(JPanel panel) {
		final ImageButton cancelUpdateButton = new ImageButton();
		cancelUpdateButton.setEnabled(false);

		JLabel ramLabel = new JLabel("Кол-во памяти (в МБ):");
		ramLabel.setFont(new Font(null, Font.PLAIN, 12));
		ramLabel.setForeground(Color.WHITE);
		ramLabel.setBounds(0, 2, 225, 15);
		panel.add(ramLabel);

		final JTextField ramTextBox = new JTextField();
		ramTextBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				try {
					Core.setRam(Integer.parseInt(ramTextBox.getText()));
				} catch (Exception err) {
				}
			}
		});
		ramTextBox.setBorder(new EmptyBorder(5, 5, 5, 5));
		ramTextBox.setBounds(160, 0, 75, 20);
		ramTextBox.setText("" + Core.getRam());
		panel.add(ramTextBox);

		final JCheckBox showLogCheckBox = new JCheckBox("Показывать лог");
		showLogCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Core.setShowLog(showLogCheckBox.isSelected());
			}
		});
		showLogCheckBox.setFont(new Font(null, Font.PLAIN, 12));
		showLogCheckBox.setForeground(Color.WHITE);
		showLogCheckBox.setSelected(Core.isShowLog());
		showLogCheckBox.setOpaque(false);
		showLogCheckBox.setBounds(0, 25, 225, 25);
		panel.add(showLogCheckBox);

		final JLabel clientLabel = new JLabel("Клиент");
		clientLabel.setFont(new Font(null, Font.PLAIN, 12));
		clientLabel.setForeground(Color.WHITE);
		clientLabel.setBounds(0, 58, 225, 15);
		panel.add(clientLabel);

		final ImageButton updateClientButton = new ImageButton();
		updateClientButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (Updater.getInstance().isUpdateClient()) {
					changePanel(ManageClientUpdatePanel.getInstance());
				} else {
					Updater.getInstance().setUpdateClient(true);
					updateClientButton.setText("Изменить");
					clientLabel.setText("Будет обновлено!");
					clientLabel.setForeground(Color.RED);
					cancelUpdateButton.setEnabled(true);
				}
			}
		});
		updateClientButton.setText("Обновить!");
		updateClientButton.setBounds(160, 55, 75, 25);
		panel.add(updateClientButton);

		final JLabel launcherLabel = new JLabel("Лаунчер");
		launcherLabel.setFont(new Font(null, Font.PLAIN, 12));
		launcherLabel.setForeground(Color.WHITE);
		launcherLabel.setBounds(0, 88, 225, 15);
		panel.add(launcherLabel);

		final ImageButton updateLauncherButton = new ImageButton();
		updateLauncherButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Updater.getInstance().setUpdateLauncher(true);
				cancelUpdateButton.setEnabled(true);
				launcherLabel.setText("Будет обновлено!");
				launcherLabel.setForeground(Color.RED);
				updateLauncherButton.setEnabled(false);
			}
		});
		updateLauncherButton.setText("Обновить!");
		updateLauncherButton.setBounds(160, 85, 75, 25);
		panel.add(updateLauncherButton);

		ImageButton modsButton = new ImageButton();
		modsButton.setBounds(20, 130, 95, 25);
		modsButton.setText("Моды");
		modsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeAction();
				try {
					Runtime.getRuntime().exec("java -jar " + Core.getLauncherFilesLocation() + File.separator + "mcpatcher.jar");
				} catch (IOException err) {
					err.printStackTrace();
				}
			}
		});
		panel.add(modsButton);

		cancelUpdateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cancelUpdateButton.setEnabled(false);

				Updater.getInstance().setUpdateClient(false);
				clientLabel.setText("Клиент");
				clientLabel.setForeground(Color.WHITE);
				updateClientButton.setEnabled(true);
				updateClientButton.setText("Обновить!");

				Updater.getInstance().setUpdateLauncher(false);
				launcherLabel.setText("Лаунчер");
				launcherLabel.setForeground(Color.WHITE);
				updateLauncherButton.setEnabled(true);
				updateLauncherButton.setText("Обновить!");
			}
		});
		cancelUpdateButton.setText("Отменить обновление");
		cancelUpdateButton.setBounds(125, 130, 95, 25);
		panel.add(cancelUpdateButton);
	}

	@Override
	public void closeAction() {
		returnToMainPanel();
	}

	@Override
	public String getName() {
		return "Дополнительно";
	}
}