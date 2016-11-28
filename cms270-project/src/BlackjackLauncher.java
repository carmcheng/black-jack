
import java.net.URL;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BlackjackLauncher extends Application {
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
	public void start(Stage stage) {
		try {
			URL url = getClass().getResource("Blackjack_borderpane.fxml");
			BorderPane root = FXMLLoader.load(url);
			
			stage.setTitle("Blackjack Test");
			Scene scene = new Scene(root, 600, 400, Color.DARKGREEN);
//			rootLabel = new Label("Results of our dialog will go here");
//			
//			root.getChildren().addAll(rootLabel, rootTF);
			stage.setScene(scene);
			stage.show();
			launchConfirmDialog();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args) {
		launch(args);
	}
}
