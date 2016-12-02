import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Utils {
	public final String RESOURCE_FOLDER = "/cms270-project/resources/";
	
	public Image getImage(String imageName) {
		InputStream imageInputStream = Utils.class.getResourceAsStream(RESOURCE_FOLDER + imageName);
		return new Image(imageInputStream);
	}
	
	public ImageView getCardImageView(String imageName) {
		return new ImageView(getImage(imageName + ".png"));
	}	
}
