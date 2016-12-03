
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.util.Pair;

/**
 * This class implements the GUI's for our game which contains
 * methods to play a game of Blackjack along with dialog box implementations
 * to enhance interaction with one or more players.
 * @author Zoe, Carmen, Ebba, Aruna
 *
 */
public class BlackjackController extends BorderPane {
	private String text;
	private Player player;
	private Table table;
	private int numPlayers;
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
	private HBox handHBox;
	private HBox dealerHandHBox;
	private DecimalFormat currency = new DecimalFormat("$0.00");

	public BlackjackController() {

		/*** Center pane ***/
		center = new VBox(5);
		center.setStyle("-fx-background-color: #023030;");
		center.setPrefWidth(350);
		centerLabel = new Label("");
		centerLabel.setStyle("-fx-text-fill: #E6E6E6; -fx-padding: 8px; -fx-font-size: 12pt;");
		handHBox = new HBox();
		handHBox.setStyle("-fx-padding: 8px;");
		playerHandValueLabel = new Label("");
		playerHandValueLabel.setStyle("-fx-text-fill: #E6E6E6; -fx-padding: 8px;");
		center.getChildren().addAll(centerLabel, handHBox, playerHandValueLabel);
		setCenter(center);

		/*** Left pane ***/
		playerVBox = new VBox();
		playerVBox.setStyle("-fx-font-size: 14; -fx-background-color: #055A5B;");
		playerVBox.setPrefWidth(200);
		setLeft(playerVBox);

		/*** Right pane ***/
		dealerPane = new VBox();
		dealerPane.setStyle("-fx-background-color: #055A5B;");
		dealerPane.setPrefWidth(400);
		rightLabel = new Label("");
		rightLabel.setStyle("-fx-text-fill: #E6E6E6; -fx-padding: 8px; -fx-font-size: 12pt;");
		dealerHandHBox = new HBox();
		dealerHandHBox.setStyle("-fx-text-fill: #E6E6E6; -fx-padding: 8px;");
		dealerHandValueLabel = new Label("");
		dealerHandValueLabel.setStyle("-fx-text-fill: #E6E6E6; -fx-padding: 8px;");
		setRight(dealerPane); 
		dealerPane.getChildren().addAll(rightLabel, dealerHandHBox, dealerHandValueLabel);

		/*** Top pane ***/
		top = new HBox();
		top.setStyle("-fx-background-color: #E6E6E6; -fx-padding: 5px;"
				+ "-fx-font-family:Gafata; -fx-font-size: 20;");
		top.getStylesheets().add("https://fonts.googleapis.com/css?family=Gafata");
		top.setPrefHeight(40);
		top.setAlignment(Pos.CENTER);
		topOutput = new Text("");
		top.getChildren().add(topOutput);
		setTop(top);

		/*** Bottom pane ***/
		bottom = new HBox();
		bottom.setStyle("-fx-background-color: #E6E6E6; -fx-text-fill: #055A5B; -fx-padding: 8px;");
		bottom.setPrefHeight(30);
		bottom.setSpacing(10);
		bottom.setAlignment(Pos.CENTER);
		start = new Button("Start");
		start.setStyle("-fx-padding: 8px;");
		start.setOnAction(new EventHandler<ActionEvent> () {
			@Override public void handle(ActionEvent e) {
				doPlayerMove(e);
			}
		});
		hit = new Button("Hit");
		hit.setVisible(false);
		hit.setOnAction(new EventHandler<ActionEvent> () {
			@Override public void handle(ActionEvent e) {
				doPlayerMove(e);
			}
		});
		stand = new Button("Stand");
		stand.setVisible(false);
		stand.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				doPlayerMove(e);
			}
		});
		doubleDown = new Button("Double");
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
/**
 * This method registers what move the play wants to make next and handles the
 * appropriate output by checking for certain events/ buttons pressed by players
 * @param event
 */
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
/**
 * This method checks if a dealt card is an ace. If so, the player can choose
 * the valuse of the card (either 1 or 11)
 * @param c
 */
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
/**
 * This method is called after all players have played and it is the dealer's
 * turn. While the dealer has a hand with a value under 17, a card is dealt. 
 * The results are then calculated, followed by a new round being started
 */
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
/**
 * This method allows the player to pick an ace card present in their hand that they
 * want to change the value of, and returns the chosen card
 * @return Card
 */
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
/**
 * This method allows the player to choose whether an ace card dealt to
 * them should have the value 1 or 11
 */
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
/**
 * This method calculates the final results of the game after all the players
 * and the dealer have played. It checks if any of the players or the dealer
 * has blackjack or has busted. It also notifies each player regarding whether
 * they have won or lost against the dealer. 
 */
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
			} else if (dealerHand().checkBlackjack()) {
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
/**
 * This method asks the players whether they would like to play another round.
 * It notifies each player of how much money they have left after the previous
 * round. If a player does not wish to play another round, they are removed
 */
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
/**
 * This method updates the displayed information for each player. It checks
 * for each player's name, amount of money and hand
 */
	protected void fillPlayerVBox() {
		for (Player p : table.getPlayers()) {
			String info = "\nPlayer: " + p.getName() + "\nWallet: " + currency.format(p.getMoney())
				+ "\nBet: " + currency.format(p.getSetBet());
			String hand = "Hand: [";
			for (Card c : p.getHand().getCards()) {
				hand += " " + c.getCardName() + " ";
			}
			hand += "]\nHand Value: " + p.getHand().checkHandValue();
			Label playerInfo = new Label(info);
			Label playerHandInfo = new Label(hand);
			playerInfo.setStyle("-fx-text-fill: #E6E6E6");
			playerHandInfo.setStyle("-fx-text-fill: #E6E6E6");
			playerVBox.getChildren().addAll(playerInfo, playerHandInfo);
		}
	}
/**
 * This method alerts the player if they do not have any money left. In this 
 * case, they can no longer play the game
 */
	protected void launchNoMoney() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("You have no money!");
		alert.setContentText("Security will be escorting you out now...");
		alert.showAndWait();
	}
