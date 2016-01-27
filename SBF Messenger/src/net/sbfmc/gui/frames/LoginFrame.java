package net.sbfmc.gui.frames;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.sbfmc.def.Core;
import net.sbfmc.gui.MyButton;
import net.sbfmc.gui.MyFrame;
import net.sbfmc.gui.MyLabel;
import net.sbfmc.gui.MyPasswordTextBox;
import net.sbfmc.gui.MyTextBox;

import org.bukkit.ChatColor;

public class LoginFrame extends MyFrame {
	private static final long serialVersionUID = -2797999602864637459L;

	@Override
	protected void build() {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(150, 100, 100, 100));
		panel.setOpaque(false);

		JPanel labesPanel = new JPanel(new BorderLayout(5, 5));
		labesPanel.setOpaque(false);
		labesPanel.add(new MyLabel("Ник: "), BorderLayout.NORTH);
		labesPanel.add(new MyLabel("Пароль: "), BorderLayout.SOUTH);
		panel.add(labesPanel, BorderLayout.WEST);

		JPanel textBoxesPanel = new JPanel(new BorderLayout(5, 5));
		textBoxesPanel.setOpaque(false);
		final MyTextBox nickTextBox = new MyTextBox();
		textBoxesPanel.add(nickTextBox, BorderLayout.NORTH);
		final MyPasswordTextBox passwordTextBox = new MyPasswordTextBox();
		textBoxesPanel.add(passwordTextBox, BorderLayout.SOUTH);
		panel.add(textBoxesPanel, BorderLayout.EAST);

		JPanel buttonsPanel = new JPanel(new BorderLayout(5, 5));
		buttonsPanel.setOpaque(false);
		buttonsPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
		MyButton enterButton = new MyButton("Вход");
		enterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (Core.getClient() != null) {
					Core.getClient().login(nickTextBox.getText(), new String(passwordTextBox.getPassword()));
				} else {
					Core.addMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Ошибка: " + ChatColor.RED + "Невозможно зайти на сервер");
				}

				Core.getMainFrame().setVisible(true);
				Core.updateView();
				setVisible(false);
			}
		});
		buttonsPanel.add(enterButton, BorderLayout.NORTH);
		MyButton closeButton = new MyButton("Закрыть");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		buttonsPanel.add(closeButton, BorderLayout.SOUTH);
		panel.add(buttonsPanel);

		add(panel);
	}

	@Override
	public int getWidth() {
		return 500;
	}

	@Override
	public int getHeight() {
		return 500;
	}
}
