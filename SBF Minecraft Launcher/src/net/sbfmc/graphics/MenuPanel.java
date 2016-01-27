package net.sbfmc.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import net.sbfmc.def.Core;
import net.sbfmc.def.Images;

public abstract class MenuPanel extends ImagePanel {
	private static final long serialVersionUID = 8863995094444449116L;

	private int panelWidth;
	private int panelHeight;
	private int panelPositionX;
	private int panelPositionY;
	private boolean showAppearance;
	private JPanel panel;
	private boolean openAnimationRunning;
	private boolean closeAnimationRunning;

	protected MenuPanel(int panelWidth, int panelHeight, boolean showAppearance) {
		this.showAppearance = showAppearance;
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		panelPositionX = Core.getWidth() / 2 - panelWidth / 2;
		panelPositionY = Core.getHeight() / 2 - panelHeight / 2;

		setMainImage(false);
		setLayout(null);

		if (showAppearance) {
			panel = new TransparentPanel();
			panel.setLayout(null);
			panel.setBounds(panelPositionX, panelPositionY, panelWidth, panelHeight);
			add(panel);

			JPanel controlsPanel = new JPanel();
			controlsPanel.setLayout(null);
			controlsPanel.setOpaque(false);
			controlsPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.WHITE));
			controlsPanel.setBounds(10, 10, panelWidth - 20, 25);
			panel.add(controlsPanel);

			JLabel nameLabel = new JLabel();
			nameLabel.setText(getName());
			nameLabel.setForeground(Color.WHITE);
			nameLabel.setFont(new Font(null, Font.PLAIN, 14));
			nameLabel.setBounds(0, 5, controlsPanel.getWidth(), 15);
			controlsPanel.add(nameLabel);

			ImageButton closeButton = new ImageButton();
			closeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					closeAction();
				}
			});
			closeButton.setBounds(controlsPanel.getWidth() - 30, 0, 20, 20);
			closeButton.setBgImage(Images.getCross());
			closeButton.setOverBgImage(Images.getCrossOver());
			closeButton.setDownBgImage(Images.getCrossDown());
			controlsPanel.add(closeButton);

			JPanel panel1 = new JPanel();
			panel1.setOpaque(false);
			panel1.setBounds(10, 45, panelWidth - 20, panelHeight - 55);
			panel1.setLayout(null);
			panel.add(panel1);

			makePanel(panel1);
		} else {
			JPanel panel = new JPanel();
			panel.setLayout(null);
			panel.setBounds(panelPositionX, panelPositionY, panelWidth, panelHeight);
			panel.setOpaque(false);
			add(panel);

			makePanel(panel);
		}
	}

	public abstract void makePanel(JPanel panel);

	public abstract void closeAction();

	@Override
	public abstract String getName();

	protected void changePanel(final JPanel panel) {
		closeAnimation();

		new Thread("change-panel") {
			@Override
			public void run() {
				try {
					while (closeAnimationRunning) {
						Thread.sleep(100);
					}
				} catch (InterruptedException err) {
					err.printStackTrace();
				}

				Core.getFrame().setContentPane(panel);
				Core.getFrame().validate();
				Core.getFrame().repaint();

				if (panel instanceof MenuPanel) {
					((MenuPanel) panel).openAnimation();
				}
			}
		}.start();
	}

	protected void returnToMainPanel() {
		changePanel(Core.getMainPanel());
	}

	public void changeToThis() {
		Core.getFrame().setContentPane(this);
		Core.getFrame().validate();
		Core.getFrame().repaint();

		openAnimation();
	}

	public void openAnimation() {
		if (openAnimationRunning || !showAppearance) {
			return;
		}
		openAnimationRunning = true;

		new Thread("open-animation") {
			@Override
			public void run() {
				synchronized (panel) {
					try {
						panel.setLocation(panelPositionX, -panelHeight);

						for (int i = 0; i < panelPositionY + panelHeight;) {
							float percent = (float) i / (panelHeight + panelPositionY);
							i += (1F - percent) * 100 / 8;
							if ((int) ((1F - percent) * 100 / 8) == 0) {
								break;
							}
							panel.setLocation(panelPositionX, i - panelHeight + 29);
							Thread.sleep(8);
						}

						panel.setLocation(panelPositionX, panelPositionY);

						openAnimationRunning = false;
					} catch (InterruptedException err) {
						err.printStackTrace();
					}
				}
			}
		}.start();
	}

	public void closeAnimation() {
		if (closeAnimationRunning || !showAppearance) {
			return;
		}
		closeAnimationRunning = true;

		new Thread("close-animation") {
			@Override
			public void run() {
				synchronized (panel) {
					try {
						panel.setLocation(panelPositionX, panelPositionY);

						for (int i = 0; i < panelPositionY + panelHeight + 1;) {
							float percent = (float) i / (panelHeight + panelPositionY + 1);
							i += (1F - percent) * 100 / 8;
							if ((int) ((1F - percent) * 100 / 8) == 0) {
								break;
							}
							panel.setLocation(panelPositionX, panelPositionY + i + 29);
							Thread.sleep(8);
						}

						closeAnimationRunning = false;

						Thread.sleep(1000);

						panel.setLocation(panelPositionX, panelPositionY);
					} catch (InterruptedException err) {
						err.printStackTrace();
					}
				}
			}
		}.start();
	}

	public int getPanelWidth() {
		return panelWidth;
	}

	public int getPanelHeight() {
		return panelHeight;
	}

	public int getPanelPositionX() {
		return panelPositionX;
	}

	public int getPanelPositionY() {
		return panelPositionY;
	}

	public boolean isShowAppearance() {
		return showAppearance;
	}

	public boolean isOpenAnimationRunning() {
		return openAnimationRunning;
	}

	public boolean isCloseAnimationRunning() {
		return closeAnimationRunning;
	}
}
