package forumate.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Confirm {
	@FXML Label confirm, cancel;
	public static boolean ok = false;
	@FXML
	public void initialize() {
		ok = false;
		confirm.setOnMouseClicked(e -> {
			ok = true;
			((Stage)((Node)(e.getSource())).getScene().getWindow()).close();
		});
		cancel.setOnMouseClicked(e -> ((Stage)((Node)(e.getSource())).getScene().getWindow()).close());
	}
}
