
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BlackjackController  {
	@FXML private TextField textInput;
	@FXML private Label bottomLabel;
	@FXML private Button rightButton;
	
	@FXML protected void hit (ActionEvent event) {
		String i = textInput.getText();
		bottomLabel.setText(i);
	}
	@FXML protected void stand (ActionEvent event) {
		String i = textInput.getText();
		bottomLabel.setText(i);
	}
}
	
//		super();
//		VBox center = new VBox();
//		center.setStyle("-fx-background-color: DARKGREEN;");
//		setCenter(center);
//		message = new Label("Center Pane");
//		
//		VBox left = new VBox();
//		left.setStyle("-fx-background-color: DARKGREEN;");
//		left.setPrefWidth(150);
//		setLeft(left);
//		
//		VBox right = new VBox();
//		right.setStyle("-fx-background-color: DARKGREEN;");
//		right.setPrefWidth(150);
//		setRight(right); 
//		
//		HBox top = new HBox();
//		top.setStyle("-fx-background-color: WHITE;");
//		top.setPrefHeight(20);
//		message = new Label("HELLO");
//		top.getChildren().add(message);
//		setTop(top);
//		
//		HBox bottom = new HBox();
//		bottom.setStyle("-fx-background-color: WHITE;");
//		bottom.setPrefHeight(20);;
//		hit = new Button("HIT");
//		hit.setOnAction(new EventHandler<ActionEvent> () {
//			@Override public void handle(ActionEvent e) {
//		        playerMove(e);
//		    }
//		});
//		stand = new Button("STAND");
//		stand.setOnAction(new EventHandler<ActionEvent>() {
//			@Override public void handle(ActionEvent e) {
//		        playerMove(e);
//		    }
//		});
//		bottom.getChildren().addAll(hit, stand);
//		setBottom(bottom);
//		
//	}
//	
//	protected void playerMove(ActionEvent event) {
//		
//		if (event.getSource() == hit) { // testing if these two are aliases
//			message.setText("You hit");
//			
//		} 
//		
//		if(event.getSource() == stand) {
//			message.setText("You stand");
//		}
//	}
//	
//	protected void displayPlayerInfo(ActionEvent event){
//		String name = 
//	
//
//