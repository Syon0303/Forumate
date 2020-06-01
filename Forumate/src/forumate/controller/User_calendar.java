package forumate.controller;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import forumate.app.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class User_calendar implements Initializable{
	@FXML private AnchorPane myGroup;
	@FXML private AnchorPane group;
	@FXML private AnchorPane home;
	@FXML private AnchorPane calendar;
	@FXML private AnchorPane facility;
	@FXML protected GridPane cal;
	@FXML protected Label curYear, curMonth;
	private static MCalendar mcal;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		myGroup.setOnMouseClicked(e -> App.go("user_myGroup.fxml"));
		group.setOnMouseClicked(e -> App.go("user_group.fxml"));
		home.setOnMouseClicked(e -> App.go("user_main.fxml"));
		calendar.setOnMouseClicked(e -> App.go("user_calendar.fxml"));
		facility.setOnMouseClicked(e -> App.go("user_facility.fxml"));
		curMonth.setOnMouseClicked(e -> {});
		curYear.setOnMouseClicked(e -> {});
		
		mcal = new MCalendar();
	}
	
	public class MCalendar {
		Calendar cal = Calendar.getInstance();
		private int year, month, date;
		private int startDate, endDate;
		
		public MCalendar() {
			this.year = cal.get(Calendar.YEAR);
			this.month = cal.get(Calendar.MONTH);
			startDate = cal.get(Calendar.DAY_OF_WEEK);
			endDate = cal.getActualMaximum(Calendar.DATE);
			
			curYear.setText(Integer.toString(year));
			curMonth.setText(Integer.toString(month));
			
			for(int i = 1; i <= Calendar.DATE; i++) {
				
			}
		}
		
		public void setYear() {
			
		}
	}
	
}