/**
 * This method starts a game by initializing a new table, asking for each 
 * player's bet and displaying the current player and dealer's hands
 */
	protected void launchGame() {
		table = new Table();
		utils = new Utils();
		numPlayers = retrieveNumPlayers();
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
/**
 * This method asks how many players will be playing the game, giving 
 * an input choice of 1-6
 * @return number of players given the chosen input
 */
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
			return 0;		//default player
		}
	}
/**
 * This method launches an error alert if a player enters an invalid value.
 * @param error
 */
	protected void launchErrorDialog(String error) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText(error);
		alert.setContentText("Please enter a valid value for " + error);
		alert.showAndWait();
	}

	// Adds players into game
	/**
	 * This method retrieves the name and amount of money that each player has by
	 * running through the total number of players. If a player enters an invalid value,
	 * an error is launched. 
	 * @param numOfPlayers
	 */
	public void launchAskPlayerInfo(int numOfPlayers) {
		players = table.getPlayers();
		for (int i = 0; i < numOfPlayers; i++) {
			try {
				Dialog<Pair<String, String>> dialog = new Dialog<>();
				dialog.setTitle("Player Information Retrieval");
				dialog.setHeaderText("Enter your name and amount of money you have.");

				ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().add(okButtonType);

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
				
				Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
				okButton.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						if (!validateInput(money) || !validateInput(name)) {
							launchErrorDialog("Name/Money");
							e.consume();
						}
					}
				});

				dialog.setResultConverter(dialogButton -> {
					if (dialogButton == okButtonType) {
						return new Pair<>(name.getText(), money.getText());
					}
					return null;
				});

				Optional<Pair<String,String>> result = dialog.showAndWait();

				result.ifPresent(playerInfo -> {
					player = new Player(playerInfo.getKey(), Double.parseDouble(playerInfo.getValue()));
			});
			players.add(player);
			} catch (Exception e) {
				launchPlayerInfoError();
			}
		}
	}
/**
 * This method launched an error alert if a player enter an invalid value
 * when entering their information. 
 */
	protected void launchPlayerInfoError() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Player Info Error");
		alert.setHeaderText("Invalid inputs");
		alert.setContentText("You will not get to play.");
		alert.showAndWait();
	}
