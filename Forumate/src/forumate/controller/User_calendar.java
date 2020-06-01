package forumate.controller;

import java.net.URL;
import java.util.Calendar;
import java.util.Observable;
import java.util.ResourceBundle;

import forumate.app.App;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class User_calendar implements Initializable{
	@FXML private AnchorPane myGroup;
	@FXML private AnchorPane group;
	@FXML private AnchorPane home;
	@FXML private AnchorPane calendar;
	@FXML private AnchorPane facility;
	@FXML protected GridPane grid;
	@FXML protected Label curYear, curMonth;
	@FXML protected Pane nextMonth, preMonth;
	private MCalendar mcal;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		myGroup.setOnMouseClicked(e -> App.go("user_myGroup.fxml"));
		group.setOnMouseClicked(e -> App.go("user_group.fxml"));
		home.setOnMouseClicked(e -> App.go("user_main.fxml"));
		calendar.setOnMouseClicked(e -> App.go("user_calendar.fxml"));
		facility.setOnMouseClicked(e -> App.go("user_facility.fxml"));

		mcal = new MCalendar();
		curMonth.setOnMouseClicked(e -> {});
		curYear.setOnMouseClicked(e -> {});
		nextMonth.setOnMouseClicked(e -> mcal.moveMonth(1));
		preMonth.setOnMouseClicked(e -> mcal.moveMonth(-1));
	}
	
	public class MCalendar {
		Calendar cal = Calendar.getInstance();
		private int year, month;
		private int startDate, endDate;
		
		public MCalendar() {
			this.year = cal.get(Calendar.YEAR);
			// Calender.MONTH에선 0이 1월이다
			this.month = cal.get(Calendar.MONTH) + 1;
			update();
		}
		
		public void update() {
			curYear.setText(Integer.toString(year));
			String m = Integer.toString(month);
			curMonth.setText(m.length() < 2 ? "0" + m : m);
			
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month-1);
			
			// startDate = 1 이면 일요일부터 시작, 7이면 토요일부터 시작
			startDate = cal.get(Calendar.DAY_OF_WEEK);
			endDate = cal.getActualMaximum(Calendar.DATE);
			
			ObservableList<Node> childrens = grid.getChildren();
			
			
			for(int i = 0; i < childrens.size() - 7; i++) {
				VBox vbox = (VBox) childrens.get(i+7);
				Label label = (Label)vbox.getChildren().get(0);
				label.setText(i+1 < startDate || i+2-startDate > endDate ? "" : Integer.toString(i+2-startDate));
			}
		}
		public void set(int y, int m) {
			year = y;
			month = m;
			update();
		}
		
		public void moveMonth(int n) {
			month += n;
			if(month > 12) {
				month -= 12; year++;
			} else if (month < 1) {
				month += 12; year--;
			}
			update();
		}
	}
}
