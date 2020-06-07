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

public class Login{
	
	@FXML private Pane root;
	@FXML private TextField id;
	@FXML private PasswordField pw;
	@FXML private ImageView login;
	@FXML private ImageView close;
	@FXML private Label state;
	@FXML
	public void initialize() {
		login.setOnMouseClicked(e -> login());
		id.setOnAction(e -> login());
		pw.setOnAction(e -> login());
		close.setOnMouseClicked(e -> System.exit(0));
	}
    public void login(){
    	try {
			new FadeIn(root).setSpeed(10).play();
			int[] res = App.network.login(id.getText(), pw.getText());
			// code: 0  로그인 실패 	  body: 1: 아이디 없음  2: 비밀번호 틀림  3: 중복 로그인
			// code: 1  로그인 성공	  body: 1: 관리자  2: 사용자
			if(res[0] == 0) {
				switch(res[1]) {
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
			}
			else {
				App.go(res[1] == 1 ? "admin_main.fxml" : "user_main.fxml");
			}
		} catch (Exception e) {
			App.go("fail.fxml");
		}
    }
}