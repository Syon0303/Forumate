package forumate.model;

public class User {
	String userId;
	String userPw;
	String userName;
	String userContact;
	String[] belongingGroup;
	String authority;
	
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
	public String[] getBelongingGroup() {
		return belongingGroup;
	}
	public void setBelongingGroup(String[] belongingGroup) {
		this.belongingGroup = belongingGroup;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
