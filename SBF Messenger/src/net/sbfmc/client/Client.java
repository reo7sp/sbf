package net.sbfmc.client;

import java.io.IOException;
import java.net.UnknownHostException;

import net.sbfmc.def.Core;

import org.bukkit.ChatColor;

public class Client {
	public static final String IP = "localhost";
	public static final int PORT = 17879;
	private static final int CLIENT_SECRET = 154101494;

	private ClientCommunicator communicator;

	private Client() throws UnknownHostException, IOException {
		communicator = new ClientCommunicator(this);
	}

	public static Client connect() throws UnknownHostException, IOException {
		return new Client();
	}

	public void disconnect() throws IOException {
		disconnect(null);
	}

	public void disconnect(String reason) throws IOException {
		sendRequest("DISCONNECT");
		communicator.disconnect();
		Core.addMessage(ChatColor.RED + "Отключен сервером" + (reason == null ? "" : ". " + ChatColor.BLUE + reason));
	}

	public void sendMessage(String message) {
		sendRequest("CHAT " + message);
	}

	public void login(String user, String password) {
		sendMessage("HEADER MESSENGER");
		sendRequest("SECRET " + Client.CLIENT_SECRET);
		sendRequest("NAME " + user);
		sendRequest("LOGIN " + password);
	}

	void sendRequest(String request) {
		communicator.getOutput().println(request);
	}

	void parseResponse(String response) {
		String[] responseParts = response.split(" ", 2);
		parseResponse(responseParts[0], responseParts.length > 1 ? responseParts[1] : null);
	}

	void parseResponse(String key, String value) {
		if (key.equals("CHAT")) {
			// adding message
			Core.addMessage(value);
		} else if (key.equals("DISCONNECT")) {
			// disconnecting
			try {
				disconnect(value);
			} catch (IOException err) {
				err.printStackTrace();
			}
		}
	}
}
