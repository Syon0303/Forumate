package forumate.app;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Network {

	private Socket socket;
	private InputStream is;
	private OutputStream os;
	
	Network(){
		try {
			socket = new Socket("219.254.24.146", 7777);
			is = socket.getInputStream();
			os = socket.getOutputStream();
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	private void send(Protocol protocol) throws Exception {
		try {
			//os.write(protocol.getPacket());
		} catch (Exception e) {
			App.go("app/fail.fxml");
		}
	}

	private Protocol recv(int type) throws Exception {
		return null;
		/*
		byte[] header = new byte[Protocol.LEN_HEADER];
		Protocol protocol = new Protocol();
		try {
			int totalReceived, readSize;
			do {
				totalReceived = 0;
				readSize = 0;
				is.read(header, 0, Protocol.LEN_HEADER);
				protocol.setPacketHeader(header);
				byte[] buf = new byte[protocol.getBodyLength()];
				while (totalReceived < protocol.getBodyLength()) {
					readSize = is.read(buf, totalReceived, protocol.getBodyLength() - totalReceived);
					totalReceived += readSize;
					if (readSize == -1) {
						App.go("app/fail.fxml");
						throw new Exception("��ſ���: ���� ������");
					}
				}
				protocol.setPacketBody(buf);
				if (protocol.getType() == Protocol.TYPE_ERROR) {
					throw new Exception("��ſ���: �������� ���� �߻���");
				} else if (protocol.getType() == type) {
					return protocol;
				}
			} while (true); // ���� �ʿ��� ������ �ƴҰ�� �����ϰ� ���� ������ ���
		} catch (IOException e) {
			App.go("app/fail.fxml");
			throw new Exception("��ſ���: ������ ���� ������");
		}
		 */
	}
	
	// ## �α���
	public boolean[] login(String id, String pw) throws Exception {
		String[] body = new String[2];
		body[0] = id;
		body[1] = pw;

		Protocol protocol = new Protocol();

		send(protocol); // ����
		// protocol = recv(Protocol.TYPE_LOGIN_RES); // ����

		
		boolean[] result = new boolean[2];
		result[0] = true;
		return result;
	}

	// ## �α׾ƿ�
	public boolean logout() throws Exception {
		return true;
	}
}
