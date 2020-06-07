package forumate.model;

public class User {
	String userId;
	String userPw;
	String userName;
	String userContact;
	String type;
	
	public User(String userId, String userName, String userContact) {
		this(userId, "", userName, userContact, "유저");
	}
	public User(String userId, String userPw, String userName, String userContact, String type) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.userName = userName;
		this.userContact = userContact;
		this.type = type;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserContact() {
		return userContact;
	}
	public void setUserContact(String userContact) {
		this.userContact = userContact;
	}
	public String getType() {
		return type;
	}
	public void setTpye(String type) {
		this.type = type;
	}
}
