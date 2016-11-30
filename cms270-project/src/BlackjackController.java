
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;

public class BlackjackController extends BorderPane {
	private Label message;
	private Label prompt;
	private TextField input;
	private Label result;
	private Button hit;
	private Button stand;
	
	private Dealer dealer;
	private static int numPlayers;
	private ArrayList<Player> players;
	private Deck cardDeck;
	private Player player;
	private Pot pot;
	private final int MAX_PLAYERS = 6;
	private static int roundCount;
	private DecimalFormat money = new DecimalFormat("$0.00");
	private Scanner scan = new Scanner(System.in);
	private Iterator playerIterator;
	
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
		top.setStyle("-fx-background-color: WHITE;");
		top.setPrefHeight(20);
		message = new Label("HELLO");
		top.getChildren().add(message);
		setTop(top);
		
		HBox bottom = new HBox();
		bottom.setStyle("-fx-background-color: WHITE;");
		bottom.setPrefHeight(20);;
		hit = new Button("HIT");
		hit.setOnAction(new EventHandler<ActionEvent> () {
			@Override public void handle(ActionEvent e) {
		        playerMove(e);
		    }
		});
		stand = new Button("STAND");
		stand.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
		        playerMove(e);
		    }
		});
		bottom.getChildren().addAll(hit, stand);
		setBottom(bottom);
		
	}
	
	protected void playerMove(ActionEvent event) {
		
		if (event.getSource() == hit) { // testing if these two are aliases
			message.setText("You hit");
			
		} 
		
		if(event.getSource() == stand) {
			message.setText("You stand");
		}
	}
	
	Label rootLabel;
	TextField rootTF;
	
	
	
}
