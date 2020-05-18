package forumate.controller;

import java.net.URL;
import java.util.ResourceBundle;

import animatefx.animation.FadeIn;
import forumate.app.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Login implements Initializable{
	
	@FXML private Pane root;
	@FXML private TextField id;
	@FXML private PasswordField pw;
	@FXML private Text login;
	@FXML private ImageView close;
	@FXML private Label state;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		login.setOnMouseClicked(e -> login());
		id.setOnAction(e -> login());
		pw.setOnAction(e -> login());
		close.setOnMouseClicked(e -> System.exit(0));
	}
    public void login(){
    	App.goFade("user_main.fxml");
    	/*
    	try {
			boolean res[] = App.network.login(id.getText(), pw.getText());
			if(res[0])
				App.goFade(App.path + (res[1] ? "main.fxml" : "S_main.fxml"));
			else
			{
				new FadeIn(root).setSpeed(2).play();
				state.setText(res[1] ? "접속 중인 IP가 있습니다" : "아이디와 비밀번호를 확인하세요");
			}
		} catch (Exception e) {
			App.go("app/fail.fxml");
		}
		*/
    }
}
