
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;

import java.text.DecimalFormat;
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
	private Utils utils;

	private Label rightLabel;
	private Label centerLabel;
	private Label playerHandValueLabel;
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
	private DecimalFormat currency = new DecimalFormat("$0.00");
	private HBox handHBox;
	private HBox dealerHandHBox;

	public BlackjackController() {

		/*** Center pane ***/
		center = new VBox();
		center.setStyle("-fx-background-color: DARKGREEN;");
		center.setPrefWidth(400);
		centerLabel = new Label("");
		centerLabel.setStyle("-fx-text-fill: WHITE");
		handHBox = new HBox();
		handHBox.setStyle("-fx-text-fill: WHITE");
		//handVBox.setAlignment(Pos.BOTTOM_LEFT);
		playerHandValueLabel = new Label("");
		playerHandValueLabel.setStyle("-fx-text-fill: WHITE");
		setCenter(center);
		center.getChildren().addAll(centerLabel, handHBox, playerHandValueLabel);

		/*** Left pane ***/
		playerVBox = new VBox();
		playerVBox.setStyle("-fx-background-color: DARKGREEN;");
		playerVBox.setPrefWidth(150);
		setLeft(playerVBox);

		/*** Right pane ***/
		dealerPane = new VBox();
		dealerPane.setStyle("-fx-background-color: DARKGREEN;");
		dealerPane.setPrefWidth(400);
		rightLabel = new Label("");
		rightLabel.setStyle("-fx-text-fill: WHITE");
		dealerHandHBox = new HBox();
		dealerHandHBox.setStyle("-fx-text-fill: WHITE");
		dealerHandValueLabel = new Label("");
		dealerHandValueLabel.setStyle("-fx-text-fill: WHITE");
		setRight(dealerPane); 
		dealerPane.getChildren().addAll(rightLabel, dealerHandHBox, dealerHandValueLabel);

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
			activePlayer().aceChanger();
			start.setVisible(false);
			hit.setVisible(true);
			stand.setVisible(true);
			if (activePlayer().getSetBet() <= activePlayer().getMoney()) {
				doubleDown.setVisible(true);
			}
			updateView();
		}

		if (event.getSource() == hit) {
			text = activePlayer().getName() + ", you chose to hit.";
			topOutput.setText(text);
			doubleDown.setVisible(false);
			activeHand().addCard(deck().dealCard());
			if (activePlayer().checkForAce()) {
				launchAceValueChooser();
			}
			if (activePlayer().isBusted()) {
				text = "You busted!";
				topOutput.setText(text);
				setButtonsVisibility();
			}
			updateView();
		}

		if(event.getSource() == stand) {
			text = activePlayer().getName() + ", you chose to stand.";
			topOutput.setText(text);
			setButtonsVisibility();
			updateView();
		}
		
		if (event.getSource() == doubleDown) {
			setButtonsVisibility();
			text = activePlayer().getName() + ", you chose to double down.";
			topOutput.setText(text);
			Card c = deck().dealCard();
			activePlayer().doubleDown();
			checkForAceCard(c);
			activeHand().addCard(c);
			updateView();
		}

		if(event.getSource() == ok) {
			if (table.hasNextPlayer()) {
				table.moveToNextPlayer();
				text = "It is now " + activePlayer().getName() + "'s turn.";
				updateView();
				hit.setVisible(true);
				stand.setVisible(true);
				ok.setVisible(false);
				if (activePlayer().getSetBet() <= activePlayer().getMoney()) {
					doubleDown.setVisible(true);
				}
				centerLabel.setText("Current player: " + activePlayer().getName());
				topOutput.setText(text);
			} else {
				text = "It is now the dealer's turn.";
				topOutput.setText(text);
				doDealerMove();
			}
		}
	}

	private void checkForAceCard(Card c) {
		if (c.getCardName().equals("A")) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Ace Card Found!");
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
		updateDealerHandView();

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
	
	protected Card launchAceCardChooser() {
		updateView();
		List<Card> choices = new ArrayList<Card>();
		for (Card c : activeHand().getCards()) {
			if (c.getCardName().equals("A")) {
				choices.add(c);
			}
		}
		ChoiceDialog<Card> dialog = new ChoiceDialog<Card>(null, choices);
		dialog.setTitle("Ace Card Chooser");
		dialog.setHeaderText("Pick the Ace card in your hand that you would like "
				+ "to switch the value of.");
		dialog.setContentText("Choose the card: ");
		
		Optional<Card> result = dialog.showAndWait();
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}
	
	protected void launchAceValueChooser() {
		Card ace = launchAceCardChooser();
		if (ace == null) {
			return;
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Ace Card Found!");
			alert.setContentText("Click OK to set card value to 1, "
					+ "Cancel to set card value to 11.");
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				activeHand().addCard(new Card(1, "A", ace.getCardSuit()));
				activeHand().remove(ace);
			} else {
				activeHand().addCard(new Card(11, "A", ace.getCardSuit()));
				activeHand().remove(ace);
			}
		}
	}

	protected void calculateResults() {
		updateFinalView();
		Alert results = new Alert(AlertType.INFORMATION);
		results.setTitle("Results");
		results.setHeaderText(null);
		String text = "";
		for (Player p : table.getPlayers()) {
			if (dealer().isBusted()) {
				text = "The dealer busted! ";
				if (!p.isBusted()) {
					text += p.getName() + ", you win!";
					p.collectWinnings();
				} else {
					text += p.getName() + ", you busted as well.";
				}
			} else if(dealerHand().checkBlackjack()) {
				text = "Dealer got Blackjack! ";
				if (!p.getHand().checkBlackjack()) {
					text += p.getName() + ", you lose!";
				} else {
					text += p.getName() + ", you got Blackjack as well!"
							+ "You do not win or lose money.";
					p.takeBetBack();
				}
			} else {
				if (p.isBusted()) {
					text = p.getName() + ", you busted! You lose!";
				} else if (p.getHand().checkHandValue() == dealerHand().checkHandValue()) {
					text = p.getName() + ", you have the same hand value"
							+ " as the dealer. You do not win or lose money.";
					p.takeBetBack();
				} else if (p.getHand().checkHandValue() > dealerHand().checkHandValue()) {
					text = p.getName() + ", you have a larger hand value than"
							+ " the dealer. You win!";
					p.collectWinnings();
				} else if (p.getHand().checkBlackjack()) {
					text = p.getName() + ", you got Blackjack! You win!";
					p.collectWinnings();
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
			alert.setContentText("You currently have " + currency.format(p.getMoney())
			+ " left.");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				if (p.getMoney() == 0) {
					launchNoMoney();
					toRemove.add(p);
				} else {
					p.getHand().reset();
				}
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
		askPlayerBet();
	}

	protected void fillPlayerVBox() {
		for (Player p : table.getPlayers()) {
			String info = "\n Player: " + p.getName() + "\n Wallet: " + currency.format(p.getMoney())
			+ "\n Bet: " + currency.format(p.getSetBet()) + "\n Hand: " + p.getHand().checkHandValue();
			Label playerInfo = new Label(info);
			playerInfo.setStyle("-fx-text-fill: WHITE");
			playerVBox.getChildren().add(playerInfo);
		}
	}

	protected void launchNoMoney() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("You have no money!");
		alert.setContentText("Security will be escorting you out now...");
		alert.showAndWait();
	}
	
	protected void launchGame() {
		table = new Table();
		utils = new Utils();
		int numPlayers = retrieveNumPlayers();
		launchAskPlayerInfo(numPlayers);
		if (table.getPlayers().size() == 0) {
			launchThanksForPlaying();
			System.exit(0);
		}
		askPlayerBet();
		fillPlayerVBox();
		table.setCurrentPlayer();
		centerLabel.setText("Current player: " + activePlayer().getName());
		rightLabel.setText("The Dealer");
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
			return 1;		//default player
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
			dialog.getDialogPane().getButtonTypes().addAll(okButtonType);

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
			double bet;
			do {
				bet = eachBet(p);
				if(p.getMoney() < bet) {
					Alert error = new Alert(AlertType.ERROR, p.getName() + ", you do not have enough money");
					error.showAndWait();
				} 
			} while (bet > p.getMoney());
			p.setBet(bet);
		}
	}

	protected double eachBet(Player p) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Set your bet");
		dialog.setHeaderText("Enter bet amount, " + p.getName() + ".");

		Optional<String> result = dialog.showAndWait();
		double bet = Double.parseDouble(result.get());
		if(result.isPresent()){
			return bet;
		} else {
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
		for (Player p : table.getPlayers()) {
			p.setBet(0);
		}
		updateFinalView();
	}

	//Update information in window
	protected void updateView() {
		updateHandView();
		dealerHiddenHandView();
		playerVBox.getChildren().clear();
		fillPlayerVBox();
	}
	
	protected void updateFinalView() {
		updateHandView();
		updateDealerHandView();
		playerVBox.getChildren().clear();
		fillPlayerVBox();
	}

	//Update hand cards
	protected void updateHandView() {
		handHBox.getChildren().clear();
		for (Card c : activeHand().getCards()) {
			ImageView cardView = utils.getCardImageView(c.getCardName() + c.getCardSuit());
			handHBox.getChildren().add(cardView);
		}
		playerHandValueLabel.setText("\n" + 
				Integer.toString(activeHand().checkHandValue()));
	}
	
	protected void updateDealerHandView() {
		dealerHandHBox.getChildren().clear();
		for (Card c : dealerHand().getCards()) {
			ImageView cardView = utils.getCardImageView(c.getCardName() + c.getCardSuit());
			dealerHandHBox.getChildren().add(cardView);
		}
		dealerHandValueLabel.setText("\n" + 
				Integer.toString(dealerHand().checkHandValue()));
	}
	
	protected void dealerHiddenHandView() {
		dealerHandHBox.getChildren().clear();
		Card c = dealerHand().getCards().get(0);
		ImageView hiddenCard = utils.getHiddenCardImageView();
		ImageView cardView = utils.getCardImageView(c.getCardName() + c.getCardSuit());
		dealerHandHBox.getChildren().addAll(cardView, hiddenCard);
	}

	protected void resetView() {
		start.setVisible(true);
		hit.setVisible(false);
		stand.setVisible(false);
		ok.setVisible(false);
		handHBox.getChildren().clear();
		dealerHandHBox.getChildren().clear();
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
		doubleDown.setVisible(false);
	}
}
