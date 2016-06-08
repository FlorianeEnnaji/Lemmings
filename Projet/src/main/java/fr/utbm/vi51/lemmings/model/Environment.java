package fr.utbm.vi51.lemmings.model;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import fr.utbm.vi51.lemmings.QTable;
import fr.utbm.vi51.lemmings.utils.enums.ActionEnum;
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
	
	/** Entry and Exit **/
	private Point entry;
	private boolean isArrived=false;
	
	/** Qtable **/
	private QTable m_qtable = new QTable();
	
	/** List of agentbodies */
	public final Map<UUID,LemmingBody> agentBodies = new TreeMap<UUID,LemmingBody>();
	
	/** Link between agent and Body */
	private LinkerClass link = new LinkerClass();
	
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
		            this.entry = new Point(xPixel, yPixel);
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
				
				if (pixel.isEntry()) {
					System.out.print(" X");
				} else if (pixel.isEmpty()) {
					System.out.print(" #");
				} else if (pixel.isDiggable()) {
					System.out.print(" .");
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
	
	public boolean isArrived(){
		return isArrived;
	}
	
	public Map<UUID,LemmingBody> getAgentBodies(){
		return this.agentBodies;
	}
	
	/**Create the body */
	public void createLemming() {
		createBody();
	}
	
	private void createBody(){
		LemmingBody body = new LemmingBody(this, MoveDirection.right, entry);
		UUID ID = new UUID(1, agentBodies.size()+1);
		agentBodies.put(ID, body);
		link.createAgent(ID);
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
		Point position = body.getPosition();
		if (position == null) {
			position = new Point(0, 0);
		}
		body.setDirection(direction);
		if (direction == null) {
			direction = MoveDirection.right;
		}
		
		Point pos = new Point(position.x + direction.getXMove(), position.y + direction.getYMove());
		ActionEnum action = ActionEnum.WALK_EAST;
		
		if (direction.getXMove() < 0) {
			action = ActionEnum.WALK_WEST;
		}
		
		if (position != null) {
			if (isInWorldDimensions(pos)) {
				if (m_world[pos.x][pos.y].isEmpty()) {
					if (!m_world[pos.x][pos.y+MoveDirection.down.getYMove()].isEmpty()) {
						//Lemming can walk in his direction
						if (body.isFalling()) {
							body.setIsFalling(false);
							if(body.getFallingHeight() > 4) {
								//Kill Lemming and its body
								m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.KILL_HIMSELF.getYourReward());
								return;
							}
						}
						body.setPosition(new Point(pos.x, pos.y));
						m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.Living.getYourReward());
					} else {
						//Lemming is falling
						if (!body.isFalling()) {
							body.setIsFalling(true);
						} else if (body.getFallingHeight() < 5 && isInWorldDimensions(new Point(pos.x, pos.y+MoveDirection.down.getYMove()))){
							body.setFallingHeight(body.getFallingHeight() + 1);
						} else {
							//Destroy Lemming and its body
							m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.KILL_HIMSELF.getYourReward());
							return;
						}
						body.setPosition(new Point(pos.x, pos.y+MoveDirection.down.getYMove()));
						m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.Living.getYourReward());
					}
				} else if (m_world[pos.x][pos.y].isExit()) {
					//Lemming has arrived to exit!
					m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.GET_OUT.getYourReward());
					isArrived=true;
				} else {
					m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
				}
			} else {
				m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
			}
		}
	}
	
	/*
	 * Function that moves the lemming and change diggable pixel
	 * if he can dig the pixel in his direction,
	 * or if he is digging, carry on or moves in his direction
	 */
	public void dig (Body body, MoveDirection direction){
		if (body != null) {
			Point position = body.getPosition();
			if (position == null) {
				position = new Point(0, 0);
			}
			if (direction == null) {
				direction = MoveDirection.down;
			}

			Point diggablePosition = new Point(position.x + direction.getXMove(), position.y + direction.getYMove());
			Point finalPosition = position;
			ActionEnum action = ActionEnum.DIG_SOUTH;
			
			if(direction.getXMove() < 0) {
				action = ActionEnum.DIG_WEST;
			} else if(direction.getXMove() > 0) {
				action = ActionEnum.DIG_EAST;
			}
			
			if (position != null & diggablePosition != null) {
				if (!isInWorldDimensions(diggablePosition)) {
					m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
					return;
				} else if (m_world[diggablePosition.x][diggablePosition.y].isDiggable()) {
					//Digging
					m_world[diggablePosition.x][diggablePosition.y].setEmpty();
					finalPosition = diggablePosition;
				} else if (m_world[diggablePosition.x][diggablePosition.y].isDiggable()) {
					//Dig through exit
					m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.GET_OUT.getYourReward());
					isArrived=true;
					body.setPosition(diggablePosition);
					body.setIsClimbing(false);
					return;
				} else {
					m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
					return;
				}
				
				if (isInWorldDimensions(finalPosition)) {
					body.setPosition(finalPosition);
					m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.Living.getYourReward());
				} else {
					m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
				}
				
			}
		}
	}
	
	/*
	 * Function that moves the lemming
	 * if he can climb the pixel in his direction,
	 * or if he is climbing, carry on or land safely
	 */
	public void climb (Body body){
		if (body != null) {
			Point position = body.getPosition();
			if (position == null) {
				position = new Point(0, 0);
			}
			MoveDirection direction = body.getDirection();
			if (direction == null) {
				direction = MoveDirection.up;
			}
			Point climbablePosition = new Point(position.x + direction.getXMove(), position.y + direction.getYMove());
			Point finalPosition = position;
			ActionEnum action = ActionEnum.CLIMB;
			
			if (position != null & climbablePosition != null) {
				if (!isInWorldDimensions(climbablePosition)) {
					m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
					return;
				} else if (!body.isClimbing() && m_world[climbablePosition.x][climbablePosition.y].isClimbable()) {
					//Start of the climbing
					finalPosition = new Point(position.x, position.y + MoveDirection.up.getYMove());
					body.setIsClimbing(true);
				} else if (body.isClimbing()) {
					Point top = new Point(position.x, position.y+MoveDirection.up.getYMove());
					if (!isInWorldDimensions(top)) {
						m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
						return;
					} else if (!m_world[top.x][top.y].isEmpty()) {
						//Kill Lemming and its body
						m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.KILL_HIMSELF.getYourReward());
						return;
					} else if (m_world[climbablePosition.x][climbablePosition.y+MoveDirection.up.getYMove()].isClimbable() ||
						m_world[climbablePosition.x][climbablePosition.y+MoveDirection.up.getYMove()].isEmpty()) {
						//Mid steps of climbing
						finalPosition = top;
					} else if (m_world[climbablePosition.x][climbablePosition.y].isEmpty()) {
						//Top of the climbing
						finalPosition = climbablePosition;
						body.setIsClimbing(false);
					} else if (m_world[climbablePosition.x][climbablePosition.y].isExit()) {
						//Top of the climbing is exit
						m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.GET_OUT.getYourReward());
						isArrived=true;
						body.setPosition(finalPosition);
						body.setIsClimbing(false);
						return;
					} else {
						m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
						return;
					}
				} else {
					m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
					return;
				}
				if (isInWorldDimensions(finalPosition)) {
					body.setPosition(finalPosition);
					m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.Living.getYourReward());
				} else {
					m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
				}
			}
		}
	}
	
	/*
	 * Function that moves the lemming
	 * if he can jump in his direction
	 * or if he is jumping, carry on or land safely
	 */
	public void jump (Body body){
		if (body != null) {
			Point position = body.getPosition();
			if (position == null) {
				position = new Point (0,0);
			}
			MoveDirection direction = body.getDirection();
			if (direction == null) {
				direction = MoveDirection.down;
			}
			Point jumpablePosition = new Point(position.x + direction.getXMove(), position.y + direction.getYMove());
			Point finalPosition = position;
			ActionEnum action = ActionEnum.JUMP;
			
			if (position != null & jumpablePosition != null) {
				if (!isInWorldDimensions(jumpablePosition)) {
					m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
					return;
				} else if (!body.isJumping() && 
					isInWorldDimensions(new Point(jumpablePosition.x, jumpablePosition.y+MoveDirection.down.getYMove())) &&
					m_world[jumpablePosition.x][jumpablePosition.y+MoveDirection.down.getYMove()].isEmpty() &&
					isInWorldDimensions(new Point(jumpablePosition.x + direction.getXMove(), jumpablePosition.y)) &&
					m_world[jumpablePosition.x + direction.getXMove()][jumpablePosition.y].isEmpty()) {
					//Start of the jump
					finalPosition = new Point(jumpablePosition.x, jumpablePosition.y + MoveDirection.down.getYMove());
					body.setIsJumping(true);
				} else if (body.isJumping()) {
					if (position.y+MoveDirection.down.getYMove() >= m_height) {
						//Killing Lemming and its body
						m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.KILL_HIMSELF.getYourReward());
						return;
					} else if (m_world[position.x][position.y+MoveDirection.down.getYMove()].isEmpty()) {
						//Mid steps of jump
						finalPosition = new Point(position.x, position.y + MoveDirection.down.getYMove());
					} else if (!m_world[position.x][position.y+MoveDirection.down.getYMove()].isEmpty()) {
						//End of the jump
						finalPosition = new Point(position.x, position.y + MoveDirection.down.getYMove());
						body.setIsJumping(false);
					}  else if (m_world[position.x][position.y+MoveDirection.down.getYMove()].isExit()) {
						//End of the jump is exit
						m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.GET_OUT.getYourReward());
						isArrived=true;
						body.setPosition(new Point(position.x, position.y + MoveDirection.down.getYMove()));
						body.setIsJumping(false);
						return;
					} else {
						m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
						return;
					}
				} else {
					m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
					return;
				}
				
				if (isInWorldDimensions(finalPosition)) {
					body.setPosition(finalPosition);
					m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.Living.getYourReward());
				} else {
					m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
				}
			}
		}
	}
	
	public boolean isInWorldDimensions(Point position) {
		return (position.x >= 0 && position.x < m_width &&
				position.y >= 0 && position.y < m_height);
	}
	
	public QTable getQTable() {
		return this.m_qtable;
	}
	
}
