import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Utils {
	
	public Image getImage(String imageName) {
		InputStream imageInputStream = Utils.class.getResourceAsStream(imageName);
		return new Image(imageInputStream);
	}
	
	public ImageView getCardImageView(String imageName) {
		ImageView iv = new ImageView(getImage(imageName + ".png"));
		iv.setPreserveRatio(true);
		iv.setFitHeight(120);
		return iv;
	}	
	
	public ImageView getHiddenCardImageView() {
		ImageView iv = new ImageView(getImage("flipped.png"));
		iv.setPreserveRatio(true);
		iv.setFitHeight(120);
		return iv;
	}
}
