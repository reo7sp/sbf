package net.sbfmc.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

public class MyPasswordTextBox extends JPasswordField {
	private static final long serialVersionUID = 4443417341120211933L;

	public MyPasswordTextBox() {
		super(18);

		setFont(new Font(null, Font.PLAIN, 11));
		setForeground(Color.DARK_GRAY);
		setBorder(new EmptyBorder(5, 5, 5, 5));
	}
}
