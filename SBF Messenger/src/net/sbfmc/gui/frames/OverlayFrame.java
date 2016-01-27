package net.sbfmc.gui.frames;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;

import net.sbfmc.def.Core;
import net.sbfmc.gui.MyButton;
import net.sbfmc.gui.MyFrame;
import net.sbfmc.gui.MyLabel;

public class OverlayFrame extends MyFrame {
	private static final long serialVersionUID = 8422197688256552522L;

	private MyLabel lastMessageLabel;

	@Override
	protected void build() {
		setAlwaysOnTop(true);
		setLayout(new BorderLayout(5, 5));
		((JComponent) getContentPane()).setBorder(new EmptyBorder(7, 7, 7, 7));

		MyButton button = new MyButton("^");
		button.setFont(new Font(null, Font.PLAIN, 14));
		button.setBorder(new EmptyBorder(5, 5, 5, 5));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Core.getMainFrame().setVisible(true);
				Core.updateView();
				setVisible(false);
			}
		});
		add(button, BorderLayout.WEST);
		button.setSelected(false);

		lastMessageLabel = new MyLabel();
		add(lastMessageLabel);
	}

	@Override
	public int getWidth() {
		return 250;
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

	public MyLabel getLastMessageLabel() {
		return lastMessageLabel;
	}
}
