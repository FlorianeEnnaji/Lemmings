package fr.utbm.vi51.lemmings.model;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Environment {

	int height = 0;
	int width = 0;
	
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
		            m_world[xPixel][yPixel] = new WorldPixel("ground", id);
		        } else {
		            m_world[xPixel][yPixel] = new WorldPixel("empty", id);
		        }
		        id++;
		    }
		}
	}
	
	
	
}
