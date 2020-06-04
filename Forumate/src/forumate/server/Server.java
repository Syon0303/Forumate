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
import forumate.model.Facility;

public class Server {
	private final static int PORT = 7778;
	private final static int MAX_USER = 50;
	public static boolean isAdminLogin = false;
	public static HashMap<String, Boolean> logined = new HashMap<String, Boolean>();

	public static void main(String args[]) throws IOException, UnknownHostException {
		try {
			ExecutorService pool = Executors.newFixedThreadPool(MAX_USER);
			ServerSocket theServer = new ServerSocket(PORT);
			
			//ServerSocket theServer = new ServerSocket();
			//theServer.bind(new InetSocketAddress("0.0.0.0", PORT));
			
            System.out.println("Wait Client...");
			while (true) {
				System.out.println("1");
				Socket connection = theServer.accept();
<<<<<<< HEAD
				System.out.println("2");
				Callable<Void> task = new Task(connection); // 클라이언트마다 쓰레드 하나와 연결한다
				System.out.println("3");
=======
				Callable<Void> task = new Task(connection); // �겢�씪�씠�뼵�듃留덈떎 �벐�젅�뱶 �븯�굹��� �뿰寃고븳�떎
>>>>>>> refs/heads/pyan
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
							System.out.println(userID + " �겢�씪�씠�뼵�듃媛� 醫낅즺�맖");
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
						
					case Protocol.TYPE_FACILITY_SEARCH_REQ:
						readFacility(protocol);
						break;
					case Protocol.TYPE_FACILITY_SEARCH_RES:
						break;
					case Protocol.TYPE_FACILITY_UPDATE_REQ:
						updateFacility(protocol);
						break;
					}
				}
			} catch (IOException e) { // �뿰寃� �삤瑜� 諛쒖깮�떆
				System.out.println(userID + " Client : Connection Error Occured");
				errorProcess(e);
				return null;
			} catch (SQLException e) { // DB �젒�냽 �삤瑜� 諛쒖깮�떆
				System.out.println(userID + " Client : DB Error Occured");
				errorProcess(e);
				return null;
			} catch (Exception e) { // �씪諛� �삤瑜� 諛쒖깮�떆
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
		// ----- �넻�떊肄붾뱶 �떆�옉!!

		// ## �넻�떊�솗�씤
		private void ping() throws IOException, SQLException, Exception {
			System.out.println(userID + " �겢�씪�씠�뼵�듃 : PING 硫붿떆吏�");
			Protocol sndData = new Protocol();
			sndData.setType(Protocol.TYPE_UNDEFINED);
			os.write(sndData.getPacket());
		}

		// ## �봽濡쒓렇�옩 醫낅즺
		private void exit() throws IOException, SQLException, Exception {
			System.out.println(userID + " �겢�씪�씠�뼵�듃 : 醫낅즺 硫붿떆吏�");
			if (this.isAdmin)
				Server.isAdminLogin = false;
			else if (Server.logined.containsKey(userID))
				Server.logined.remove(userID);

			is.close();
			os.close();
			socket.close();
			System.out.println(userID + " Client : Closed");
		}

		// ## 濡쒓렇�씤
		private void login(Protocol rcvData) throws IOException, SQLException, Exception { // 濡쒓렇�씤
			System.out.println(userID + " �겢�씪�씠�뼵�듃 : 濡쒓렇�씤 �슂泥�");
			String[] str = (String[]) rcvData.getBody();
			Mysql mysql = Mysql.getConnection();
			
			mysql.sql("SELECT * FROM `user` WHERE userID = '" + str[0] + "'");
	
			ResultSet rs = mysql.select();
			Protocol sndData = new Protocol(Protocol.TYPE_LOGIN_RES);
			sndData.setBody(null);

			sndData.setCode(0);
			if(!rs.next()) // �씪移섑븯�뒗 �븘�씠�뵒媛� �뾾�뒗 寃쎌슦
				sndData.setBody(1);
			else if(!rs.getString("userPW").equals(str[1])) // 鍮꾨��踰덊샇媛� �씪移섑븯吏� �븡��� 寃쎌슦
				sndData.setBody(2);
			else if(logined.containsKey(rs.getString("userID"))) // 以묐났 濡쒓렇�씤
				sndData.setBody(3);
			else {
				sndData.setCode(1);
				this.userID = rs.getString("userID");
				Server.logined.put(this.userID, true);
				this.isAdmin = rs.getString("type").equals("愿�由ъ옄");
				if (this.isAdmin)
					Server.isAdminLogin = true;
				sndData.setBody(isAdmin ? 1 : 2);
			}
			os.write(sndData.getPacket());
		}

		// ## 濡쒓렇�븘�썐
		private void logout(Protocol rcvData) throws IOException, SQLException, Exception {
			System.out.println(userID + " �겢�씪�씠�뼵�듃 : 濡쒓렇�븘�썐 �슂泥�");
			if (this.isAdmin)
				Server.isAdminLogin = false;
			if (Server.logined.containsKey(userID))
				Server.logined.remove(userID);
			this.userID = "";
			this.isAdmin = false;
			Protocol sndData = new Protocol(Protocol.TYPE_LOGOUT_RES, 1);
			os.write(sndData.getPacket());
		}

		// ## �꽌踰꾩떆媛� 議고쉶 
		private void queryServerTime(Protocol rcvData) throws IOException, SQLException, Exception {
			Protocol sndData = new Protocol(Protocol.TYPE_QUERY_SERVERTIME_RES, 1);
			sndData.setBody(LocalDateTime.now());
			os.write(sndData.getPacket());
		}
		
		// ## �솃�뵾�뱶 �깮�꽦
		private void homefeed(Protocol rcvData) {
			
		}
		
		private void updateFacility(Protocol rcvData) throws IOException, SQLException, Exception{
			
			try {
			Mysql mysql = Mysql.getConnection();
			
			mysql.sql("SELECT * FROM facility WHERE facilityId ='" + rcvData.getBody() + "'");
			ResultSet rs = mysql.select();
			Facility facility = new Facility();
			while(rs.next())
				{facility.setReservations(rs.getString("reservations"));}
			
			String reservations = facility.getReservations();
			int reservationsInt = Integer.parseInt(reservations);
			reservationsInt = reservationsInt + 1;
			String facilityId = (String) rcvData.getBody();
			
			mysql.sql("UPDATE facility SET reservations=? WHERE facilityId=?");
			mysql.set(1, Integer.toString(reservationsInt));
			mysql.set(2, facilityId);
			
			mysql.update();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		//## 시설 명 조회
		private void readFacility(Protocol rcvData) throws IOException, SQLException, Exception{
			System.out.println("시설 정보 조회 요청");
			Mysql mysql = Mysql.getConnection();
			
			//rcvData.getBody() 에는 search가 있음.
			mysql.sql("SELECT * FROM facility WHERE facilityName LIKE '%" + rcvData.getBody() + "%'");
			
			Protocol sndData = new Protocol(Protocol.TYPE_FACILITY_SEARCH_RES);
			sndData.setBody(null);
			
			ResultSet rs = mysql.select();
			ArrayList<Facility> arrayList = new ArrayList<Facility>();
			while(rs.next()) {
				Facility facility = new Facility();
				
				facility.setFacilityId(rs.getString("facilityId"));
				facility.setFacilityName(rs.getString("facilityName"));
				facility.setFacilityClassification(rs.getString("facilityClassification"));
				facility.setCloseDay(rs.getString("closeDay"));
				facility.setWeekdaySttm(rs.getString("weekdaySttm"));
				facility.setWeekdayEndtm(rs.getString("weekdayEndtm"));
				facility.setWeekendSttm(rs.getString("weekendSttm"));
				facility.setWeekendEndtm(rs.getString("weekendEndtm"));
				facility.setPaidUse(rs.getString("paidUse"));
				facility.setFee(rs.getString("fee"));
				facility.setCapacity(rs.getString("capacity"));
				facility.setAdditionalFacilityInfo(rs.getString("additionalFacilityInfo"));
				facility.setHowApply(rs.getString("howApply"));
				facility.setAddress(rs.getString("address"));
				facility.setOrganizationName(rs.getString("organizationName"));
				facility.setPhoneNumber(rs.getString("phoneNumber"));
				facility.setLatitude(rs.getDouble("latitude"));
				facility.setLongitude(rs.getDouble("longitude"));
				facility.setReservations(rs.getString("reservations"));
				
				arrayList.add(facility);
			}
			Collections.sort(arrayList);
			sndData.setBody(arrayList);
			os.write(sndData.getPacket());
		}
	}
}