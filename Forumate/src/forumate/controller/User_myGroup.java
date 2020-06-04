package forumate.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import forumate.app.App;
import forumate.model.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class User_myGroup {
	@FXML private AnchorPane myGroup;
	@FXML private AnchorPane group;
	@FXML private AnchorPane home;
	@FXML private AnchorPane calendar;
	@FXML private AnchorPane facility;
	@FXML private ListView<String> listView;
	ArrayList<Group> groupList;
	@FXML
	public void initialize() {
		myGroup.setOnMouseClicked(e -> App.go("user_myGroup.fxml"));
		group.setOnMouseClicked(e -> App.go("user_group.fxml"));
		home.setOnMouseClicked(e -> App.go("user_main.fxml"));
		calendar.setOnMouseClicked(e -> App.go("user_calendar.fxml"));
		facility.setOnMouseClicked(e -> App.go("user_facility.fxml"));
		
//		groupList = (ArrayList<Group>) App.handle;
		groupList = new ArrayList<Group>();
		groupList.add(new Group("1224", "금오살", "111"));
		groupList.add(new Group("1234", "금오ㅇ살", "111"));
		groupList.add(new Group("1214", "금오ㅇ살", "111"));
		groupList.add(new Group("1234", "금오ㅍ살", "111"));
		groupList.add(new Group("1224", "금오ㅌㅋ살", "111"));
		groupList.add(new Group("1234", "금ㅋ오살", "111"));
		groupList.add(new Group("1234", "금오ㅌ살", "111"));
		groupList.add(new Group("1234", "금오ㅍ살", "111"));
		
		ObservableList<String> list = FXCollections.observableArrayList();
		for(Group group : groupList) {
			list.add(String.format("%-10s%s", group.getGroupId(),  group.getGroupName()));
		}
		listView.setItems(list);
	}
}
