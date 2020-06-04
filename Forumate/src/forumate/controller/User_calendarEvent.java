package forumate.controller;

import java.time.LocalDate;


import forumate.app.App;
import forumate.model.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class User_calendarEvent {
    @FXML private TextField eventName;
	@FXML private TextField groupName;
    @FXML private TextField place;
    @FXML private DatePicker start;
    @FXML private DatePicker end;
    @FXML private ColorPicker color;
    @FXML private TextArea content;
    @FXML private Label close;
    @FXML private Label state;
    @FXML private Label edit;
    @FXML private Label delete;
	
	@FXML 
	public void initialize() {
		Event event = (Event) App.handle;
		if(event.getEventId() != 0) {
			eventName.setText(event.getEventName());
			groupName.setText(event.getGroupName());
			place.setText(event.getPlace());
			start.setValue(event.getStartDate());
			end.setValue(event.getEndDate());
			color.setValue(Color.valueOf(event.getColor()));
			content.setText(event.getEventContent());
		}
		else {
			delete.setVisible(false);
			edit.setText("확인");
		}
		state.setText("");
		
		close.setOnMouseClicked(e -> close(e));
		edit.setOnMouseClicked(e -> {
			event.setEventName(eventName.getText());
			event.setGroupName(groupName.getText());
			event.setPlace(place.getText());
			event.setStartDate(start.getValue());
			event.setEndDate(end.getValue());
			event.setColor(color.getValue().toString());
			event.setEventContent(content.getText());
			if(!event.isValid())
				state.setText("입력을 검토해주세요");
			else {
				// 서버에게 요청 	
				// 해당 그룹 수정,삭제 권한이 없다면 빠꾸
				
				close(e);
			}
		});
		delete.setOnMouseClicked(e -> {
			// 권한이 없다면 빠꾸
			close(e);
		});
	}
	
	void close(MouseEvent e) {
		((Stage) ((Node) (e.getSource())).getScene().getWindow()).close();
	}
}
