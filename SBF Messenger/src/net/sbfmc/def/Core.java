package net.sbfmc.def;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Set;

import net.sbfmc.client.Client;
import net.sbfmc.gui.frames.LoginFrame;
import net.sbfmc.gui.frames.MainFrame;
import net.sbfmc.gui.frames.MiniOverlayFrame;
import net.sbfmc.gui.frames.OverlayFrame;
import net.sbfmc.util.ChatColorer;
import net.sbfmc.util.GeneralUtils;

import org.bukkit.ChatColor;

public class Core {
	private static MainFrame mainFrame = new MainFrame();
	private static LoginFrame loginFrame = new LoginFrame();
	private static OverlayFrame overlayFrame = new OverlayFrame();
	private static MiniOverlayFrame miniOverlayFrame = new MiniOverlayFrame();
	private static Client client;
	private static HashMap<String, Long> messages = new HashMap<String, Long>();
	private static long lastView;

	public static void main(String[] args) {
		mainFrame.setVisible(true);
		getClient().login("", ""); // connect
	}

	public static Client getClient() {
		if (client == null) {
			try {
				addMessage(ChatColor.GREEN + "Подключение...");
				client = Client.connect();
				addMessage(ChatColor.GREEN + "Подключено!");
				addMessage("");
			} catch (UnknownHostException err) {
				err.printStackTrace();
				addMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Ошибка: " + ChatColor.RED + "Неправильный путь");
			} catch (IOException err) {
				err.printStackTrace();
				addMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Ошибка: " + ChatColor.RED + "Проблемы с соединением");
			} catch (Exception err) {
				err.printStackTrace();
				addMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Ошибка: " + ChatColor.RED + err);
			}
		}

		return client;
	}

	public static Set<String> getMessages() {
		return messages.keySet();
	}

	public static HashMap<String, Long> getMessagesWithTime() {
		return messages;
	}

	public static void addMessage(String message) {
		String transformedMessage = ChatColorer.toHtml(message);
		messages.put(transformedMessage, System.currentTimeMillis());

		mainFrame.getChatTextArea().append(transformedMessage.replace("#FFFFFF", "#000000") + "<br>");
		overlayFrame.getLastMessageLabel().setText("<html>" +
				transformedMessage.replace(GeneralUtils.getHexColor(overlayFrame.getBackground()), "#FFFFFF"));

		if (mainFrame.isVisible() || overlayFrame.isVisible()) {
			updateView();
		}

		miniOverlayFrame.updateCount();
	}

	public static void updateView() {
		Core.lastView = System.currentTimeMillis();
		miniOverlayFrame.updateCount();
	}

	public static MainFrame getMainFrame() {
		return mainFrame;
	}

	public static LoginFrame getLoginFrame() {
		return loginFrame;
	}

	public static OverlayFrame getOverlayFrame() {
		return overlayFrame;
	}

	public static MiniOverlayFrame getMiniOverlayFrame() {
		return miniOverlayFrame;
	}

	public static long getLastView() {
		return lastView;
	}
}
