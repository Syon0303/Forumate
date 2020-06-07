package forumate.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import forumate.app.App;
import forumate.model.Group;
import forumate.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class User_myGroup {
	@FXML private AnchorPane myGroup;
	@FXML private AnchorPane group;
	@FXML private AnchorPane home;
	@FXML private AnchorPane calendar;
	@FXML private AnchorPane facility;
	@FXML private AnchorPane content;
	@FXML private ListView<String> listView;
	@FXML private Text createGroup;
	ArrayList<Group> groupList;
	int clickedIndex = -1;
	
	@FXML
	public void initialize() {
		myGroup.setOnMouseClicked(e -> App.go("user_myGroup.fxml"));
		group.setOnMouseClicked(e -> App.go("user_group.fxml"));
		home.setOnMouseClicked(e -> App.go("user_main.fxml"));
		calendar.setOnMouseClicked(e -> {
			User_myGroup_detail.curGroupIdName = "";
			App.go("user_calendar.fxml");
			});
		facility.setOnMouseClicked(e -> App.go("user_facility.fxml"));
		
//		groupList = (ArrayList<Group>) App.handle;
		groupList = new ArrayList<Group>();
		User manager = new User("1234", "족장님", "8228");
		ArrayList<User> member = new ArrayList<User>();
		member.add(manager);
		groupList.add(new Group("1224", "금오살", "111", "금오공대에서 살자구나", manager, member));
		groupList.add(new Group("1223", "금오대", "111", "금오공대에서 살자구나", manager, member));
		groupList.add(new Group("1222", "금오키", "111", "금오공대에서 살자구나", manager, member));
		groupList.add(new Group("1221", "금오오코", "111", "금오공대에서 살자구나", manager, member));
		groupList.add(new Group("1220", "금오하", "111", "금오공대에서 살자구나", manager, member));
		
		ObservableList<String> list = FXCollections.observableArrayList();
		for(Group group : groupList) {
			list.add(String.format("%-10s%s", group.getGroupId(),  group.getGroupName()));
		}
		listView.setItems(list);
		
		listView.setOnMouseClicked(e -> {
			int i = listView.getSelectionModel().getSelectedIndex();
			if(clickedIndex != i) {
				clickedIndex = i;
				return;
			}
			App.handle = groupList.get(listView.getSelectionModel().getSelectedIndex());
			content.getChildren().setAll(App.getContent("user_myGroup_detail.fxml"));
		});	
		
		createGroup.setOnMouseClicked(e -> {
			App.pop("user_group_add.fxml");
		});
	}
}
