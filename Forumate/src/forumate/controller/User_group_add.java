package forumate.controller;

import forumate.model.Group;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class User_group_add {
    @FXML private TextField groupName;
    @FXML private TextArea introduction;
    @FXML private Label close;
    @FXML private Label confirm;
    @FXML
    public void initialize() {
    	close.setOnMouseClicked(e -> ((Stage) ((Node) (e.getSource())).getScene().getWindow()).close());
    	confirm.setOnMouseClicked(e ->{
    		
    		((Stage) ((Node) (e.getSource())).getScene().getWindow()).close();
    	});
    }
}
