package chat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;

public class Session {

	private PrintWriter printWriter;

	public void processConnection(Socket socket, Consumer<String> broadcaster,
								  Consumer<Session> sessionRemover) {

		try {
			Scanner scanner = new Scanner(socket.getInputStream());
			printWriter = new PrintWriter(socket.getOutputStream());
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				System.out.println(line);
				broadcaster.accept(line);
				if (line.equals("exit")) {
					break;
				}
			}
			sessionRemover.accept(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sendToClient(String line) {
		printWriter.println(" > " + line);
		printWriter.flush();
	}

}
