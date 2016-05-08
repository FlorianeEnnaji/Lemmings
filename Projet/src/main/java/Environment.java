import java.awt.Color;
import java.awt.image.BufferedImage;

public class Environment {

	int height = 0;
	int width = 0;
	
	Color m_lightBrown = new Color(205,133,63);
	Color m_yellow = new Color(255,218,57);
	Color m_brown = new Color(153,102,51);
	
	EnvironmentObject[][] m_world;
	
	public Environment(){
		m_world = new EnvironmentObject[width][height];
		
	}
	
	public Environment(BufferedImage image){
		initializeWorld(image);
		
	}
	
	private void initializeWorld (BufferedImage image){
		int id = 0;
		m_world = new EnvironmentObject[image.getWidth()][image.getHeight()];
		for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
		{
			for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
		    {
		        int color = image.getRGB(xPixel, yPixel);
		        if (color==Color.BLACK.getRGB()) {
		            m_world[xPixel][yPixel] = new WorldPixel("empty", id);
		        } else if (color==m_lightBrown.getRGB()) {
		            m_world[xPixel][yPixel] = new WorldPixel("dig", id);
		        } else if (color==m_yellow.getRGB()) {
		            m_world[xPixel][yPixel] = new WorldPixel("entry", id);
		        } else if (color==Color.WHITE.getRGB()) {
		            m_world[xPixel][yPixel] = new WorldPixel("exit", id);
		        } else if (color==m_brown.getRGB()) {
		            m_world[xPixel][yPixel] = new WorldPixel("ground", id);
		        }
		        id++;
		    }
		}
	}
	
	
	
}
