package forumate.model;

public class Group {
	String groupId;
	String groupName;
<<<<<<< HEAD
=======
	String managerId;
	String introduction;
>>>>>>> refs/heads/pyan
	
	public Group(String groupId, String groupName, String managerId) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.managerId = managerId;
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
<<<<<<< HEAD
=======
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
>>>>>>> refs/heads/pyan
}
