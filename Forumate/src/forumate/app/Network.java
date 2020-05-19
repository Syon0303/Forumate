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

	public static String serverURL = "219.254.24.146";
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	
	Network(){
		try {
			socket = new Socket("localhost", 7777);
			is = socket.getInputStream();
			os = socket.getOutputStream();
			System.out.println("서버 접속 중");
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	private void send(Protocol protocol) throws Exception {
		try {
			os.write(protocol.getPacket());
			System.out.println("서버에게 전송");
		} catch (Exception e) {
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
					System.out.println(totalReceived);
					readSize = is.read(buf, totalReceived, protocol.getBodyLength() - totalReceived);
					totalReceived += readSize;
					if (readSize == -1) {
						App.go("app/fail.fxml");
						throw new Exception("통신오류: 연결 끊어짐");
					}
				}
				protocol.setPacketBody(buf);
				if (protocol.getType() == Protocol.TYPE_ERROR) {
					throw new Exception("통신오류: 서버에서 오류 발생함");
				} else if (protocol.getType() == type) {
					return protocol;
				}
			} while (true); // 현재 필요한 응답이 아닐경우 무시하고 다음 응답을 대기
		} catch (IOException e) {
			throw new Exception("통신오류: 데이터 수신 실패함");
		}
	}
	
	// ## 로그인
	public int login(String id, String pw) throws Exception {
		String[] body = new String[2];
		body[0] = id;
		body[1] = pw;

		Protocol protocol = new Protocol();
		protocol.setType(Protocol.TYPE_LOGIN_REQ);
		protocol.setBody(body);
		
		send(protocol);
		protocol = recv(Protocol.TYPE_LOGIN_RES);
		// code: 0 로그인 성공
		// code: 1 아이디 없음
		// code: 2 비밀번호 틀림
		// code: 3 중복 로그인
		return protocol.getCode();
	}

	// ## 로그아웃
	public boolean logout() throws Exception {
		return true;
	}
}
