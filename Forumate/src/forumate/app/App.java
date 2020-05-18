package forumate.app;

import java.io.IOException;

import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class App extends Application {

	// 임시 데이터를 저장하는 객체
	public static Network network;
	public static Object handle;
	public static String path = "/forumate/view/";

	@FXML public static Pane root;
	@FXML public static Stage stage;

	@Override
	public void start(Stage primaryStage) {
		App.stage = primaryStage;
		root = new Pane();
		mouseDrag(root, stage);
		go("login.fxml");
		
		stage.setScene(new Scene(root));
		stage.initStyle(StageStyle.UNDECORATED);
		stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
			if (KeyCode.ESCAPE == event.getCode())
				stage.close();
		});
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	// 화연 이동하는 메소드
	public static void goFade(String fxml) {
		go(fxml);
		new FadeIn(root).setSpeed(1.1).play();
	}

	public static void go(String fxml) {
		try {
			Parent scene = FXMLLoader.load(App.class.getResource(path + fxml));
			root.getChildren().removeAll();
			root.getChildren().setAll(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// 팝업 창을 띄우는 메소드
	public static void pop(String fxml) {
		try {
			Stage popStage = new Stage();
			
			Pane pop = new Pane();
			mouseDrag(pop, popStage);
			Parent scene = FXMLLoader.load(App.class.getResource(path + fxml));
			pop.getChildren().setAll(scene);
			popStage.setScene(new Scene(pop));
			popStage.initModality(Modality.APPLICATION_MODAL);
			popStage.initStyle(StageStyle.UNDECORATED);
			popStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
				if (KeyCode.ESCAPE == event.getCode())
					popStage.close();
			});
			popStage.showAndWait();
		} catch (IOException e) {
			System.err.println("pop 에러");
			e.printStackTrace();
		}
	}

	// Pane을 드레그해서 이동시키는 메소드
	private static double xOffset = 0, yOffset = 0;
	private static void mouseDrag(Pane root, Stage stage) {
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() - xOffset);
				stage.setY(event.getScreenY() - yOffset);
			}
		});
	}
}
