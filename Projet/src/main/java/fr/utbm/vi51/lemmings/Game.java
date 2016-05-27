package fr.utbm.vi51.lemmings;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.utbm.vi51.lemmings.agent.Lemming;
import fr.utbm.vi51.lemmings.model.Environment;
import fr.utbm.vi51.lemmings.model.LemmingBody;
import fr.utbm.vi51.lemmings.model.SpawnMapping;
import io.sarl.lang.core.Agent;

public class Game {

	public static void main(String[] args) throws IOException {
		try{
			File file = new File("./src/img/sample2.bmp");
			BufferedImage image = ImageIO.read(file);
			Environment env = new Environment(image);
			env.createLemming();
		}
		catch (Exception e) {
			System.out.println("Wrong file path");
		}
		
			
	}
	
	private static class ApplicationMapping extends SpawnMapping {

		@Override
		public Class<? extends Agent> getAgentTypeForBody(LemmingBody body) {			
				return Lemming.class;
		}
	}
}


