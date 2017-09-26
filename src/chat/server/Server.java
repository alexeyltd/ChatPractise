package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Server {

	private static final int DEFAULT_PORT = 10000;
	private static List<Session> sessions;

	public static void main(String[] args) {
		System.out.println("start");
		sessions = new ArrayList<>();
		try {
			ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Got connection " + socket);
				new Thread(() -> {
					Session session = new Session();
					sessions.add(session);
					session.processConnection(socket, Server::broadCast);
				}).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void broadCast(String line) {
		for (Session session1 : sessions) {
			session1.sendToClient(line);
		}
	}

}
