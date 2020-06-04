package forumate.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import forumate.app.App;
import forumate.model.Facility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.*;
import javafx.scene.image.ImageView;

public class User_facility{
	@FXML private AnchorPane myGroup;
	@FXML private AnchorPane group;
	@FXML private AnchorPane home;
	@FXML private AnchorPane calendar;
	@FXML private AnchorPane facility;
	@FXML protected ListView<String> listView;
	@FXML protected TextField search;
	@FXML protected ImageView btn_search;
	ArrayList<Facility> totalFacility;
	
	@FXML
	public void initialize() {
		myGroup.setOnMouseClicked(e -> App.go("user_myGroup.fxml"));
		group.setOnMouseClicked(e -> App.go("user_group.fxml"));
		home.setOnMouseClicked(e -> App.go("user_main.fxml"));
		calendar.setOnMouseClicked(e -> App.go("user_calendar.fxml"));
		facility.setOnMouseClicked(e -> App.go("user_facility.fxml"));
		
		search.setOnAction(e -> facility());
		btn_search.setOnMouseClicked(e -> facility());
		}
	
	public void facility() {
			String searchFacility = search.getText();
			try {
				totalFacility = App.network.facility(searchFacility);
				ObservableList<String> listItems = FXCollections.observableArrayList();
				
				for(int i=0; i<totalFacility.size(); i++)
					listItems.add(totalFacility.get(i).getFacilityName() + "(" + totalFacility.get(i).getFacilityClassification() + ")");
				listView.setItems(listItems);
				
				listView.setOnMouseClicked(e -> {
					App.handle = totalFacility.get(listView.getSelectionModel().getSelectedIndex());
					App.pop("user_facility_details.fxml");
				});			
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("�ü� ���� �ۼ��� ����");
			}
	}
}
