package forumate.controller;

import forumate.app.App;
import forumate.model.Event;
import forumate.model.Group;
import forumate.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class User_myGroup_detail {
	@FXML Label groupID;
	@FXML Label groupName;
	@FXML TextArea introduction;
	@FXML Label edit, deleteGroup;
	@FXML ListView<String> userListView;
	@FXML Label ban;
	@FXML ListView<String> applyListView;
	@FXML Label accept, reject;
	@FXML ImageView groupCalendar;
	@FXML Label addCalendar;
	Group group;
	// 일정 조회 화면으로 넘어가면서 일정조회 컨트롤러에서 이를 참조한다
	public static String curGroupIdName="";
	
	@FXML
	public void initialize() {
		if(App.handle == null)
			return;
		group = (Group) App.handle;
		curGroupIdName = group.getGroupId() + " " + group.getGroupName();
		
		groupID.setText(group.getGroupId());
		groupName.setText(group.getGroupName());
		introduction.setText(group.getIntroduction());
		edit.setOnMouseClicked(e -> {
			group.setIntroduction(introduction.getText());
			// DB에 등록
		});
		
		deleteGroup.setOnMouseClicked(e -> {
			App.pop("confirm.fxml");
			if(Confirm.ok) {
				// DB에서 해당 그룹을 삭제
			}
		});
		
//		// 맴버
//		ObservableList<String> userList = FXCollections.observableArrayList();
//		for(User user : group.getMember()) {
//			userList.add(String.format("%-10s%-10s%s", user.getUserId(), user.getUserName(), user.getUserContact()));
//		}
//		userList.set(0, "★    " + userList.get(0));
//		userListView.setItems(userList);
		
		ban.setOnMouseClicked(e -> {
			int i = userListView.getSelectionModel().getSelectedIndex();
			String banID = group.getMember().get(i).getUserId();
			App.pop("confirm.fxml");
			if(Confirm.ok) {
				// DB에서 해당 유저를 삭제한다
			}
		});
		
		// 신청 목록
//		ObservableList<String> applyList = FXCollections.observableArrayList();
//		for(User user : group.getMember()) {
//			applyList.add(String.format("%-10s%-10s%s", user.getUserId(), user.getUserName(), user.getUserContact()));
//		}
//		applyListView.setItems(applyList);
		accept.setOnMouseClicked(e -> apply(true));
		reject.setOnMouseClicked(e -> apply(false));
		
		groupCalendar.setOnMouseClicked(e -> {
			App.go("user_calendar.fxml");
		});
		addCalendar.setOnMouseClicked(e -> {
			App.handle = new Event(0, curGroupIdName=="" ? "curUserID" : curGroupIdName);
			App.pop("user_calendarEvent.fxml");
		});
	}
	private void apply(boolean confirm) {

	}
}
