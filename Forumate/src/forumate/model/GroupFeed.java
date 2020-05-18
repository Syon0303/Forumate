package forumate.model;

public class GroupFeed {
	String groupId;
	String userId;
	String feedId;
	Object feedContents;
	String createTime;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFeedId() {
		return feedId;
	}
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}
	public Object getFeedContents() {
		return feedContents;
	}
	public void setFeedContents(Object feedContents) {
		this.feedContents = feedContents;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
