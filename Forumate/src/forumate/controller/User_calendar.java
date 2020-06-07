package forumate.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import forumate.app.App;
import forumate.model.Event;
import forumate.model.User;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class User_calendar {
	@FXML private AnchorPane myGroup;
	@FXML private AnchorPane group;
	@FXML private AnchorPane home;
	@FXML private AnchorPane calendar;
	@FXML private AnchorPane facility;
	@FXML protected GridPane grid;
	@FXML protected Label curYear, curMonth;
	@FXML protected Pane nextMonth, preMonth;
	@FXML protected Text register;
	@FXML protected Text curGroup;
	private MCalendar mcal;
	String curGroupIdName;
	
	@FXML
	public void initialize() {
		myGroup.setOnMouseClicked(e -> App.go("user_myGroup.fxml"));
		group.setOnMouseClicked(e -> App.go("user_group.fxml"));
		home.setOnMouseClicked(e -> App.go("user_main.fxml"));
		calendar.setOnMouseClicked(e -> App.go("user_calendar.fxml"));
		facility.setOnMouseClicked(e -> App.go("user_facility.fxml"));

		curGroupIdName = User_myGroup_detail.curGroupIdName;
		curGroup.setText(curGroupIdName);
		mcal = new MCalendar();
		curMonth.setOnMouseClicked(e -> mcal.set());
		curYear.setOnMouseClicked(e -> mcal.set());
		nextMonth.setOnMouseClicked(e -> mcal.moveMonth(1));
		preMonth.setOnMouseClicked(e -> mcal.moveMonth(-1));
		register.setOnMouseClicked(e ->{
			App.handle = new Event(0);
			App.pop("user_calendarEvent.fxml");
		});
	}
	
	public class MCalendar {
		Calendar cal = Calendar.getInstance();
		private int year, month;
		private int startDate, endDate;
		
		public MCalendar() {
			this.year = cal.get(Calendar.YEAR);
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

			if(curGroupIdName != "") {
				// DB에서 그룹명에 해당되는 일정들을 가져와야 한다.
			}
			//test
			ArrayList<Event> arr = new ArrayList<>();
	        String string = "2020-06-10";
	        LocalDate date = LocalDate.parse(string, DateTimeFormatter.ISO_DATE);
	        string = "2020-06-11";
	        LocalDate date2 = LocalDate.parse(string, DateTimeFormatter.ISO_DATE);
			arr.add(new Event(1151, "test", "금오", "D331", date, date2, "창프 쁘수기", "#acacac"));
	        string = "2020-06-11";
	        date = LocalDate.parse(string, DateTimeFormatter.ISO_DATE);
	        string = "2020-06-13";
	        date2 = LocalDate.parse(string, DateTimeFormatter.ISO_DATE);
			arr.add(new Event(1151, "test", "금오", "D331", date, date2, "창프 쁘수기", "#acacac"));
	        string = "2020-06-12";
	        date = LocalDate.parse(string, DateTimeFormatter.ISO_DATE);
	        string = "2020-06-15";
	        date2 = LocalDate.parse(string, DateTimeFormatter.ISO_DATE);
			arr.add(new Event(1151, "test", "금오", "D331", date, date2, "창프 쁘수기", "#acacac"));
			
			//add event
			for(Event event : arr) {
				// 년도 월 까지 검사해야 한다
				if(event.getStartDate().isAfter(LocalDate.of(year, month, endDate)) 
						|| event.getEndDate().isBefore(LocalDate.of(year, month, 1)))
					continue;
				int sD = event.getStartDate().getMonthValue() < month ? 1 : event.getStartDate().getDayOfMonth();
				int eD = event.getEndDate().getMonthValue() > month ? endDate : event.getEndDate().getDayOfMonth();
				
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
					// banner 클릭시 상세 정보 밑 수정할 수 있다
					banner.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent mv) {
							App.handle = event;
							App.pop("user_calendarEvent.fxml");
							App.go("user_calendar.fxml");
						}
					});
					banner.setAlignment(Pos.CENTER);
					banner.setStyle("-fx-background-color: " + event.getColor());
					// 첫날에만 일정명을 적어준다
					banner.getChildren().add(new Label(i == sD ? event.getEventName() : " "));
					
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
