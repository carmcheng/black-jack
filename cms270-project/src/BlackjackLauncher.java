import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

/** This is our gui class.
 */
public class BlackjackLauncher extends Application{

	Label rootLabel;
	TextField rootTF;
//	private Player player;
//	private ArrayList<Player> players;
//	@FXML private Label playerName;
//	@FXML private Label playerMoney;
	
	public void start(Stage stage) {
		BlackjackController root = new BlackjackController();

		Scene scene = new Scene(root, 600, 400);

		stage.setScene(scene);
		stage.setTitle("Let's Play Blackjack!");
		stage.show();
		
		//launchConfirmDialog();
		int numPlayers = root.retrieveNumPlayers();
		root.launchAskPlayerInfo(numPlayers);
		root.printPlayerNames();
		
	}

	public static void main(String [] args) {
		launch(args);
	}
	
//
//	protected void launchConfirmDialog() {
//		//confirmation dialog
//		Alert alert = new Alert(AlertType.CONFIRMATION);
//		alert.setTitle("Ready To Play Dialog");
//		alert.setHeaderText("Play!");
//		alert.setContentText("Are you ready to start the game?");
//
//	}
//
//	public int retrieveNumPlayers() {
//		List<String> choices = new ArrayList<>();
//		choices.add("1");
//		choices.add("2");
//		choices.add("3");
//		choices.add("4");
//		choices.add("5");
//		choices.add("6");
//
//		ChoiceDialog<String> dialog = new ChoiceDialog<>("1", choices);
//		dialog.setTitle("New Players");
//		dialog.setHeaderText("Number of Players");
//		dialog.setContentText("Choose how many players will play tonight:");
//
//		// Traditional way to get the response value.
//		Optional<String> result = dialog.showAndWait();
//		if (result.isPresent()){
//			return Integer.parseInt(result.get());
//		} else {
//			launchErrorDialog("number of players");
//			return 1; //default of 1 player
//		}
//	}
//
//	public void launchAskPlayerInfo(int numOfPlayers) {
//		players = new ArrayList<Player>();
//		for (int i = 0; i < numOfPlayers; i++) {
//			Dialog<Pair<String, String>> dialog = new Dialog<>();
//			dialog.setTitle("Player Information Retrieval");
//			dialog.setHeaderText("Enter your name and amount of money you have.");
//
//			ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
//			dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
//
//			GridPane grid = new GridPane();
//			grid.setHgap(10);
//			grid.setVgap(10);
//			grid.setPadding(new Insets(20, 150, 10, 10));
//
//			TextField name = new TextField();
//			name.setPromptText("Name");
//			TextField money = new TextField();
//			money.setPromptText("Money");
//
//			grid.add(new Label("Name:"), 0, 0);
//			grid.add(name, 1, 0);
//			grid.add(new Label("Money:"), 0, 1);
//			grid.add(money, 1, 1);
//
//			dialog.getDialogPane().setContent(grid);
//
//			Platform.runLater(() -> name.requestFocus());
//
//			dialog.setResultConverter(dialogButton -> {
//				if (dialogButton == okButtonType) {
//					return new Pair<>(name.getText(), money.getText());
//				}
//				return null;
//			});
//
//			Optional<Pair<String,String>> result = dialog.showAndWait();
//
//			result.ifPresent(playerInfo -> {
//				player = new Player(playerInfo.getKey(), Double.parseDouble(playerInfo.getValue()));
//				players.add(player);
//			});
//			
//		}
//	}
//	
//
//	public void launchErrorDialog(String error) {
//		Alert alert = new Alert(AlertType.ERROR);
//		alert.setTitle("Error Dialog");
//		alert.setHeaderText(error);
//		alert.setContentText("Please enter a valid value for " + error);
//		alert.showAndWait();
//	}

//	public void start(Stage stage) {
//		try {
//			URL url = getClass().getResource("Blackjack_borderpane.fxml");
//			BorderPane root = FXMLLoader.load(url);
//
//			Scene scene = new Scene(root, 600, 400);
//
//			stage.setScene(scene);
//			stage.setTitle("Example FXML GUI");
//			stage.show();
//			int numOfPlayers = retrieveNumPlayers();
//			launchAskPlayerInfo(numOfPlayers);
//			for (int i = 0; i < players.size(); i++) {
//				System.out.println(players.get(i).getName());
//				
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	



}
