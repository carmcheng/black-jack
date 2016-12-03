import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/** This class launches the GUI's for our BlackJack game,
 *
 *@author Zoe, Carmen, Ebba, Aruna
 */
public class BlackjackLauncher extends Application{

	Label rootLabel;
	TextField rootTF;
	
	public void start(Stage stage) {
		BlackjackController root = new BlackjackController();

		Scene scene = new Scene(root, 1400, 600);

		stage.setScene(scene);
		stage.setTitle("Let's Play Blackjack!");
		stage.show();
		
		root.launchGame();
		
	}

	public static void main(String [] args) {
		launch(args);
	}
}
