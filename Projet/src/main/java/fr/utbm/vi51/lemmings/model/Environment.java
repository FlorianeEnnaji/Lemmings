package fr.utbm.vi51.lemmings.model;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import fr.utbm.vi51.lemmings.utils.enums.MoveDirection;

public class Environment {

	/** Matrix of pixels */
	private EnvironmentObject[][] m_world;
	
	/** Height and width of the world */
	private int m_height = 0;
	private int m_width = 0;
	
	/** Color of pixels of the world */
	private Color m_lightBrown = new Color(205,133,63);
	private Color m_yellow = new Color(255,218,57);
	private Color m_brown = new Color(153,102,51);
	
	/** Constructors */
	public Environment(){
		m_world = new EnvironmentObject[m_width][m_height];	
	}
	
	public Environment(BufferedImage image){
		initializeWorld(image);
		printEnvironment();
	}
	
	/** World initializing */
	private void initializeWorld (BufferedImage image){
		int id = 0;
		m_width = image.getWidth();
		m_height = image.getHeight();
		m_world = new EnvironmentObject[m_width][m_height];
		
		for (int yPixel = 0; yPixel < m_height; yPixel++)
		{
			for (int xPixel = 0; xPixel < m_width; xPixel++)
		    {
		        int color = image.getRGB(xPixel, yPixel);
		        /* int alpha = (color >> 24) & 0xFF;
		        int red =   (color >> 16) & 0xFF;
		        int green = (color >>  8) & 0xFF;
		        int blue =  (color      ) & 0xFF;
		        System.out.println(alpha + " " + red + " " + green + " " + blue); */
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
		for (int i = 0; i < m_height; i++)
		{
			System.out.print(i);
			for (int j = 0; j < m_width; j++)
		    {
				WorldPixel pixel = (WorldPixel) m_world[j][i];
				
				if (pixel.isEmpty()) {
					System.out.print(" #");
				} else if (pixel.isDiggable()) {
					System.out.print(" .");
				} else if (pixel.isEntry()) {
					System.out.print(" X");
				} else if (pixel.isExit()){
					System.out.print(" O");
				} else {
					System.out.print(" _");
				}
		    }
			System.out.println();
		}
	}
	
	public int getWidth() {
		return this.m_width;
	}
	
	public int getHeight() {
		return this.m_height;
	}
	
	/** Return the position of the body */
	private Point getPosition(Body body) {
		for(int x=0; x< m_width; x++) {
			for(int y=0; y< m_height; y++) {
				if (m_world[x][y] == body) {
					return new Point(x,y);
				}
			}
		}
		return null;
	}
	
	/** Return the perception of the body */
	public List<PerceivableObject> perceive(LemmingBody body) {
		List<PerceivableObject> list = new LinkedList<PerceivableObject>();
		Point position = getPosition(body);
		if (position!=null) {
			/* Get all objects present in perception field */
			int upDist = 1;
			int downDist = 4;
			int leftDist = 1;
			int rightDist = 1;
			
			// 4 directions
			MoveDirection[] directions = MoveDirection.values();

			int x, y;
			PerceivableObject perception;
			// TODO
		}

		return list;
	}

	public void move(Body body, MoveDirection direction) {
		Point position = getPosition(body);
		if (position != null) {
			int x = position.x + direction.getXMove();
			int y = position.y + direction.getYMove();

			if ((x!=position.x || y!=position.y) && x>=0 && x<this.m_width && y>=0 && y<this.m_height) {
				// TODO
			}
		}
	}
	
}
