package net.sbfmc.graphics.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import net.sbfmc.def.Core;
import net.sbfmc.graphics.MenuPanel;

public class LogPanel extends MenuPanel {
	private static final long serialVersionUID = 1L;
	private static LogPanel instance;

	private JTextArea textArea;

	public static LogPanel getInstance() {
		if (instance == null) {
			instance = new LogPanel();
		}
		return instance;
	}

	private LogPanel() {
		super(Core.getWidth() - 20, Core.getHeight() - 20, false);
	}

	public static void addLine(String line) {
		getInstance().textArea.append(line + "\n");
	}

	@Override
	public void makePanel(JPanel panel) {
		textArea = new JTextArea();
		panel.setLayout(new BorderLayout());

		textArea.setEditable(false);
		textArea.setFont(null);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		((DefaultCaret) textArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane textAreaScrollPane = new JScrollPane(textArea);
		textAreaScrollPane.setAutoscrolls(true);
		panel.add(textAreaScrollPane);
	}

	@Override
	public void closeAction() {
		System.exit(0);
	}

	@Override
	public String getName() {
		return "Лог Minecraft";
	}
}
