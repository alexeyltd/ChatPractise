package chat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public static final int DEFAULT_PORT = 10000;

	public static void main(String[] args) {

		System.out.println("start");

		try {
			ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Got connection " + socket);
				new Thread(() -> processConnection(socket)).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void processConnection(Socket socket) {

		try {
			Scanner scanner = new Scanner(socket.getInputStream());

			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				System.out.println(line);
				printWriter.println(line);
				printWriter.flush();
				if (line.equals("exit")) {
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
