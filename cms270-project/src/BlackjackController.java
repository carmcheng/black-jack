
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Pair;
public class BlackjackController extends BorderPane {
	private String text;
	private Player player;
	private Table table;
	private ArrayList<Player> players;
	
	private Label message;
	private Label leftLabel;
	private Label rightLabel;
	private Label centerLabel;
	private Label playerHandValueLabel;
	private Label dealerHandValueLabel;
	private Text topOutput;
	private Button start;
	private Button hit;
	private Button stand;
	private VBox center;
	private VBox left;
	private VBox right;
	private HBox top;
	private HBox bottom;
	
	private VBox handVBox;
	private VBox dealerHandVBox;

	public BlackjackController() {
		
		/*** Center pane ***/
		center = new VBox();
//		center.setStyle("-fx-background-color: DARKGREEN;");
		centerLabel = new Label("");
		handVBox = new VBox();
		playerHandValueLabel = new Label("");
		setCenter(center);
		center.getChildren().addAll(centerLabel, handVBox, playerHandValueLabel);

		/*** Left pane ***/
		left = new VBox();
		//	left.setStyle("-fx-background-color: DARKGREEN;");
		left.setPrefWidth(150);
		leftLabel = new Label("Player Information:");
		setLeft(left);
		left.getChildren().add(leftLabel);

		/*** Right pane ***/
		right = new VBox();
		//	right.setStyle("-fx-background-color: DARKGREEN;");
		right.setPrefWidth(150);
		rightLabel = new Label("The Dealer");
		dealerHandVBox = new VBox();
		dealerHandValueLabel = new Label("");
		setRight(right); 
		right.getChildren().addAll(rightLabel, dealerHandVBox, dealerHandValueLabel);

		/*** Top pane ***/
		top = new HBox();
		top.setStyle("-fx-background-color: WHITE;");
		top.setPrefHeight(20);
		topOutput = new Text("");
		setTop(top);
		top.getChildren().add(topOutput);

		/*** Bottom pane ***/
		bottom = new HBox();
		bottom.setStyle("-fx-background-color: WHITE;");
		bottom.setPrefHeight(20);;
		start = new Button("START");
		start.setOnAction(new EventHandler<ActionEvent> () {
			@Override public void handle(ActionEvent e) {
				doPlayerMove(e);
			}
		});
		hit = new Button("HIT");
		hit.setOnAction(new EventHandler<ActionEvent> () {
			@Override public void handle(ActionEvent e) {
				doPlayerMove(e);
			}
		});
		stand = new Button("STAND");
		stand.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				// doPlayerMove(e);
			}
		});
		bottom.getChildren().addAll(start, hit, stand);
		setBottom(bottom);
	}

	
	protected void doPlayerMove(ActionEvent event) {
		// Start the round
		if (event.getSource() == start) {
			text = "Round has started. First hands dealt.";
			topOutput.setText(text);
			table.firstDeal();
			start.setVisible(false);		// Hides start button until the round is over
			updateView();
		}
		if (event.getSource() == hit) {
			text = activePlayer().getName() + ", you chose to hit.";
			topOutput.setText(text);
			if (activePlayer().isBusted()) {
				text = "You busted!";
				topOutput.setText(text);
				if (table.hasNextPlayer()) {
					table.moveToNextPlayer();
				} else {
					doDealerMove();
				}
			}
			activeHand().addCard(cardDeck().dealCard());
			updateView();
		}
		
		if(event.getSource() == stand) {
			if (table.hasNextPlayer()) {
				table.moveToNextPlayer();
				text = "You chose to stand.\nIt is now " + activePlayer().getName()
						+ "'s turn.";
				topOutput.setText(text);
			} else {
				hit.setVisible(false);
				stand.setVisible(false);
				doDealerMove();
			}
			updateView();
		}
	}
	
	protected void doDealerMove() {
		text = "It is the dealer's turn.";
		topOutput.setText(text);
		return;
	}
	
	public void printPlayerNames() {
		for(int i = 0; i < players.size(); i++) {
			message = new Label(players.get(i).getName() + " " + players.get(i).getMoney());
			left.getChildren().add(message);
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

	// Adds players into game
	public void launchAskPlayerInfo(int numOfPlayers) {
		table = new Table();
		players = table.getPlayers();
		for (int i = 0; i < numOfPlayers; i++) {
			Dialog<Pair<String, String>> dialog = new Dialog<>();
			dialog.setTitle("Player Information Retrieval");
			dialog.setHeaderText("Enter your name and amount of money you have.");

			ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));

			TextField name = new TextField();
			name.setPromptText("Name");
			TextField money = new TextField();
			money.setPromptText("Money");

			grid.add(new Label("Name:"), 0, 0);
			grid.add(name, 1, 0);
			grid.add(new Label("Money:"), 0, 1);
			grid.add(money, 1, 1);

			dialog.getDialogPane().setContent(grid);

			Platform.runLater(() -> name.requestFocus());

			dialog.setResultConverter(dialogButton -> {
				if (dialogButton == okButtonType) {
					return new Pair<>(name.getText(), money.getText());
				}
				return null;
			});

			Optional<Pair<String,String>> result = dialog.showAndWait();

			result.ifPresent(playerInfo -> {
				player = new Player(playerInfo.getKey(), Double.parseDouble(playerInfo.getValue()));
				players.add(player);
			});
		}
		table.setCurrentPlayer();
		centerLabel.setText("Current player: " + activePlayer().getName());
	}
	
	//Update information in window
	protected void updateView() {
		updateHandView();
		updateDealerHandView();
	}
	
	//Update hand cards
	protected void updateHandView() {
		handVBox.getChildren().clear();
		for (Card c : activeHand().getCards()) {
			Label cardLabel = new Label(c.toString());
			handVBox.getChildren().add(cardLabel);
		}
		playerHandValueLabel.setText("\n" + 
				Integer.toString(activeHand().checkHandValue()));
	}
	
	protected void updateDealerHandView() {
		dealerHandVBox.getChildren().clear();
		for (Card c : dealerHand().getCards()) {
			Label cardLabel = new Label(c.toString());
			dealerHandVBox.getChildren().add(cardLabel);
		}
		dealerHandValueLabel.setText("\n" + 
				Integer.toString(dealerHand().checkHandValue()));
	}
	
	private Player activePlayer() {
		return table.getCurrentPlayer();
	}
	
	private Hand activeHand() {
		return activePlayer().getHand();
	}
	
	private Hand dealerHand() {
		return table.getDealer().getHand();
	}
	
	private Deck cardDeck() {
		return table.getDeck();
	}
}

