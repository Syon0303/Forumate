package forumate.server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {
	private final static int PORT = 336;
	private final static int MAX_USER = 50;

	public static void main(String args[]) throws IOException {
		ExecutorService pool = Executors.newFixedThreadPool(MAX_USER);
		ServerSocket theServer = new ServerSocket(PORT);
		System.out.println("������ ���� ���Դϴ�");

		while (true) {
			Socket connection = theServer.accept();
			Callable<Void> task = new Task(connection); // Ŭ���̾�Ʈ���� ������ �ϳ��� �����Ѵ�
			pool.submit(task);
		}
	}

	private static class Task implements Callable<Void> {

		private Socket connection;
		private BufferedWriter sender;
		private BufferedReader reader;
		private OutputStream os;
		private InputStream is;
		private byte[] buf = new byte[20];

		Task(Socket connection) throws IOException {
			this.connection = connection;
			sender = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			os = connection.getOutputStream();
			is = connection.getInputStream();
		}

		public Void call() throws IOException {
			System.out.println("\tŬ���̾�Ʈ ����");
			while (true) {
				switch (reader.readLine()) {
				case "login":
					String id = reader.readLine();
					String pw = reader.readLine();
					sender.write(1);
					sender.flush();
					break;
				case "close":
					connection.close();
					System.out.println("\tŬ���̾�Ʈ ��������");
					return null;
				}
			}
		}
	}

	private static byte[] convertToBytes(Object object) throws IOException {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream(bos)) {
			out.writeObject(object);
			return bos.toByteArray();
		}
	}

	private static Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
		try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInput in = new ObjectInputStream(bis)) {
			return in.readObject();
		}
	}
}