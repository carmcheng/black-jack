
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BlackjackController {
	@FXML private TextField textInput;
	@FXML private Label bottomLabel;
	@FXML private Button rightButton;

	@FXML protected void rightClick(ActionEvent event) {
			String i = textInput.getText();
			bottomLabel.setText(i);
		
	} 
}
