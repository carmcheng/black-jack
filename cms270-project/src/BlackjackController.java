
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;

public class BlackjackController extends BorderPane {
	private Label message;
	
	public BlackjackController() {
		super();
		VBox center = new VBox();
		center.setStyle("-fx-background-color: DARKGREEN;");
		setCenter(center);
		message = new Label("Center Pane");
		
		VBox left = new VBox();
		left.setStyle("-fx-background-color: DARKGREEN;");
		left.setPrefWidth(150);
		setLeft(left);
		
		VBox right = new VBox();
		right.setStyle("-fx-background-color: DARKGREEN;");
		right.setPrefWidth(150);
		setRight(right); 
		
		HBox top = new HBox();
		top.setStyle("-fx-background-color: PINK;");
		top.setPrefHeight(20);
		message = new Label("HELLO");
		top.getChildren().add(message);
		setTop(top);
	}
}
