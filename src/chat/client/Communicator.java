package chat.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;

public class Communicator {

	private static final int DEFAULT_PORT = 10000;
	private PrintWriter writer;

	public static void main(String[] args) {

		Communicator client = new Communicator();

		Scanner keyboardScanner = new Scanner(System.in);
		new Thread(() -> {
			while (keyboardScanner.hasNext()) {
				String line = keyboardScanner.nextLine();
				client.sendTextToServer(line);

			}
		}).start();

		client.init(System.out::println);
	}

	public void init(Consumer<String> consumer) {
		try {
			Socket socket = new Socket("localhost", DEFAULT_PORT);
			Scanner serverScanner = new Scanner(socket.getInputStream());
			writer = new PrintWriter(socket.getOutputStream());
			new Thread(() -> {
				while (serverScanner.hasNext()) {
					String line = serverScanner.nextLine();
					consumer.accept(line);
				}
			}).start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendTextToServer(String text) {
		writer.println(text);
		writer.flush();
	}
}
