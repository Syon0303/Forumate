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
				Callable<Void> task = new Task(connection); // Ŭ���̾�Ʈ���� ������ �ϳ��� �����Ѵ�
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
							System.out.println(userID + " Ŭ���̾�Ʈ�� �����");
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
			} catch (IOException e) { // ���� ���� �߻���
				System.out.println(userID + " Client : Connection Error Occured");
				errorProcess(e);
				return null;
			} catch (SQLException e) { // DB ���� ���� �߻���
				System.out.println(userID + " Client : DB Error Occured");
				errorProcess(e);
				return null;
			} catch (Exception e) { // �Ϲ� ���� �߻���
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
		// ----- ����ڵ� ����!!

		// ## ���Ȯ��
		private void ping() throws IOException, SQLException, Exception {
			System.out.println(userID + " Ŭ���̾�Ʈ : PING �޽���");
			Protocol sndData = new Protocol();
			sndData.setType(Protocol.TYPE_UNDEFINED);
			os.write(sndData.getPacket());
		}

		// ## ���α׷� ����
		private void exit() throws IOException, SQLException, Exception {
			System.out.println(userID + " Ŭ���̾�Ʈ : ���� �޽���");
			if (this.isAdmin)
				Server.isAdminLogin = false;
			else if (Server.logined.containsKey(userID))
				Server.logined.remove(userID);

			is.close();
			os.close();
			socket.close();
			System.out.println(userID + " Client : Closed");
		}

		// ## �α���
		private void login(Protocol rcvData) throws IOException, SQLException, Exception { // �α���
			System.out.println(userID + " Ŭ���̾�Ʈ : �α��� ��û");
			String[] str = (String[]) rcvData.getBody();
			Mysql mysql = Mysql.getConnection();
			
			mysql.sql("SELECT * FROM `user` WHERE userID = '" + str[0] + "'");
	
			ResultSet rs = mysql.select();
			Protocol sndData = new Protocol(Protocol.TYPE_LOGIN_RES);
			sndData.setBody(null);

			sndData.setCode(0);
			if(!rs.next()) // ��ġ�ϴ� ���̵� ���� ���
				sndData.setBody(1);
			else if(!rs.getString("userPW").equals(str[1])) // ��й�ȣ�� ��ġ���� ���� ���
				sndData.setBody(2);
			else if(logined.containsKey(rs.getString("userID"))) // �ߺ� �α���
				sndData.setBody(3);
			else {
				sndData.setCode(1);
				this.userID = rs.getString("userID");
				Server.logined.put(this.userID, true);
				this.isAdmin = rs.getString("type").equals("������");
				if (this.isAdmin)
					Server.isAdminLogin = true;
				sndData.setBody(isAdmin ? 1 : 2);
			}
			os.write(sndData.getPacket());
		}

		// ## �α׾ƿ�
		private void logout(Protocol rcvData) throws IOException, SQLException, Exception {
			System.out.println(userID + " Ŭ���̾�Ʈ : �α׾ƿ� ��û");
			if (this.isAdmin)
				Server.isAdminLogin = false;
			if (Server.logined.containsKey(userID))
				Server.logined.remove(userID);
			this.userID = "";
			this.isAdmin = false;
			Protocol sndData = new Protocol(Protocol.TYPE_LOGOUT_RES, 1);
			os.write(sndData.getPacket());
		}

		// ## �����ð� ��ȸ 
		private void queryServerTime(Protocol rcvData) throws IOException, SQLException, Exception {
			Protocol sndData = new Protocol(Protocol.TYPE_QUERY_SERVERTIME_RES, 1);
			sndData.setBody(LocalDateTime.now());
			os.write(sndData.getPacket());
		}
		
		// ## Ȩ�ǵ� ����
		private void homefeed(Protocol rcvData) {
			
		}
	}
}