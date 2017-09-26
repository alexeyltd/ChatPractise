package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	private static final int DEFAULT_PORT = 10000;
	private static List<Session> sessions;
	private static ExecutorService broadCastService;

	public static void main(String[] args) {
		System.out.println("start");
		sessions = new ArrayList<>();
		broadCastService = Executors.newCachedThreadPool();
		try {
			ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Got connection " + socket);
				broadCastService.execute(() -> {
					Session session = new Session();
					sessions.add(session);
					session.processConnection(socket, Server::broadCast, Server::removeSession);
				});
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void broadCast(String line) {
		for (Session session1 : sessions) {
			broadCastService.execute(() -> {
				session1.sendToClient(line);
			});
		}
	}

	private static void removeSession(Session session) {
		sessions.remove(session);
		System.out.println("removed " + session);
		System.out.println("sessions size = " + sessions.size());
	}

}
