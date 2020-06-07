package forumate.model;

import java.util.ArrayList;

public class Group {
	String groupId;
	String groupName;
	String managerId;
	String introduction;
	User manager;
	ArrayList<User> member;
	
	public Group(String groupId, String groupName, String managerId, String introduction, User manager,
			ArrayList<User> member) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.managerId = managerId;
		this.introduction = introduction;
		this.manager = manager;
		this.member = member;
	}

	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public User getManager() {
		return manager;
	}
	public void setManager(User manager) {
		this.manager = manager;
	}
	public ArrayList<User> getMember() {
		return member;
	}
	public void setMember(ArrayList<User> member) {
		this.member = member;
	}
	
}
