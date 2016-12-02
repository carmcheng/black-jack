
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

	private Label leftLabel;
	private Label rightLabel;
	private Label centerLabel;
	private Label playerHandValueLabel;
	private Label cardLabel;
	private Label dealerHandValueLabel;
	private Text topOutput;
	private Button start;
	private Button hit;
	private Button stand;
	private Button ok;
	private Button doubleDown;
	private VBox center;
	private VBox playerVBox;
	private VBox dealerPane;
	private HBox top;
	private HBox bottom;

	private VBox card;
	private VBox handVBox;
	private VBox dealerHandVBox;

	public BlackjackController() {

		/*** Center pane ***/
		center = new VBox();
		center.setStyle("-fx-background-color: DARKGREEN;");
		centerLabel = new Label("");
		handVBox = new VBox();
		//handVBox.setAlignment(Pos.BOTTOM_LEFT);
		playerHandValueLabel = new Label("");
		setCenter(center);
		center.getChildren().addAll(centerLabel, handVBox, playerHandValueLabel);

		/*** Left pane ***/
		playerVBox = new VBox();
		playerVBox.setStyle("-fx-background-color: DARKGREEN;");
		playerVBox.setPrefWidth(150);
		leftLabel = new Label("Player Information:");
		leftLabel.setStyle("-fx-text-fill: WHITE");
		setLeft(playerVBox);
		playerVBox.getChildren().add(leftLabel);

		/*** Right pane ***/
		dealerPane = new VBox();
		dealerPane.setStyle("-fx-background-color: DARKGREEN;");
		dealerPane.setPrefWidth(150);
		rightLabel = new Label("The Dealer");
		rightLabel.setStyle("-fx-text-fill: WHITE");
		dealerHandVBox = new VBox();
		dealerHandValueLabel = new Label("");
		setRight(dealerPane); 
		dealerPane.getChildren().addAll(rightLabel, dealerHandVBox, dealerHandValueLabel);

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
		hit.setVisible(false);
		hit.setOnAction(new EventHandler<ActionEvent> () {
			@Override public void handle(ActionEvent e) {
				doPlayerMove(e);
			}
		});
		stand = new Button("STAND");
		stand.setVisible(false);
		stand.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				doPlayerMove(e);
			}
		});
		doubleDown = new Button("DOUBLE");
		doubleDown.setVisible(false);
		doubleDown.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				doPlayerMove(e);
			}
		});
		ok = new Button("OK");
		ok.setVisible(false);
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				doPlayerMove(e);
			}
		});
		bottom.getChildren().addAll(start, hit, stand, doubleDown, ok);
		setBottom(bottom);
	}


	protected void doPlayerMove(ActionEvent event) {
		// Start the round
		if (event.getSource() == start) {
			text = "Round has started. First hands dealt.";
			topOutput.setText(text);
			table.firstDeal();
			Iterator cardIterator=table.getCurrentPlayer().getHand().createIterator();
			while(cardIterator.hasNext()){
				Card currentCard=(Card) cardIterator.next();
				cardLabel=new Label(currentCard.getCardSuit()+currentCard.getCardValue());
				card=new VBox(5);
				card.getChildren().add(cardLabel);
				cardLabel.setAlignment(Pos.TOP_LEFT);
				card.setPrefSize(10,30);
				card.setStyle("-fx-background-color: WHITE;");
				center.getChildren().add(card);
				card.setAlignment(Pos.BOTTOM_CENTER);
			}
			//			for(int i=0; i<table.getCurrentPlayer().getHand().numOfCards();i++){
			//				cardLabel=new Label();
			//				card=new VBox();
			//				handVBox.getChildren().add(card);
			//			}
			start.setVisible(false);
			hit.setVisible(true);
			stand.setVisible(true);
			doubleDown.setVisible(true);
			updateView();
		}

		if (event.getSource() == hit) {
			text = activePlayer().getName() + ", you chose to hit.";
			topOutput.setText(text);
			doubleDown.setVisible(false);
			activeHand().addCard(deck().dealCard());
			activePlayer().aceChanger();
			if (activePlayer().isBusted()) {
				text = "You busted!";
				topOutput.setText(text);
				setButtonsVisibility();
			}
			updateView();
		}

		if(event.getSource() == stand) {
			if (table.hasNextPlayer()) {
				table.moveToNextPlayer();
				centerLabel.setText("Current player: " + activePlayer().getName());
				text = "You chose to stand.\nIt is now " + activePlayer().getName()
						+ "'s turn.";
				topOutput.setText(text);
			} else {
				text = "You chose to stand. It is now the dealer's turn.";
				topOutput.setText(text);
				setButtonsVisibility();    // hides stand and hit, shows OK
			}
			updateView();
		}
		if (event.getSource() == doubleDown) {
			text = activePlayer().getName() + ", you chose to double down.";
			topOutput.setText(text);
			Card c = deck().dealCard();
			activePlayer().doubleDown();       // BET DECREASES BECAUSE OF DOUBLE DOWN
			checkForAceCard(c);
			activeHand().addCard(c);
			updateView();
			setButtonsVisibility();
			doubleDown.setVisible(false);
			if (table.hasNextPlayer()) {
				table.moveToNextPlayer();
				centerLabel.setText("Current player: " + activePlayer().getName());
				text = "You chose to stand.\nIt is now " + activePlayer().getName()
						+ "'s turn.";
				topOutput.setText(text);
			}
		}

		if(event.getSource() == ok) {
			if (table.hasNextPlayer()) {
				table.moveToNextPlayer();
				text += "\nIt is now " + activePlayer().getName() + "'s turn.";
				updateView();
				hit.setVisible(true);
				stand.setVisible(true);
				ok.setVisible(false);
				doubleDown.setVisible(true);
				centerLabel.setText("Current player: " + activePlayer().getName());
				topOutput.setText(text);
			} else {
				doDealerMove();
			}
		}
	}

	private void checkForAceCard(Card c) {
		if (c.getCardName().equals("A")) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Ace Card Found!");
			alert.setHeaderText("You received an Ace card.");
			alert.setContentText("Would you like to change its value "
					+ "from 11 to 1? Click OK to confirm, cancel to leave "
					+ "card value unchanged.");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				c.setCardValue(1);
			} else {
				return;
			}
		}
	}

	protected void doDealerMove() {
		text = "It is the dealer's turn.";
		topOutput.setText(text);

		while(dealer().checkSoftSeventeen() || dealerHand().checkHandValue()<17){
			dealerHand().addCard(deck().dealCard());
			dealer().aceChecker();
		}

		if (dealer().isBusted()) {
			text = "The dealer busted!";
			topOutput.setText(text);
		}

		updateDealerHandView();
		calculateResults();
		newRound();
	}

	protected void calculateResults() {
		Alert results = new Alert(AlertType.INFORMATION);
		results.setTitle("Results");
		results.setHeaderText(null);
		String text = "";
		for (Player p : table.getPlayers()) {
			if (dealer().isBusted()) {
				text = "The dealer busted! ";
				if (!p.isBusted()) {
					text += p.getName() + ", you win!";
				} else {
					text += p.getName() + ", you busted as well."
							+ " You do not win or lose money.";
				}
			} else if(dealerHand().checkBlackjack()) {
				text = "Dealer got Blackjack! ";
				if (!p.getHand().checkBlackjack()) {
					text += p.getName() + ", you lose!";
				} else {
					text += p.getName() + ", you got Blackjack as well!"
							+ "You do not win or lose money.";
				}
			} else {
				if (p.isBusted()) {
					text = p.getName() + ", you busted! You lose!";
				} else if (p.getHand().checkHandValue() == dealerHand().checkHandValue()) {
					text = p.getName() + ", you have the same hand value"
							+ " as the dealer. You do not win or lose money.";
				} else if (p.getHand().checkHandValue() > dealerHand().checkHandValue()) {
					text = p.getName() + ", you have a larger hand value than"
							+ " the dealer. You win!";
				} else {
					text = p.getName() + ", you have a smaller hand value than"
							+ " the dealer. You lose!";
				}
			}
			results.setContentText(text);
			results.showAndWait();
		}
		return;
	}

	protected void newRound() {
		ArrayList <Player> toRemove = new ArrayList<Player>();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Reset");

		for (Player p : table.getPlayers()) {
			alert.setHeaderText(p.getName() + ", would you"
					+ " like to stay for another round?");
			alert.setContentText("You currently have $" + p.getMoney()
			+ " left.");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				p.getHand().reset();
			} else {
				toRemove.add(p);
			}
		}

		for (Player p : toRemove) {
			players.remove(p);
		}
		table.restart();
		reset();
		if (table.getPlayers().size() == 0) {
			launchThanksForPlaying();
			System.exit(0);
		}
	}

	protected void fillPlayerVBox() {
		for (Player p : table.getPlayers()) {
			String info = "\n" + p.getName() + "\n" + p.getMoney();
			Label playerInfo = new Label(info);
			playerVBox.getChildren().add(playerInfo);
		}
	}

	protected void launchGame() {
		table = new Table();
		int numPlayers = retrieveNumPlayers();
		launchAskPlayerInfo(numPlayers);
		askPlayerBet();
		fillPlayerVBox();
		table.setCurrentPlayer();
		centerLabel.setText("Current player: " + activePlayer().getName());
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

	protected void launchErrorDialog(String error) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText(error);
		alert.setContentText("Please enter a valid value for " + error);
		alert.showAndWait();
	}

	// Adds players into game
	public void launchAskPlayerInfo(int numOfPlayers) {
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
	}
	
	protected void askPlayerBet() {
		for (Player p : table.getPlayers()){
			if(p.getMoney() < eachBet()) {
				Alert error = new Alert(AlertType.ERROR, "You do not have enough money");
				error.showAndWait();
				eachBet();
			} else {
				p.setBet(eachBet());
			}
		}
	}

	protected double eachBet() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Set your bet now");
		dialog.setHeaderText("Enter bet amount.");

		Optional<String> result = dialog.showAndWait();
		double bet = Double.parseDouble(result.get());
		if(result.isPresent()){
			return bet;
		}
		else {
			launchErrorDialog("Default Bet");
			return 1.00; //default of 1 player
		}
	}

	protected void askForNewPlayers() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("New Players?");

		alert.setHeaderText("Are there any new players? If yes, press OK");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			playerVBox.getChildren().clear();
			int newPlayers = retrieveNumPlayers();
			if (newPlayers + table.getPlayers().size() > 6) {
				launchTooManyPlayersError();
				newPlayers = retrieveNumPlayers();
			}
			launchAskPlayerInfo(newPlayers);
			fillPlayerVBox();
			table.setCurrentPlayer();
			centerLabel.setText("Current player: " + activePlayer().getName());
		} else {
			return;
		}

	}

	protected void launchThanksForPlaying() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Quitting...");
		alert.setHeaderText(null);
		alert.setContentText("Thanks for playing!");
		alert.showAndWait();
	}

	protected void launchTooManyPlayersError() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Too Many Players");
		alert.setHeaderText("The table cannot exceed 6 players.");
		alert.setContentText("Please try again.");
		alert.showAndWait();
	}

	protected void reset() {
		resetView();
		askForNewPlayers();
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

	protected void resetView() {
		start.setVisible(true);
		hit.setVisible(false);
		stand.setVisible(false);
		ok.setVisible(false);
		handVBox.getChildren().clear();
		dealerHandVBox.getChildren().clear();
		topOutput.setText("");
		playerHandValueLabel.setText("");
		dealerHandValueLabel.setText("");

	}

	private Player activePlayer() {
		return table.getCurrentPlayer();
	}

	private Hand activeHand() {
		return activePlayer().getHand();
	}

	private Dealer dealer() {
		return table.getDealer();
	}

	private Hand dealerHand() {
		return table.getDealer().getHand();
	}

	private Deck deck() {
		return table.getDeck();
	}

	private void setButtonsVisibility() {
		hit.setVisible(false);
		stand.setVisible(false);
		ok.setVisible(true);
	}
}
