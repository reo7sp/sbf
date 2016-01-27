package net.sbfmc.graphics.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.sbfmc.graphics.ImageButton;
import net.sbfmc.graphics.MenuPanel;
import net.sbfmc.utils.Updater;

public class UpdatePanel extends MenuPanel {
	private static final long serialVersionUID = -183819733820640452L;
	private static UpdatePanel instance;

	private JLabel label;

	private UpdatePanel() {
		super(200, 60, false);
	}

	public static UpdatePanel getInstance() {
		if (instance == null) {
			instance = new UpdatePanel();
		}
		return instance;
	}

	public void updateLabel(int MB) {
		label.setText("Загружено " + MB + " МБ");
	}

	@Override
	public void makePanel(JPanel panel) {
		label = new JLabel();
		label.setForeground(Color.WHITE);
		label.setText("Идёт загрузка...");
		label.setFont(new Font(null, Font.PLAIN, 12));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, panel.getWidth(), 20);
		panel.add(label);

		final ImageButton pauseButton = new ImageButton();
		pauseButton.setText("Пауза");
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (Updater.getInstance().getCurrentDownload() != null) {
					if (Updater.getInstance().getCurrentDownload().isPaused()) {
						Updater.getInstance().getCurrentDownload().resumeDownload();
						pauseButton.setText("Пуск");
					} else {
						Updater.getInstance().getCurrentDownload().pauseDownload();
						pauseButton.setText("Пауза");
					}
				}
			}
		});
		pauseButton.setBounds(panel.getWidth() / 2 - 80, 30, 75, 25);
		panel.add(pauseButton);

		ImageButton cancelButton = new ImageButton();
		cancelButton.setBounds(panel.getWidth() / 2 + 5, 30, 75, 25);
		cancelButton.setText("Отмена");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Updater.getInstance().stopUpdateClient();
				Updater.getInstance().stopUpdateLauncher();
			}
		});
		panel.add(cancelButton);
	}

	@Override
	public void closeAction() {
		System.exit(0);
	}

	@Override
	public String getName() {
		return "Обновление";
	}

}
