package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Hello {

	public static final int DEFAULT_PORT = 10000;

	public static void main(String[] args) {

		System.out.println("start");

		try {
			ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);
			Socket socket = serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
