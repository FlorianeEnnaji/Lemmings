package fr.utbm.vi51.lemmings.model;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import fr.utbm.vi51.lemmings.utils.enums.MoveDirection;

public class Environment {

	/** Matrix of pixels */
	private WorldPixel[][] m_world;
	
	/** Height and width of the world */
	private int m_height = 0;
	private int m_width = 0;
	
	/** Color of pixels of the world */
	private Color m_lightBrown = new Color(205,133,63);
	private Color m_yellow = new Color(255,218,57);
	private Color m_brown = new Color(153,102,51);
	
	/** Constructors */
	public Environment(){
		m_world = new WorldPixel[m_width][m_height];	
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
		m_world = new WorldPixel[m_width][m_height];
		
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
	
		
	/** Return the perception of the body */
	public List<PerceivableObject> getPerception(Body body) {
		List<PerceivableObject> list = new LinkedList<PerceivableObject>();
		Point position = body.getPosition();
		if (position!=null) {
			/* Get all objects present in perception field */
			PerceivableObject perception;
			for (int i = position.x-1;i <= position.x + 1; i++){
				if (i >= 0 && i < m_width){
					for (int j = position.y-1;j <= position.y + 1; j++){
						if (j >= 0 && j < m_height){
							WorldPixel pixel = m_world[i][j];
							perception = new PerceivableObject(new Point(i, j), 
									pixel.isDiggable(), pixel.isEmpty(), 
									pixel.isClimbable(), pixel.isEntry(), 
									pixel.isExit());
							list.add(perception);
						}
					}	
				}	
			}
		}
		return list;
	}

	public void move(Body body, MoveDirection direction) {
		//TODO change to ensure that lemmings can reach the given direction and manage the death and the end (the lemming is arrived)
		Point position = body.getPosition();
		body.setDirection(direction);
		
		if (position != null) {
			Point pos = new Point(position.x + direction.getXMove(), position.y + direction.getYMove());

			if ((pos.x != position.x || pos.y != position.y) 
					&& pos.x >= 0 && pos.x < this.m_width 
					&& pos.y >= 0 && pos.y < this.m_height) {
				body.setPosition(new Point(pos.x, pos.y));
			}
		}
	}
	
	public void dig (MoveDirection direction){
		//TODO the function that remove the block in the chosen direction
	}
	
	/*
	 * Function that moves the lemming
	 * if he can climb the pixel in his direction,
	 * or if he is climbing, carry on or land safely
	 */
	public void climb (Body body){
		Point position = body.getPosition();
		MoveDirection direction = body.getDirection();
		Point climbablePosition = new Point(position.x + direction.getXMove(), position.y + direction.getYMove());
		
		if (position != null & climbablePosition != null) {
			if (!body.isClimbing() && m_world[climbablePosition.x][climbablePosition.y].isClimbable()) {
				//Start of the climbing
				body.setPosition(new Point(position.x, position.y + MoveDirection.up.getYMove()));
				body.setIsClimbing(true);
			} else if (body.isClimbing()) {
				if (m_world[position.x][position.y+MoveDirection.up.getYMove()]) {
					
				} else if (m_world[climbablePosition.x][climbablePosition.y+MoveDirection.up.getYMove()].isClimbable() ||
					m_world[climbablePosition.x][climbablePosition.y+MoveDirection.up.getYMove()].isEmpty()) {
					//Mid steps of climbing
					body.setPosition(new Point(position.x, position.y + MoveDirection.up.getYMove()));
				} else if (m_world[climbablePosition.x][climbablePosition.y].isEmpty()) {
					//Top of the climbing
					body.setPosition(climbablePosition);
					body.setIsClimbing(false);
				}
			}
		}
		//TODO : take care of climbing and being blocked by a pixel
	}
	
	/*
	 * Function that moves the lemming
	 * if he can jump in his direction
	 * or if he is jumping, carry on or land safely
	 */
	public void jump (Body body){
		Point position = body.getPosition();
		MoveDirection direction = body.getDirection();
		Point jumpablePosition = new Point(position.x + direction.getXMove(), position.y + direction.getYMove());
		
		if (position != null & jumpablePosition != null) {
			if (!body.isJumping() && 
				m_world[jumpablePosition.x][jumpablePosition.y+MoveDirection.down.getYMove()].isEmpty() &&
				m_world[jumpablePosition.x + direction.getXMove()][jumpablePosition.y].isEmpty()) {
				//Start of the jump
				body.setPosition(new Point(jumpablePosition.x, jumpablePosition.y + MoveDirection.down.getYMove()));
				body.setIsJumping(true);
			} else if (body.isJumping()) {
				if (position.y+MoveDirection.down.getYMove() >= m_height) {
					//TODO : Destroy Lemming and its body
				} else if (m_world[position.x][position.y+MoveDirection.down.getYMove()].isEmpty()) {
					//Mid steps of jump
					body.setPosition(new Point(position.x, position.y + MoveDirection.down.getYMove()));
				} else if (!m_world[position.x][position.y+MoveDirection.down.getYMove()].isEmpty()) {
					//End of the jump
					body.setPosition(new Point(position.x, position.y + MoveDirection.down.getYMove()));
					body.setIsJumping(false);
				}
			}
		}
		
	}
	
}
