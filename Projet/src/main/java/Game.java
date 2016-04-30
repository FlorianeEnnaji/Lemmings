import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Game {

	public static void main(String[] args) throws IOException {
		try{
			File file = new File("./src/img/sample.bmp");
			BufferedImage image = ImageIO.read(file);
			Environment env = new Environment(image);
		}
		catch (Exception e) {
			System.out.println("Wrong file path");
		}
		
			
	}
	
	

}
