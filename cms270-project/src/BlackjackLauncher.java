import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/** This is our gui class.
 */
public class BlackjackLauncher extends Application{

	Label rootLabel;
	TextField rootTF;
	
	public void start(Stage stage) {
		BlackjackController root = new BlackjackController();

		Scene scene = new Scene(root, 1024, 500);

		stage.setScene(scene);
		stage.setTitle("Let's Play Blackjack!");
		stage.show();
		
		root.launchGame();
		
	}

	public static void main(String [] args) {
		launch(args);
	}
}
