package forumate.app;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class Protocol {

	public static final int LEN_TYPE = 1;
	public static final int LEN_CODE = 1;
	public static final int LEN_BODYLENGTH = 4;
	public static final int LEN_HEADER = 6; // 6 Byte
	
	public static final int TYPE_ERROR = -1; // 오류 응답
	public static final int TYPE_UNDEFINED = 0; // 프로토콜이 지정되어 있지 않은 경우
	public static final int TYPE_EXIT = 1; // 프로그램 종료
	public static final int TYPE_LOGIN_REQ = 2; // 로그인 요청
	public static final int TYPE_LOGIN_RES = 3; // 로그인 응답
	public static final int TYPE_LOGOUT_REQ = 4; // 로그아웃 요청
	public static final int TYPE_LOGOUT_RES = 5; // 로그인 응답
	public static final int TYPE_QUERY_SERVERTIME_REQ = 8; // 서버시간 요청
	public static final int TYPE_QUERY_SERVERTIME_RES = 9; // 서버시간 응답
	
	public static final int TYPE_HOMEFEED_REQ = 11; // 홈피드 요청
	public static final int TYPE_HOMEFEED_RES = 12; // 홈피드 응답
	
	public static final int TYPE_MYGROUP_REQ = 21; // 내그룹 요청
	public static final int TYPE_MYGROUP_RES = 22; // 내그룹 응답

	public static final int TYPE_GROUP_SEARCH_REQ = 31; // 그룹검색 요청
	public static final int TYPE_GROUP_SEARCH_RES = 32; // 그룹검색 응답
	
	public static final int TYPE_CALENDAR_REQ = 41; // 일정 요청
	public static final int TYPE_CALENDAR_RES = 42; // 일정 응답

	public static final int TPYE_FACILITY_SEARCH_REQ = 51; // 공공시설 조회 요청
	public static final int TPYE_FACILITY_SEARCH_RES = 52; // 공공시설 조회 응답
	
	
	private byte type;
	private byte code;
	private int bodyLength;
	private byte[] body; // 직렬화 하여 저장
	
	public Protocol() {
		this(TYPE_UNDEFINED, 0);
	}
	public Protocol(int type) {
		this(type, 0);
	}
	public Protocol(int type, int code) {
		setType(type);
		setCode(code);
		setBodyLength(0);
	}

	public byte getType() {
		return type;
	}
	public void setType(int type) {
		this.type = (byte) type;
	}

	public byte getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = (byte) code;
	}

	public int getBodyLength() {
		return bodyLength;
	}
	private void setBodyLength(int bodyLength) { // Body Length는 직접 설정할 수 없음
		this.bodyLength = bodyLength;
	}
	
	public Object getBody() {
		return deserialize(body);
	}
	
	public void setBody(Object body) {
		byte[] serializedObject = serialize(body);
		this.body = serializedObject;
		setBodyLength(serializedObject.length);
	}

	public byte[] getPacket() { // 현재 header와 body로 패킷을 생성하여 리턴
		byte[] packet = new byte[LEN_HEADER + getBodyLength()];
		packet[0] = getType();
		packet[LEN_TYPE] = getCode();
		System.arraycopy(intToByte(getBodyLength()), 0, packet, LEN_TYPE + LEN_CODE, LEN_BODYLENGTH);
		if (getBodyLength() > 0) {
			System.arraycopy(body, 0, packet, LEN_HEADER, getBodyLength());
		}

		return packet;
	}

	public void setPacketHeader(byte[] packet) { // 매개 변수 packet을 통해 header만 생성
		byte[] data;

		setType(packet[0]);
		setCode(packet[LEN_TYPE]);

		data = new byte[LEN_BODYLENGTH];
		System.arraycopy(packet, LEN_TYPE + LEN_CODE, data, 0, LEN_BODYLENGTH);
		setBodyLength(byteToInt(data));
	}
	
	public void setPacketBody(byte[] packet) { // 매개 변수 packet을 통해 body를 생성
		byte[] data;

		if (getBodyLength() > 0) {
			data = new byte[getBodyLength()];
			System.arraycopy(packet, 0, data, 0, getBodyLength());
			setBody(deserialize(data));
		}
	}

	private byte[] serialize(Object b) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(b);
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Object deserialize(byte[] b) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(b);
			ObjectInputStream ois = new ObjectInputStream(bais);
			Object ob = ois.readObject();
			return ob;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	 private byte[] intToByte(int i) {
	 return ByteBuffer.allocate(Integer.SIZE / 8).putInt(i).array();
	 }

	 private int byteToInt(byte[] b) {
	 return ByteBuffer.wrap(b).getInt();
	}
}