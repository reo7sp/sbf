package net.sbfmc.gui.frames;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import net.sbfmc.def.Core;
import net.sbfmc.gui.MyButton;
import net.sbfmc.gui.MyFrame;
import net.sbfmc.gui.MyLabel;
import net.sbfmc.gui.MyTextArea;
import net.sbfmc.gui.MyTextBox;

public class MainFrame extends MyFrame {
	private static final long serialVersionUID = 3708489476085850677L;

	private MyTextArea chatTextArea;

	@Override
	protected void build() {
		setLayout(new BorderLayout(5, 5));
		((JComponent) getContentPane()).setBorder(new EmptyBorder(5, 10, 10, 10));

		JPanel headerPanel = new JPanel(new BorderLayout(5, 5));
		headerPanel.setOpaque(false);
		MyLabel headerLabel = new MyLabel("SBF Messenger");
		headerLabel.setFont(new Font(null, Font.PLAIN, 14));
		headerPanel.add(headerLabel);
		JPanel headerButtonsPanel = new JPanel();
		headerButtonsPanel.setOpaque(false);
		MyButton minimizeButton = new MyButton("_");
		minimizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Core.getMiniOverlayFrame().setVisible(true);
				setVisible(false);
			}
		});
		headerButtonsPanel.add(minimizeButton);
		MyButton closeButton = new MyButton("x");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		headerButtonsPanel.add(closeButton);
		headerPanel.add(headerButtonsPanel, BorderLayout.EAST);
		add(headerPanel, BorderLayout.NORTH);

		chatTextArea = new MyTextArea();
		chatTextArea.setEditable(false);
		JScrollPane chatScrollPane = new JScrollPane(chatTextArea);
		chatScrollPane.setBorder(null);
		add(chatScrollPane, BorderLayout.CENTER);

		JPanel controlsPanel = new JPanel(new BorderLayout(10, 5));
		controlsPanel.setOpaque(false);
		controlsPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
		MyTextBox messageTextBox = new MyTextBox();
		controlsPanel.add(messageTextBox, BorderLayout.CENTER);
		MyButton sendButton = new MyButton("Отправить");
		controlsPanel.add(sendButton, BorderLayout.EAST);
		// add(controlsPanel, BorderLayout.SOUTH);
	}

	@Override
	public int getWidth() {
		return 500;
	}

	@Override
	public int getHeight() {
		return 500;
	}

	public MyTextArea getChatTextArea() {
		return chatTextArea;
	}
}
