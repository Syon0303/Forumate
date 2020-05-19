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
	@FXML private ImageView login;
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
		App.go("user_main.fxml");
    	/*
    	try {
			int res = App.network.login(id.getText(), pw.getText());
    		// code: 0 로그인 성공
    		// code: 1 아이디 없음
    		// code: 2 비밀번호 틀림
    		// code: 3 중복 로그인
			new FadeIn(root).setSpeed(2).play();
			switch(res) {
			case 0:
				break;
			case 1:
				state.setText("아이디를 확인하세요");
				break;
			case 2:
				state.setText("비밀번호를 확인하세요");
				break;
			case 3:
				state.setText("접속중인 아이디가 있습니다");
				break;
			}
		} catch (Exception e) {
			
		}*/
    }
}
