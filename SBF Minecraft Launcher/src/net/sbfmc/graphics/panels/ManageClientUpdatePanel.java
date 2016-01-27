package net.sbfmc.graphics.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sbfmc.graphics.MenuPanel;
import net.sbfmc.utils.Updater;

public class ManageClientUpdatePanel extends MenuPanel {
	private static final long serialVersionUID = 23990311589858908L;
	private static ManageClientUpdatePanel instance;

	private JLabel totalMBLabel;
	private JCheckBox nativesSubCheckBox;
	private JCheckBox binSubCheckBox;
	private JCheckBox resourcesSubCheckBox;
	private JCheckBox binCheckBox;
	private JCheckBox nativesCheckBox;
	private JCheckBox resourcesCheckBox;
	private JCheckBox minecraftJarCheckBox;
	private JCheckBox modsCheckBox;
	private JCheckBox modsSubCheckBox;

	public static ManageClientUpdatePanel getInstance() {
		if (instance == null) {
			instance = new ManageClientUpdatePanel();
		}
		return instance;
	}

	private ManageClientUpdatePanel() {
		super(330, 380, true);
	}

	@Override
	public void makePanel(JPanel panel) {
		final JCheckBox minecraftCheckBox = new JCheckBox(".minecraft");
		minecraftCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				minecraftCheckBox.setSelected(true);
			}
		});
		minecraftCheckBox.setSelected(true);
		minecraftCheckBox.setFont(new Font(null, Font.PLAIN, 12));
		minecraftCheckBox.setForeground(Color.WHITE);
		minecraftCheckBox.setOpaque(false);
		minecraftCheckBox.setBounds(0, 0, 130, 25);
		panel.add(minecraftCheckBox);

		binCheckBox = new JCheckBox("bin (8 МБ)");
		binCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				binSubCheckBox.setSelected(binCheckBox.isSelected());
				Updater.getInstance().setUpdateBin(binCheckBox.isSelected());

				nativesCheckBox.setSelected(binCheckBox.isSelected());
				nativesSubCheckBox.setSelected(binCheckBox.isSelected());
				Updater.getInstance().setUpdateNatives(binCheckBox.isSelected());

				minecraftJarCheckBox.setSelected(binCheckBox.isSelected());
				Updater.getInstance().setUpdateMinecraftJar(binCheckBox.isSelected());

				reCalculateTotal();
			}
		});
		binCheckBox.setSelected(Updater.getInstance().isUpdateBin());
		binCheckBox.setFont(new Font(null, Font.PLAIN, 12));
		binCheckBox.setForeground(Color.WHITE);
		binCheckBox.setOpaque(false);
		binCheckBox.setBounds(20, 30, 200, 25);
		panel.add(binCheckBox);

		nativesCheckBox = new JCheckBox("natives (2 МБ)");
		nativesCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				nativesSubCheckBox.setSelected(nativesCheckBox.isSelected());
				Updater.getInstance().setUpdateNatives(nativesCheckBox.isSelected());

				reCalculateTotal();
			}
		});
		nativesCheckBox.setSelected(Updater.getInstance().isUpdateNatives());
		nativesCheckBox.setFont(new Font(null, Font.PLAIN, 12));
		nativesCheckBox.setForeground(Color.WHITE);
		nativesCheckBox.setOpaque(false);
		nativesCheckBox.setBounds(40, 60, 200, 25);
		panel.add(nativesCheckBox);

		nativesSubCheckBox = new JCheckBox("...");
		nativesSubCheckBox.setEnabled(false);
		nativesSubCheckBox.setFont(new Font(null, Font.PLAIN, 12));
		nativesSubCheckBox.setForeground(Color.WHITE);
		nativesSubCheckBox.setOpaque(false);
		nativesSubCheckBox.setBounds(60, 90, 160, 25);
		panel.add(nativesSubCheckBox);

		minecraftJarCheckBox = new JCheckBox("minecraft.jar (5 МБ)");
		minecraftJarCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Updater.getInstance().setUpdateMinecraftJar(minecraftJarCheckBox.isSelected());

				reCalculateTotal();
			}
		});
		minecraftJarCheckBox.setSelected(Updater.getInstance().isUpdateMinecraftJar());
		minecraftJarCheckBox.setFont(new Font(null, Font.PLAIN, 12));
		minecraftJarCheckBox.setForeground(Color.WHITE);
		minecraftJarCheckBox.setOpaque(false);
		minecraftJarCheckBox.setBounds(40, 120, 200, 25);
		panel.add(minecraftJarCheckBox);

		binSubCheckBox = new JCheckBox("...");
		binSubCheckBox.setOpaque(false);
		binSubCheckBox.setForeground(Color.WHITE);
		binSubCheckBox.setFont(new Font(null, Font.PLAIN, 12));
		binSubCheckBox.setEnabled(false);
		binSubCheckBox.setBounds(40, 150, 200, 25);
		panel.add(binSubCheckBox);

		resourcesCheckBox = new JCheckBox("resources (40 МБ)");
		resourcesCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				resourcesSubCheckBox.setSelected(resourcesCheckBox.isSelected());
				Updater.getInstance().setUpdateResources(resourcesCheckBox.isSelected());

				reCalculateTotal();
			}
		});
		resourcesCheckBox.setSelected(Updater.getInstance().isUpdateResources());
		resourcesCheckBox.setFont(new Font(null, Font.PLAIN, 12));
		resourcesCheckBox.setForeground(Color.WHITE);
		resourcesCheckBox.setOpaque(false);
		resourcesCheckBox.setBounds(20, 180, 200, 25);
		panel.add(resourcesCheckBox);

		resourcesSubCheckBox = new JCheckBox("...");
		resourcesSubCheckBox.setOpaque(false);
		resourcesSubCheckBox.setForeground(Color.WHITE);
		resourcesSubCheckBox.setFont(new Font(null, Font.PLAIN, 12));
		resourcesSubCheckBox.setEnabled(false);
		resourcesSubCheckBox.setBounds(40, 210, 200, 25);
		panel.add(resourcesSubCheckBox);

		modsCheckBox = new JCheckBox("mods (? МБ)");
		modsCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				modsSubCheckBox.setSelected(modsCheckBox.isSelected());
				Updater.getInstance().setUpdateMods(modsCheckBox.isSelected());

				reCalculateTotal();
			}
		});
		modsCheckBox.setSelected(Updater.getInstance().isUpdateMods());
		modsCheckBox.setFont(new Font(null, Font.PLAIN, 12));
		modsCheckBox.setForeground(Color.WHITE);
		modsCheckBox.setOpaque(false);
		modsCheckBox.setBounds(20, 240, 200, 25);
		panel.add(modsCheckBox);

		modsSubCheckBox = new JCheckBox("...");
		modsSubCheckBox.setOpaque(false);
		modsSubCheckBox.setForeground(Color.WHITE);
		modsSubCheckBox.setFont(new Font(null, Font.PLAIN, 12));
		modsSubCheckBox.setEnabled(false);
		modsSubCheckBox.setBounds(40, 270, 200, 25);
		panel.add(modsSubCheckBox);

		totalMBLabel = new JLabel();
		totalMBLabel.setFont(new Font(null, Font.PLAIN, 12));
		totalMBLabel.setForeground(Color.WHITE);
		totalMBLabel.setBounds(0, 310, 200, 15);
		panel.add(totalMBLabel);

		reCalculateTotal();
	}

	private void reCalculateTotal() {
		int totalMB = 0;

		if (binCheckBox.isSelected()) {
			totalMB += 1;
		}
		if (minecraftJarCheckBox.isSelected()) {
			totalMB += 5;
		}
		if (nativesCheckBox.isSelected()) {
			totalMB += 2;
		}
		if (resourcesCheckBox.isSelected()) {
			totalMB += 40;
		}

		totalMBLabel.setText("Всего " + (modsCheckBox.isSelected() ? "более " : "") + totalMB + " МБ");
	}

	@Override
	public void closeAction() {
		changePanel(AdditionalPanel.getInstance());
	}

	@Override
	public String getName() {
		return "Настройки обновления";
	}
}