//protected void playerMove(ActionEvent event) {
//
//if (event.getSource() == hit) { // testing if these two are aliases
//	message.setText("You hit");
//	
//} 
//
//if(event.getSource() == stand) {
//	message.setText("You stand");
//}
//}

//public class BlackjackController  {
//	@FXML private TextField textInput;
//	@FXML private Label bottomLabel;
//	@FXML private Button rightButton;
//	@FXML private Button okButton;
//	@FXML private Label playerName;
//	@FXML private Label playerMoney;
//	private ArrayList<Player> players;
//	private Player player;
//	
//	@FXML protected void getPlayerInfo() {
//		players = new ArrayList<Player>();
//		String name = textInput.getText();
//		playerName.setText(name);
//		okButton.setOnAction(new EventHandler<ActionEvent> () {
//			@Override public void handle(ActionEvent e) {
//				String m = textInput.getText();
//				double money = Double.parseDouble(m);
//				playerMoney.setText(m);
//				player = new Player(name, money);
//			}
//		});
//		players.add(player);
//	}
//	
//	@FXML protected void hit (ActionEvent event) {
//		String i = textInput.getText();
//		bottomLabel.setText(i);
//	}
//	@FXML protected void stand (ActionEvent event) {
//		String i = textInput.getText();
//		bottomLabel.setText(i);
//	}
//}
