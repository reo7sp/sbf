package net.sbfmc.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MyButton extends JButton {
	private static final long serialVersionUID = 7179081270079197473L;

	public MyButton() {
		this("");
	}

	public MyButton(String name) {
		super(name);

		setFont(new Font(null, Font.PLAIN, 11));
		setForeground(Color.WHITE);
		setContentAreaFilled(false);
		setFocusable(false);
		setBorder(null);
		setBorder(new CompoundBorder(new LineBorder(Color.WHITE), new EmptyBorder(5, 15, 5, 15)));
	}
}
