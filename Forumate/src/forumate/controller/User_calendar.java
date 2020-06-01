package forumate.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import forumate.app.App;
import forumate.model.Event;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class User_calendar {
	@FXML private AnchorPane myGroup;
	@FXML private AnchorPane group;
	@FXML private AnchorPane home;
	@FXML private AnchorPane calendar;
	@FXML private AnchorPane facility;
	@FXML protected GridPane grid;
	@FXML protected Label curYear, curMonth;
	@FXML protected Pane nextMonth, preMonth;
	private MCalendar mcal;
	
	@FXML
	public void initialize() {
		myGroup.setOnMouseClicked(e -> App.go("user_myGroup.fxml"));
		group.setOnMouseClicked(e -> App.go("user_group.fxml"));
		home.setOnMouseClicked(e -> App.go("user_main.fxml"));
		calendar.setOnMouseClicked(e -> App.go("user_calendar.fxml"));
		facility.setOnMouseClicked(e -> App.go("user_facility.fxml"));

		mcal = new MCalendar();
		curMonth.setOnMouseClicked(e -> mcal.set());
		curYear.setOnMouseClicked(e -> mcal.set());
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
			
			//calendar setting
			ObservableList<Node> childrens = grid.getChildren();
			for(int i = 0; i < childrens.size() - 7; i++) {
				VBox vbox = (VBox) childrens.get(i+7);
				vbox.getChildren().clear();
				
				int date = i+2-startDate;
				if(0 < date && date <= endDate) {
					Label label = new Label(Integer.toString(date));
					label.setPadding(new Insets(5, 0, 0, 0));
					vbox.getChildren().add(label);
				}
			}

			//test
			ArrayList<Event> arr = new ArrayList<>();
			arr.add(new Event("test", "2", "3"));
			arr.add(new Event("tesaaaaaaat2", "3", "8"));
			arr.add(new Event("test3", "5", "6"));
			
			//add event
			for(Event e : arr) {
				// 년도 월 까지 검사해야 한다
				int sD = Integer.parseInt(e.getStartDate());
				int eD = Integer.parseInt(e.getEndDate());
				
				int index = sD + 5 + startDate;
				VBox vbox = (VBox) childrens.get(index);
				int eLevel = vbox.getChildren().size();
				// 밑 층에 추가 할 수 있을 때
				for(int i = 1; i < vbox.getChildren().size(); i++) {
					HBox hbox = (HBox) vbox.getChildren().get(i);
					if(((Label)(hbox).getChildren().get(0)).getText().equals("")) {
						eLevel = i;
						break;
					}
				}
				
				for(int i = sD; i <= eD; i++) {
					vbox = (VBox) childrens.get(index++);
					HBox banner = new HBox();
					banner.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent mv) {
							App.handle = e;
							App.pop("user_calendarEvent.fxml");
						}
					});
					banner.setAlignment(Pos.CENTER);
					// Event에 칼러까지 추가
					banner.setStyle("-fx-background-color: #acacac");
					banner.getChildren().add(new Label(i == sD ? e.getEventName() : " "));
					// banner 클릭시 상세 정보를 볼 수 있어야 한다
					
					if(eLevel < vbox.getChildren().size()) {
						HBox hbox = (HBox) vbox.getChildren().get(eLevel);
						hbox.setAlignment(Pos.CENTER);
						hbox.setStyle(banner.getStyle());
						hbox.getChildren().clear();
						hbox.getChildren().add(banner);
					}
					else {
						while(vbox.getChildren().size() < eLevel) 
							vbox.getChildren().add(new HBox(new Label("")));
						vbox.getChildren().add(banner);
					}
				}
			}
		}
		
		public void set() {
			int[] arr = {year, month};
			App.handle = arr;
			App.pop("user_calendarSetMonth.fxml");
			arr = (int[]) App.handle;
			year = arr[0];
			month = arr[1];
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
