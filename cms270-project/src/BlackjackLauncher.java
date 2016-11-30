import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
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


/** This is our gui class
 */
public class BlackjackLauncher extends Application{
	
	Label rootLabel;
	TextField rootTF;
	
	protected void launchConfirmDialog() {
		//confirmation dialog
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Ready To Play Dialog");
		alert.setHeaderText("Play!");
		alert.setContentText("Are you ready to start the game?");

		
		//ERROR CHECK LATER: does not prevent user from playing the game
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			rootLabel.setText("User is ready to play!");
		} else {
			rootLabel.setText("User DOESN'T want to start the game.");
		}
	}
	
	public int retrieveNumPlayers() {
		List<String> choices = new ArrayList<>();
		choices.add("1");
		choices.add("2");
		choices.add("3");
		choices.add("4");
		choices.add("5");
		choices.add("6");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("1", choices);
		dialog.setTitle("New Players");
		dialog.setHeaderText("Number of Players");
		dialog.setContentText("Choose how many players will play tonight:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			rootLabel.setText(result.get() + " players in this game\n");
			return Integer.parseInt(result.get());
		} else {
			launchErrorDialog("number of players");
			return 1; //default of 1 player
		}	
	}
	
	public void launchErrorDialog(String error) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText(error);
		alert.setContentText("Please enter a valid value for " + error);
		alert.showAndWait();
	}
	
	public static void main (String [] args) {
		Application.launch(args);
	}
	
	@Override
	
	public void start(Stage stage) {
		stage.setTitle("Prepare to lose all your money");
		BorderPane bp = new BlackjackController();
		rootLabel = new Label("Results of our dialog will go here");
		rootTF = new TextField();
		bp.getChildren().addAll(rootLabel, rootTF);
		
		Scene scene = new Scene(bp, 600, 400, Color.DARKGREEN);
		
		
	//	bp.setPadding(new Insets(10, 20, 10, 20));
		
		stage.setScene(scene);
		stage.show();
		
		
		launchConfirmDialog();
		
	}
	

}
