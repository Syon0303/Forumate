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
    	try {
			//new FadeIn(root).setSpeed(10).play();
			int[] res = App.network.login(id.getText(), pw.getText());
			// code: 0  �α��� ���� 	  body: 1: ���̵� ����  2: ��й�ȣ Ʋ��  3: �ߺ� �α���
			// code: 1  �α��� ����	  body: 1: ������  2: �����
			if(res[0] == 0) {
				switch(res[1]) {
				case 1:
					state.setText("���̵� Ȯ���ϼ���");
					break;
				case 2:
					state.setText("��й�ȣ�� Ȯ���ϼ���");
					break;
				case 3:
					state.setText("�������� ���̵� �ֽ��ϴ�");
					break;
				}
			}
			else
				App.go(res[1] == 1 ? "admin_main.fxml" : "user_main.fxml");
		} catch (Exception e) {
			App.go("fail.fxml");
		}
    }
}
