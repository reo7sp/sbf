package net.sbfmc.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class MyLabel extends JLabel {
	private static final long serialVersionUID = -16390185571713791L;

	public MyLabel() {
		this("");
	}

	public MyLabel(String name) {
		super(name);

		setFont(new Font(null, Font.PLAIN, 11));
		setForeground(Color.WHITE);
		setBorder(new EmptyBorder(5, 5, 5, 5));
	}
}
