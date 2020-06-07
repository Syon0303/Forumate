package forumate.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class GroupFeed {
	String feedId;
	String groupId;
	String userId;
	Object feedContents;
	LocalDate createDate;
	String text;
	int likeCnt;
	
	public GroupFeed(String feedId, String groupId, String userId, Object feedContents, LocalDate createDate,
			String text, int likeCnt) {
		super();
		this.feedId = feedId;
		this.groupId = groupId;
		this.userId = userId;
		this.feedContents = feedContents;
		this.createDate = createDate;
		this.text = text;
		this.likeCnt = likeCnt;
	}
	public String getFeedId() {
		return feedId;
	}
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}
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
	public Object getFeedContents() {
		return feedContents;
	}
	public void setFeedContents(Object feedContents) {
		this.feedContents = feedContents;
	}
	public LocalDate getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getLikeCnt() {
		return likeCnt;
	}
	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}
}
