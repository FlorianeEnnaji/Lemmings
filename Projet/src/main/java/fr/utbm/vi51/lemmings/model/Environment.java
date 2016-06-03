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
		agentBodies.put(new UUID(1, agentBodies.size()+1), body);
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
			if (isInWorldDimensions(pos) && isInWorldDimensions(new Point(pos.x, pos.y+MoveDirection.down.getYMove()))) {
				if (m_world[pos.x][pos.y].isEmpty()) {
					if (!m_world[pos.x][pos.y+MoveDirection.down.getYMove()].isEmpty()) {
						
						//Lemming can walk in his direction
						body.setPosition(new Point(pos.x, pos.y));
						m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.Living.getYourReward());
					
					} else {
						//Lemming is falling
						
						Point down = new Point (pos.x, pos.y+MoveDirection.down.getYMove());
						
						while(isInWorldDimensions(down) && 
								m_world[down.x][down.y].isEmpty() && 
								(down.y - pos.y) < 5){
							down = new Point (down.x, down.y+MoveDirection.down.getYMove());
						}
						
						if (!isInWorldDimensions(down) || (down.y - pos.y) < 5) {
							//He dies
							m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.KILL_HIMSELF.getYourReward());
						} else {
							body.setPosition(down);
							if (m_world[down.x][down.y].isExit()) {
								//He landed on exit
								m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.GET_OUT.getYourReward());
								isArrived = true;
							} else {
								//He landed successfully
								m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.Living.getYourReward());
							}
						}
						
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
				direction = MoveDirection.right;
			}
			ActionEnum action = ActionEnum.CLIMB;
			Point climbablePosition = new Point(position.x + direction.getXMove(), position.y + direction.getYMove());
			Point nextClimbablePosition = new Point(climbablePosition.x, climbablePosition.y + action.getDir().getYMove());
			Point onTop = new Point(position.x, position.y + MoveDirection.up.getYMove());
			Point finalPosition = position;
			int reward = 0;
			
			boolean landed = false;
			while(!landed) {
				if (position != null && climbablePosition != null) {
					if (!isInWorldDimensions(climbablePosition)) {
						
						//If he wants to climb outside the world
						if (position.y - climbablePosition.y < 2) {
							//He doesn't climb
							reward = ActionEnum.NOTHING.getYourReward();
						} else {
							//He falls so die
							reward = ActionEnum.KILL_HIMSELF.getYourReward();
						}
						landed = true;
						
					} else if (!isInWorldDimensions(onTop)) {
						
						//If he wants to climb but is already too high
						if (onTop.y - position.y < 2) {
							//He doesn't climb
							reward = ActionEnum.NOTHING.getYourReward();
						} else {
							//He falls so die
							reward = ActionEnum.KILL_HIMSELF.getYourReward();
						}
						landed = true;
						
					} else if (m_world[climbablePosition.x][climbablePosition.y].isClimbable() && m_world[onTop.x][onTop.y].isEmpty()) {
						
						//He can climb
						reward = ActionEnum.Living.getYourReward();
						finalPosition = onTop;
						if (m_world[nextClimbablePosition.x][nextClimbablePosition.y].isEmpty()){
							//He finished climbing
							finalPosition = nextClimbablePosition;
							landed = true;
						} else if (m_world[nextClimbablePosition.x][nextClimbablePosition.y].isExit()){
							//He lands on exit
							finalPosition = nextClimbablePosition;
							reward = ActionEnum.GET_OUT.getYourReward();
							landed = true;
						}
						
					} else if (m_world[climbablePosition.x][climbablePosition.y].isClimbable() && m_world[onTop.x][onTop.y].isExit()) {
						
						//He climbs through exit!
						finalPosition = onTop;
						reward = ActionEnum.GET_OUT.getYourReward();
						landed = true;
						isArrived = true;
						
					} else {
						
						//He can't climb
						if (position.y - climbablePosition.y < 2) {
							//He doesn't climb
							reward = ActionEnum.NOTHING.getYourReward();
						} else {
							//He falls so die
							reward = ActionEnum.KILL_HIMSELF.getYourReward();
						}
						landed = true;
						
					}
					
					//Preparing vars for next iteration
					onTop = new Point(finalPosition.x, finalPosition.y + MoveDirection.up.getYMove());
					climbablePosition = new Point(finalPosition.x + direction.getXMove(), finalPosition.y + direction.getYMove());
					nextClimbablePosition = new Point(climbablePosition.x, climbablePosition.y + action.getDir().getYMove());
					
				} else {
					//There is a null position
					reward = ActionEnum.NOTHING.getYourReward();
					landed = true;
				}
			}
			
			body.setPosition(finalPosition);
			m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+reward);
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
				direction = MoveDirection.right;
			}
			Point jumpablePosition = new Point(position.x + direction.getXMove(), position.y + direction.getYMove());
			Point finalPosition = position;
			ActionEnum action = ActionEnum.JUMP;
			int reward = 0;
			
			boolean landed = false;
			
			while(!landed) {
				if (position != null & jumpablePosition != null) {
					if (!isInWorldDimensions(jumpablePosition)) {
						
						//If he wants to jump outside the world
						if (position.y == jumpablePosition.y) {
							//He starts a jump in one of his side
							reward = ActionEnum.NOTHING.getYourReward();
						} else {
							//He falls so die
							reward = ActionEnum.KILL_HIMSELF.getYourReward();
						}
						landed = true;
						
					} else if (position.y == jumpablePosition.y) {
						
						//Jump is starting
						reward = ActionEnum.Living.getYourReward();
						Point onBottom = new Point(jumpablePosition.x, jumpablePosition.y + MoveDirection.down.getYMove());
						
						if (isInWorldDimensions(onBottom) &&
							m_world[jumpablePosition.x][jumpablePosition.y].isEmpty() && 
							m_world[onBottom.x][onBottom.y].isEmpty()) {
							finalPosition = onBottom;
						} else {
							//He can't jump
							reward = ActionEnum.NOTHING.getYourReward();
							landed = true;
						}
						
					} else if (m_world[jumpablePosition.x][jumpablePosition.y].isEmpty()) {
						//He is jumping
						finalPosition = jumpablePosition;
						body.setIsJumping(true);
					} else if (m_world[jumpablePosition.x][jumpablePosition.y].isExit()) {
						//He landed through exit
						finalPosition = jumpablePosition;
						reward = ActionEnum.GET_OUT.getYourReward();
						landed = true;
						isArrived = true;
					} else {
						//He lands
						finalPosition = jumpablePosition;
						reward = ActionEnum.Living.getYourReward();
						landed = true;
					}
					
					//Preparing var for next iteration
					jumpablePosition = new Point(finalPosition.x, finalPosition.y + MoveDirection.down.getYMove());
				} else {
					//There is a null position
					reward = ActionEnum.NOTHING.getYourReward();
					landed = true;
				}
			}
			
			body.setPosition(finalPosition);
			m_qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+reward);
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
