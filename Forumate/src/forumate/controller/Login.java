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
    		// code: 0 �α��� ����
    		// code: 1 ���̵� ����
    		// code: 2 ��й�ȣ Ʋ��
    		// code: 3 �ߺ� �α���
			new FadeIn(root).setSpeed(2).play();
			switch(res) {
			case 0:
				break;
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
		} catch (Exception e) {
			
		}*/
    }
}