/**
 * This method asks each player for their bet. If they ask to bet a larger 
 * amount of money than what they have, an error alert is launched. 
 */
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
	
	/**
	 * This method prompts the player to set a bet amount for the round.
	 * @param p - The player to set a bet for.
	 * @return - double representation of bet amount. 
	 */
	protected double eachBet(Player p) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.getDialogPane().getButtonTypes().remove(ButtonType.CANCEL);
		dialog.setTitle("Set your bet");
		dialog.setHeaderText("Enter bet amount, " + p.getName() + ".");

		Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
		okButton.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (!validateInput(dialog.getEditor())) {
					launchErrorDialog("Bet");
					e.consume();
				}
			}
		});
		
		Optional<String> result = dialog.showAndWait();
		double bet = Double.parseDouble(result.get());
		if(result.isPresent()) {
			return bet;
		} else {
			return 0.1;
		}
	}
	
	/**
	 * This method ensures that the player entered a valid input 
	 * when prompted to type in information.
	 * @param tf - The text field to check for valid input
	 * @return - boolean representation if input was valid
	 */
	private boolean validateInput(TextField tf) {
		String n = tf.getText();
		if(n == null || n.equals("")) {
			return false;
		} else {
			return true;
		}
	}
	
	/** 
	 * This method prompts the user to check for new players at
	 * the start of a new round. If so, new players are asked to input
	 * their information in order to play. 
	 */
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
			table.setCurrentPlayer();
			centerLabel.setText("Current player: " + activePlayer().getName());
			return;
		}

	}
	
	/**
	 * This method confirms to the user that they are quitting a game
	 * of Blackjack.
	 */
	protected void launchThanksForPlaying() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Quitting...");
		alert.setHeaderText(null);
		alert.setContentText("Thanks for playing!");
		alert.showAndWait();
	}
	
	/**
	 * This method indicates an error when there are more than
	 * six players at the table. The user is asked to try again.
	 */
	protected void launchTooManyPlayersError() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Too Many Players");
		alert.setHeaderText("The table cannot exceed 6 players.");
		alert.setContentText("Please try again.");
		alert.showAndWait();
	}
	
	/**
	 * This method resets the game for a new round of Blackjack.
	 */
	protected void reset() {
		resetView();
		if(numPlayers < 6) {
			askForNewPlayers();
		}
		for (Player p : table.getPlayers()) {
			p.setBet(0);
		}
		updateFinalView();
	}

	/**
	 * This method updates the information when the 
	 * dealer's complete hand is shown.
	 */
	protected void updateView() {
		updateHandView();
		if (dealerHand().checkBlackjack()) {
			updateDealerHandView();
			calculateResults();
		} else {
			dealerHiddenHandView();
		}
		playerVBox.getChildren().clear();
		fillPlayerVBox();
	}
	
	/**
	 * This method updates the final view of both player's and dealer's hand value.
	 */
	protected void updateFinalView() {
		updateDealerHandView();
		handHBox.getChildren().clear();
		playerHandValueLabel.setText("Hand Value: ");
		playerVBox.getChildren().clear();
		fillPlayerVBox();
	}

	/**
	 * This method updates the view(image) of the each card in the player's hand
	 * while playing a round of Blackjack.
	 */
	protected void updateHandView() {
		handHBox.getChildren().clear();
		for (Card c : activeHand().getCards()) {
			ImageView cardView = utils.getCardImageView(c.getCardName() + c.getCardSuit());
			handHBox.getChildren().add(cardView);
		}
		playerHandValueLabel.setText("\nHand Value: " + 
				Integer.toString(activeHand().checkHandValue()));
	}
	
	/**
	 * This method updates the view(image) of the each card in the dealer's hand
	 * while playing a round of Blackjack.
	 */
	protected void updateDealerHandView() {
		dealerHandHBox.getChildren().clear();
		for (Card c : dealerHand().getCards()) {
			ImageView cardView = utils.getCardImageView(c.getCardName() + c.getCardSuit());
			dealerHandHBox.getChildren().add(cardView);
		}
		dealerHandValueLabel.setText("\nDealer's Hand Value: " + 
				Integer.toString(dealerHand().checkHandValue()));
	}
	
	/**
	 * This method displays the dealer's hidden card when it's their
	 * turn to play and updates all the cards in their hand.
	 */
	protected void dealerHiddenHandView() {
		dealerHandHBox.getChildren().clear();
		Card c = dealerHand().getCards().get(0);
		ImageView hiddenCard = utils.getHiddenCardImageView();
		ImageView cardView = utils.getCardImageView(c.getCardName() + c.getCardSuit());
		dealerHandHBox.getChildren().addAll(cardView, hiddenCard);
	}
	
	/**
	 * This method resets the view of the game when a 
	 * new round of Blackjack begins.
	 */
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
		centerLabel.setText("");
	}
	
	/**
	 * This method is used to get the current player playing Blackjack.
	 * @return - the current player
	 */
	private Player activePlayer() {
		return table.getCurrentPlayer();
	}
	
	/**
	 * This method is used to get the current player's hand playing Blackjack.
	 * @return - current player's hand/value
	 */
	private Hand activeHand() {
		return activePlayer().getHand();
	}
	
	/**
	 * This method is used to get the dealer object.
	 * @return - the dealer info
	 */
	private Dealer dealer() {
		return table.getDealer();
	}
	
	/**
	 * This method is used to get the dealer's hand.
	 * @return - dealer's hand/value
	 */
	private Hand dealerHand() {
		return table.getDealer().getHand();
	}
	
	/**
	 * This method is used to get the current deck at the table.
	 * @return - the card deck 
	 */
	private Deck deck() {
		return table.getDeck();
	}
	
	/**
	 * This method is used to display appropriate buttons when necessary.
	 */
	private void setButtonsVisibility() {
		hit.setVisible(false);
		stand.setVisible(false);
		ok.setVisible(true);
		doubleDown.setVisible(false);
	}
}
