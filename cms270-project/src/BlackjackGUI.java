import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class BlackjackGUI extends Application{
	public static void main (String [] args) {
		Application.launch(args);
	}
	@Override
	public void start(Stage stage) {
		stage.setTitle("Blackjack Test");
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10, 20, 10, 20));
		
		//creating vbox for the center, setting background as green 
		VBox center = new VBox();
		bp.setCenter(center);
		center.setStyle("-fx-background-color: GREEN;");
		
		Scene scene = new Scene(bp, 600, 400, Color.GREEN);
		stage.setScene(scene);
		stage.show();	
	}
	
	public void displayCurrentPlayer(){
		
	}

}
