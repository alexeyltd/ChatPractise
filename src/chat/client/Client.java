package chat.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static final int DEFAULT_PORT = 10000;

	public static void main(String[] args) {

		try {
			Socket socket = new Socket("localhost", DEFAULT_PORT);
			Scanner serverScanner = new Scanner(socket.getInputStream());
			Scanner keyboardScanner = new Scanner(System.in);
			PrintWriter writer = new PrintWriter(socket.getOutputStream());

			new Thread(() -> {
				while (keyboardScanner.hasNext()) {
					String line = keyboardScanner.nextLine();
					writer.println(line);
					writer.flush();
				}
			}).start();

			while (serverScanner.hasNext()) {
				String line = serverScanner.nextLine();
				System.out.println(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
