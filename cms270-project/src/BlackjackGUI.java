import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class BlackjackGUI extends Application{
	
	Label rootLabel;
	TextField rootTF;
	HBox cards = new HBox(); //***
	
	public void launchConfirmDialog() {
		//confirmation dialog
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Ready To Play Dialog");
		alert.setHeaderText("Play!");
		alert.setContentText("Are you ready to start the game?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			rootLabel.setText("User is ready to play!");
		} else {
			rootLabel.setText("User DOESN'T want to start the game.");
		}
	}
	
	public static void main (String [] args) {
		Application.launch(args);
	}
	
	@Override
	
	public void start(Stage stage) {
		stage.setTitle("Blackjack Test");
		BorderPane bp = new BorderPane();
		rootLabel = new Label("Results of our dialog will go here");
		rootTF = new TextField();
		bp.getChildren().addAll(rootLabel, rootTF);
	//	bp.setPadding(new Insets(10, 20, 10, 20));
		
		//creating vbox for the center, left and right and  setting background as green 
		VBox center = new VBox();
		center.setStyle("-fx-background-color: DARKGREEN;");
		center.getChildren().add(cards);
		bp.setCenter(center);
		
		VBox left = new VBox();
		left.setStyle("fx-background-color: DARKGREEN;");
		left.setPrefWidth(150);
		bp.setLeft(left);
		
		VBox right = new VBox();
		right.setStyle("fx-background-color: DARKGREEN;");
		right.setPrefWidth(150);
		bp.setRight(right);
		
		HBox top = new HBox(); 
		top.setPrefHeight(30);
		bp.setTop(top);  
		
		Scene scene = new Scene(bp, 600, 400, Color.DARKGREEN);
		stage.setScene(scene);
		stage.show();
		
		launchConfirmDialog();
	}
	
	public void displayCurrentPlayer(Player player){
		int numCards = player.getHand().numOfCards();
		for(int i = 0; i < numCards; i++){
			VBox card = new VBox();
			card.setPrefSize(50, 100);
			cards.getChildren().add(card);
		}
	}

}
