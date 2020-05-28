package forumate.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Network {

	private Socket socket;
	private InputStream is;
	private OutputStream os;
	
	Network(){
		try {
			socket = new Socket("192.168.0.2", 7777);
			is = socket.getInputStream();
			os = socket.getOutputStream();
			System.out.println("���� ���� ��");
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	private void send(Protocol protocol) throws Exception {
		try {
			os.write(protocol.getPacket());
			System.out.println("�������� ����");
		} catch (Exception e) {
			App.go("fail.fxml");
		}
	}

	private Protocol recv(int type) throws Exception {
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
					System.out.println("���� ����Ʈ: " + totalReceived);
					readSize = is.read(buf, totalReceived, protocol.getBodyLength() - totalReceived);
					totalReceived += readSize;
					if (readSize == -1) {
						App.go("fail.fxml");
						throw new Exception("��ſ���: ���� ������");
					}
				}
				protocol.setPacketBody(buf);
				if (protocol.getType() == Protocol.TYPE_ERROR)
					throw new Exception("��ſ���: �������� ���� �߻���");
				else if (protocol.getType() == type)
					return protocol;
			} while (true); // ���� �ʿ��� ������ �ƴҰ�� �����ϰ� ���� ������ ���
		} catch (IOException e) {
			throw new Exception("��ſ���: ������ ���� ������");
		}
	}
	// ## ���α׷� ����
	public void exit() throws Exception {
		Protocol protocol = new Protocol();
		protocol.setType(Protocol.TYPE_EXIT);

		send(protocol); // ����
		System.exit(0);
	}
		
	// ## �α���
	public int[] login(String id, String pw) throws Exception {
		String[] body = new String[2];
		body[0] = id;
		body[1] = pw;

		Protocol protocol = new Protocol();
		protocol.setType(Protocol.TYPE_LOGIN_REQ);
		protocol.setBody(body);
		
		send(protocol);
		protocol = recv(Protocol.TYPE_LOGIN_RES);
		int[] res = new int[2];
		res[0] = protocol.getCode();
		res[1] = (int) protocol.getBody();
		// code: 0  �α��� ���� 	   1: ���̵� ����  2: ��й�ȣ Ʋ��  3: �ߺ� �α���
		// code: 1  �α��� ����	   1: ������  2: �����
		return res;
	}

	// ## �α׾ƿ�
	public boolean logout() throws Exception {
		Protocol protocol = new Protocol(Protocol.TYPE_LOGOUT_REQ);
		send(protocol);
		protocol = recv(Protocol.TYPE_LOGOUT_RES);
		return (protocol.getCode() == 1);
	}
}
