package net.sbfmc.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientCommunicator extends Thread {
	private Client client;
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;

	ClientCommunicator(Client client) throws UnknownHostException, IOException {
		this.client = client;
		socket = new Socket(Client.IP, Client.PORT);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

		start();
	}

	@Override
	public void run() {
		try {
			// parsing response
			String response;
			while ((response = input.readLine()) != null) {
				client.parseResponse(response);
			}
		} catch (IOException err) {
			err.printStackTrace();
		}

		// disconnecting
		try {
			client.disconnect("Конец потока");
		} catch (IOException err) {
			err.printStackTrace();
		}
	}

	public void disconnect() throws IOException {
		input.close();
		output.close();
		socket.close();
	}

	public Client getClient() {
		return client;
	}

	public Socket getSocket() {
		return socket;
	}

	public BufferedReader getInput() {
		return input;
	}

	public PrintWriter getOutput() {
		return output;
	}
}
