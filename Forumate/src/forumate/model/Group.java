package forumate.model;

public class Group {
	String groupId;
	String groupName;
	String managerId;
	String[] memberId;
	
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
	public String[] getMemberId() {
		return memberId;
	}
	public void setMemberId(String[] memberId) {
		this.memberId = memberId;
	}
}
