
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BlackjackController {
	@FXML private TextField textInput;
	@FXML private Label bottomLabel;
	@FXML private Label topLabel;
	@FXML private Button rightButton;
	@FXML private Button leftButton;

	@FXML protected void stand(ActionEvent event) { // when player stands 
		String i = textInput.getText();
		bottomLabel.setText(i);
	} 
	
	@FXML protected void hit(ActionEvent event) { // when player hits
		String i = textInput.getText();
		topLabel.setText(i);
	}
}
