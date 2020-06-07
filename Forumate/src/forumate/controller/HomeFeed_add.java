package forumate.controller;

import java.io.File;

import forumate.app.App;
import forumate.model.Group;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HomeFeed_add {    
	@FXML private Label groupName, userName;
	@FXML private ImageView image;
    @FXML private Label myFile;
    @FXML private TextArea text;
    @FXML private Label close, confirm;
    
    private FileChooser fileChooser;
    private File file;
    private FileChooser.ExtensionFilter fileExtension;
    
    @FXML 
    public void initialize() {
    	groupName.setText(((Group)App.handle).getGroupName());
    	
    	myFile.setOnMouseClicked(e->{
 
    		fileChooser = new FileChooser();
    		fileExtension = new FileChooser.ExtensionFilter("JPG", "*.jpg");
    		fileChooser.getExtensionFilters().add(fileExtension);
    		
    		file = fileChooser.showOpenDialog(App.stage);
    		if (file!=null) {
    			image.setImage(new Image(file.toURI().toString()));
    			myFile.setText(file.getAbsolutePath());
    		}
    	});
    	close.setOnMouseClicked(e -> close(e));
    	confirm.setOnMouseClicked(e -> {
    		
//			App.network.uploadFile(file);
    		close(e);
    	});
    }
    private void close(Event e) {
		((Stage) ((Node) (e.getSource())).getScene().getWindow()).close();
    }
}
