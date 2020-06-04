package forumate.app;

import forumate.model.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;


public class Network {

	private Socket socket;
	private InputStream is;
	private OutputStream os;
	
	Network(){
		try {
<<<<<<< HEAD
		//	socket = new Socket("219.254.24.146", 7778);
			socket = new Socket("192.168.0.2", 7778);
=======
			socket = new Socket("219.254.24.146", 7778);
			
>>>>>>> refs/heads/pyan
			is = socket.getInputStream();
			os = socket.getOutputStream();
			System.out.println("서버 접속 완료");
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	private void send(Protocol protocol) throws Exception {
		try {
			os.write(protocol.getPacket());
			System.out.println("서버에게 전송");
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
					System.out.println("받은 바이트: " + totalReceived);
					readSize = is.read(buf, totalReceived, protocol.getBodyLength() - totalReceived);
					totalReceived += readSize;
					if (readSize == -1) {
						App.go("fail.fxml");
						throw new Exception("통신오류: 연결 끊어짐");
					}
				}
				protocol.setPacketBody(buf);
				if (protocol.getType() == Protocol.TYPE_ERROR)
					throw new Exception("통신오류: 서버에서 오류 발생함");
				else if (protocol.getType() == type)
					return protocol;
			} while (true); // 현재 필요한 응답이 아닐경우 무시하고 다음 응답을 대기
		} catch (IOException e) {
			throw new Exception("통신오류: 데이터 수신 실패함");
		}
	}
	// ## 프로그램 종료
	public void exit() throws Exception {
		Protocol protocol = new Protocol();
		protocol.setType(Protocol.TYPE_EXIT);

		send(protocol); // 전송
		System.exit(0);
	}
		
	// ## 로그인
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
		// code: 0  로그인 실패 	   1: 아이디 없음  2: 비밀번호 틀림  3: 중복 로그인
		// code: 1  로그인 성공	   1: 관리자  2: 사용자
		return res;
	}

	// ## 로그아웃
	public boolean logout() throws Exception {
		Protocol protocol = new Protocol(Protocol.TYPE_LOGOUT_REQ);
		send(protocol);
		protocol = recv(Protocol.TYPE_LOGOUT_RES);
		return (protocol.getCode() == 1);
	}
<<<<<<< HEAD
	
	
	//## �ü� ��ȸ ��û
	public ArrayList<Facility> facility(String search) throws Exception {
		Protocol protocol = new Protocol();
		protocol.setType(Protocol.TYPE_FACILITY_SEARCH_REQ);
		protocol.setBody(search);
		send(protocol);
		
		protocol = recv(Protocol.TYPE_FACILITY_SEARCH_RES);
		ArrayList<Facility> facility = (ArrayList<Facility>) protocol.getBody();
		return (ArrayList<Facility>) protocol.getBody();
	}
	
	//#�ü� Ŭ�� �� ������Ʈ ��û
	public void FacilityReservations(String facilityId) throws Exception{
		Protocol protocol = new Protocol();
		protocol.setType(Protocol.TYPE_FACILITY_UPDATE_REQ);
		protocol.setBody(facilityId);
		send(protocol);
	}
}
=======
}
>>>>>>> refs/heads/pyan
