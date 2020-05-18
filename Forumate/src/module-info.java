module FXwork {
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.media;
	requires javafx.swing;
	requires javafx.web;
	requires javafx.swt;
	requires AnimateFX;
	requires java.logging;
	// 모든 패키지를 opens시키면 된다
	opens forumate.app;
	opens forumate.controller;
}