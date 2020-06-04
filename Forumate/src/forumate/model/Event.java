package forumate.model;

import java.time.LocalDate;

public class Event {
	int eventId;
	String eventName;
	String groupName;
	String place;
	LocalDate startDate;
	LocalDate endDate;
	String eventContent;
	String color;
	
	public Event(int eventId) {
		super();
		this.eventId = eventId;
		groupName = "";
	}
	public Event(int eventId, String eventName, String groupName, String place, LocalDate startDate, LocalDate endDate,
			String eventContent, String color) {
		super();
		this.eventId = eventId;
		this.eventName = eventName;
		this.groupName = groupName;
		this.place = place;
		this.startDate = startDate;
		this.endDate = endDate;
		this.eventContent = eventContent;
		this.color = color;
	}
	public boolean isValid() {
		if(eventName.equals("") || place.equals("") || color == null || startDate == null || endDate == null)
			return false;
		return true;
	}
	
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getEventContent() {
		return eventContent;
	}
	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
