package forumate.controller;

import forumate.app.App;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class User_calenderSetMonth{
	@FXML Text confirm, cancel;
	@FXML Spinner<Integer> inputYear, inputMonth;
	String regExp = "^[0-9]+$";

	@FXML
	public void initialize() {
		cancel.setOnMouseClicked(e -> ((Stage)((Node)(e.getSource())).getScene().getWindow()).close());
		confirm.setOnMouseClicked(e -> confirm(e));
	
		IntegerSpinnerValueFactory yRange = new IntegerSpinnerValueFactory(1, 3000);
		yRange.setValue(((int[])App.handle)[0]);
		inputYear.setValueFactory(yRange);
		
		IntegerSpinnerValueFactory mRange = new IntegerSpinnerValueFactory(1, 12);
		mRange.setValue(((int[])App.handle)[1]);
		inputMonth.setValueFactory(mRange);
	}
	
	private void confirm(Event e) {
		int[] arr = {inputYear.getValue(), inputMonth.getValue()};
		App.handle = arr;
		((Stage)((Node)(e.getSource())).getScene().getWindow()).close();
	}
}
