package forumate.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import forumate.app.App;
import forumate.model.GroupFeed;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomeFeed {
	@FXML private Label groupName;
	@FXML private Label userName;
    @FXML private ImageView like;
	@FXML private Label likeCnt;
	@FXML private Label delete;
	@FXML private Label time;
    @FXML private ImageView image;
    @FXML private TextArea text;
    GroupFeed gf;
    boolean isLiked;
    Image likeImg, dolikeImg;
    
	@FXML
	public void initialize() {
		if(App.handle == null)
			return;
		gf = (GroupFeed) App.handle;
		groupName.setText(gf.getGroupId());

		time.setText(gf.getCreateDate().toString());
		
		userName.setText(gf.getUserId());
		text.setText(gf.getText());
		likeCnt.setText(toNumFormat(gf.getLikeCnt()));

		// 해당 글을 작성한 유저라면 해당 피드를 삭제 버튼을 활성화
		delete.setOnMouseClicked(e ->{
				// 해당 피드를 삭제
		});
		
		isLiked = false;
		
	    Image likeImg = new Image(new File("bin/forumate/img/heart.png").toURI().toString());
	    Image dolikeImg = new Image(new File("bin/forumate/img/heartRed.png").toURI().toString());
	    like.setImage(likeImg);

		like.setOnMouseClicked(e ->{
			gf.setLikeCnt(isLiked ? gf.getLikeCnt() - 1 : gf.getLikeCnt() + 1);
			likeCnt.setText(toNumFormat(gf.getLikeCnt()));
			like.setImage(isLiked ? likeImg : dolikeImg);
			isLiked = !isLiked;
			// 변경된 groupFeed 서버에게 전달
		});
	}
	
	 public static String toNumFormat(int num) {
		  DecimalFormat df = new DecimalFormat("#,###");
		  return df.format(num);
	}
}
