package forumate.server;

import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

import forumate.app.Protocol;

public class Server {
	private final static int PORT = 7777;
	private final static int MAX_USER = 50;
	public static boolean isAdminLogin = false;
	public static HashMap<String, Boolean> logined = new HashMap<String, Boolean>();

	public static void main(String args[]) throws IOException {
		try {
			ExecutorService pool = Executors.newFixedThreadPool(MAX_USER);
			ServerSocket theServer = new ServerSocket(PORT);
			System.out.println("Wait Client...");
			while (true) {
				Socket connection = theServer.accept();
				Callable<Void> task = new Task(connection); // 클라이언트마다 쓰레드 하나와 연결한다
				pool.submit(task);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class Task implements Callable<Void> {

		private Socket socket;
		private OutputStream os;
		private InputStream is;
		private String userID = "";
		private Boolean isAdmin = false;

		Task(Socket connection) throws IOException {
			this.socket = connection;
			os = socket.getOutputStream();
			is = socket.getInputStream();
		}

		public Void call() throws IOException {
			byte[] header = new byte[Protocol.LEN_HEADER];
			Protocol protocol = new Protocol();
			System.out.println("Client Connected");
			try {
				int totalReceived, readSize;
				while (true) {
					totalReceived = 0;
					readSize = 0;
					is.read(header, 0, Protocol.LEN_HEADER);
					protocol.setPacketHeader(header);
					byte[] buf = new byte[protocol.getBodyLength()];
					while (totalReceived < protocol.getBodyLength()) {
						readSize = is.read(buf, totalReceived, protocol.getBodyLength() - totalReceived);
						totalReceived += readSize;
						System.out.println(userID + " : Data Received (" + totalReceived + "/" + protocol.getBodyLength() + ")");
						if (readSize == -1) {
							System.out.println(userID + " 클라이언트가 종료됨");
							return null;
						}
					}
					protocol.setPacketBody(buf);
					switch (protocol.getType()) {
					case Protocol.TYPE_UNDEFINED:
						ping();
						break;
					case Protocol.TYPE_EXIT:
						exit();
						return null;
					case Protocol.TYPE_LOGIN_REQ:
						login(protocol);
						break;
					case Protocol.TYPE_LOGOUT_REQ:
						logout(protocol);
						break;
					case Protocol.TYPE_HOMEFEED_REQ:
						break;
					case Protocol.TYPE_HOMEFEED_RES:
						break;
					case Protocol.TYPE_CALENDAR_REQ:
						break;
					case Protocol.TYPE_CALENDAR_RES:
						break;
					case Protocol.TYPE_MYGROUP_REQ:
						break;
					case Protocol.TYPE_MYGROUP_RES:
						break;
					case Protocol.TYPE_GROUP_SEARCH_REQ:
						break;
					case Protocol.TYPE_GROUP_SEARCH_RES:
						break;
					}
				}
			} catch (IOException e) { // 연결 오류 발생시
				System.out.println(userID + " Client : Connection Error Occured");
				errorProcess(e);
				return null;
			} catch (SQLException e) { // DB 접속 오류 발생시
				System.out.println(userID + " Client : DB Error Occured");
				errorProcess(e);
				return null;
			} catch (Exception e) { // 일반 오류 발생시
				System.out.println(userID + " Client : General Error Occured");
				errorProcess(e);
			} finally {
				if (this.isAdmin)
					Server.isAdminLogin = false;
				else if (Server.logined.containsKey(userID))
					Server.logined.remove(userID);
			}
			return null;
		}
		private void errorProcess(Exception e) {
			try {
			Mysql mysql = Mysql.getConnection();
			mysql.rollback();
			mysql.setAutoCommit(true);
			Protocol sndData = new Protocol();
			sndData.setType(Protocol.TYPE_ERROR);
			os.write(sndData.getPacket());
			is.close();
			os.close();
			socket.close();
			} catch (Exception ex){
				System.out.println(userID + " Client : Error Process Failed");
			}
		}
		// ----- 통신코드 시작!!

		// ## 통신확인
		private void ping() throws IOException, SQLException, Exception {
			System.out.println(userID + " 클라이언트 : PING 메시지");
			Protocol sndData = new Protocol();
			sndData.setType(Protocol.TYPE_UNDEFINED);
			os.write(sndData.getPacket());
		}

		// ## 프로그램 종료
		private void exit() throws IOException, SQLException, Exception {
			System.out.println(userID + " 클라이언트 : 종료 메시지");
			if (this.isAdmin)
				Server.isAdminLogin = false;
			else if (Server.logined.containsKey(userID))
				Server.logined.remove(userID);

			is.close();
			os.close();
			socket.close();
			System.out.println(userID + " Client : Closed");
		}

		// ## 로그인
		private void login(Protocol rcvData) throws IOException, SQLException, Exception { // 로그인
			System.out.println(userID + " 클라이언트 : 로그인 요청");
			String[] str = (String[]) rcvData.getBody();
			Mysql mysql = Mysql.getConnection();
			
			mysql.sql("SELECT * FROM `user` WHERE userID = '" + str[0] + "'");
	
			ResultSet rs = mysql.select();
			Protocol sndData = new Protocol(Protocol.TYPE_LOGIN_RES);
			sndData.setBody(null);

			sndData.setCode(0);
			if(!rs.next()) // 일치하는 아이디가 없는 경우
				sndData.setBody(1);
			else if(!rs.getString("userPW").equals(str[1])) // 비밀번호가 일치하지 않은 경우
				sndData.setBody(2);
			else if(logined.containsKey(rs.getString("userID"))) // 중복 로그인
				sndData.setBody(3);
			else {
				sndData.setCode(1);
				this.userID = rs.getString("userID");
				Server.logined.put(this.userID, true);
				this.isAdmin = rs.getString("type").equals("관리자");
				if (this.isAdmin)
					Server.isAdminLogin = true;
				sndData.setBody(isAdmin ? 1 : 2);
			}
			os.write(sndData.getPacket());
		}

		// ## 로그아웃
		private void logout(Protocol rcvData) throws IOException, SQLException, Exception {
			System.out.println(userID + " 클라이언트 : 로그아웃 요청");
			if (this.isAdmin)
				Server.isAdminLogin = false;
			if (Server.logined.containsKey(userID))
				Server.logined.remove(userID);
			this.userID = "";
			this.isAdmin = false;
			Protocol sndData = new Protocol(Protocol.TYPE_LOGOUT_RES, 1);
			os.write(sndData.getPacket());
		}

		// ## 서버시간 조회 
		private void queryServerTime(Protocol rcvData) throws IOException, SQLException, Exception {
			Protocol sndData = new Protocol(Protocol.TYPE_QUERY_SERVERTIME_RES, 1);
			sndData.setBody(LocalDateTime.now());
			os.write(sndData.getPacket());
		}
		
		// ## 홈피드 생성
		private void homefeed(Protocol rcvData) {
			
		}
	}
}