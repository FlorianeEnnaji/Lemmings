package fr.utbm.vi51.lemmings.model;
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
		printEnvironment();
	}
	
	private void initializeWorld (BufferedImage image){
		int id = 0;
		width = image.getWidth();
		height = image.getHeight();
		m_world = new EnvironmentObject[width][height];
		for (int yPixel = 0; yPixel < height; yPixel++)
		{
			for (int xPixel = 0; xPixel < width; xPixel++)
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
	
	public void printEnvironment(){
		for (int i = 0; i < height; i++)
		{
			System.out.print(i);
			for (int j = 0; j < width; j++)
		    {
				WorldPixel pixel = (WorldPixel) m_world[i][j];
				
				if (pixel.isEmpty()) {
					System.out.print(" O");
				} else if (pixel.isDiggable()) {
					System.out.print(" ..");
				} else if (pixel.isEntry()) {
					System.out.print(" X");
				} else if (pixel.isExit()){
					System.out.print(" []");
				} else {
					System.out.print(" _");
				}
		    }
			System.out.println();
		}
	}
	
}
