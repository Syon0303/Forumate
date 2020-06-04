package forumate.controller;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import forumate.app.App;
import forumate.model.GroupFeed;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class User_main{
	@FXML private AnchorPane myGroup;
	@FXML private AnchorPane group;
	@FXML private AnchorPane home;
	@FXML private AnchorPane calendar;
	@FXML private AnchorPane facility;
	@FXML private VBox vbox;
	ArrayList<GroupFeed> groupFeed;
	
	@FXML
	public void initialize() {
		myGroup.setOnMouseClicked(e -> App.go("user_myGroup.fxml"));
		group.setOnMouseClicked(e -> App.go("user_group.fxml"));
		home.setOnMouseClicked(e -> App.go("user_main.fxml"));
		calendar.setOnMouseClicked(e -> App.go("user_calendar.fxml"));
		facility.setOnMouseClicked(e -> App.go("user_facility.fxml"));
		
		// data
		groupFeed = new ArrayList<GroupFeed>();
		groupFeed.add(new GroupFeed("1234", "오늘도 삽질", "삽질러1", "", LocalTime.now(), "오늘 우리 아주 불타 올랐습니다.", 1));
		groupFeed.add(new GroupFeed("1234", "오늘도 삽질", "삽질러1", "", LocalTime.now(), "오늘 우리 아주 불타 올랐습니다.", 100));
		groupFeed.add(new GroupFeed("1234", "오늘도 삽질", "삽질러1", "", LocalTime.now(), "오늘 우리 아주 불타 올랐습니다.", 1000));
		groupFeed.add(new GroupFeed("1234", "오늘도 삽질", "삽질러1", "", LocalTime.now(), "오늘 우리 아주 불타 올랐습니다.", 22));
		
		for(GroupFeed gf : groupFeed) {
			App.handle = gf;
			Parent scene;
			try {
				scene = FXMLLoader.load(App.class.getResource(App.path + "homeFeed.fxml"));
				AnchorPane root = new AnchorPane();
				root.getChildren().setAll(scene);
				vbox.getChildren().add(root);
			} catch (IOException e1) {
				System.err.println("홈피드 추가 오류");
			}
		}
	}
}
