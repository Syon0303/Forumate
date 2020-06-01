package forumate.controller;

import forumate.app.App;
import forumate.model.Event;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class User_calendarEvent {
	@FXML Text text;
	@FXML
	public void initialize() {
		Event event = (Event) App.handle;
		text.setText(event.getEventName());
	}
}
