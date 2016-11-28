
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BlackjackLauncher extends Application {
	public void start(Stage stage) {
		try {
			URL url = getClass().getResource("Blackjack_borderpane.fxml");
			BorderPane root = FXMLLoader.load(url);
			
			Scene scene = new Scene(root, 600, 400);
			scene.setFill(Color.GREEN);
			stage.setScene(scene);
			stage.setTitle("Blackjack Game");
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args) {
		launch(args);
	}
}
