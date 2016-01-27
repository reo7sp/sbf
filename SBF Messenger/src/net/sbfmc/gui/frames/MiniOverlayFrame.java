package net.sbfmc.gui.frames;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;

import net.sbfmc.def.Core;
import net.sbfmc.gui.MyButton;
import net.sbfmc.gui.MyFrame;

public class MiniOverlayFrame extends MyFrame {
	private static final long serialVersionUID = 8422197688256552522L;

	private MyButton button;

	@Override
	protected void build() {
		setAlwaysOnTop(true);
		setLayout(new FlowLayout());
		((JComponent) getContentPane()).setBorder(new EmptyBorder(7, 7, 7, 7));

		button = new MyButton("0");
		button.setFont(new Font(null, Font.PLAIN, 14));
		button.setBorder(null);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Core.getOverlayFrame().setVisible(true);
				setVisible(false);
			}
		});
		add(button);
	}

	public void updateCount() {
		int count = 0;

		for (String message : Core.getMessages()) {
			if (Core.getMessagesWithTime().get(message) - Core.getLastView() > 0) {
				count++;
			}
		}

		button.setText("" + count);
	}

	@Override
	public int getWidth() {
		return 40;
	}

	@Override
	public int getHeight() {
		return 40;
	}

	@Override
	public int getX() {
		return 50;
	}

	@Override
	public int getY() {
		return Toolkit.getDefaultToolkit().getScreenSize().height - 100;
	}
}
