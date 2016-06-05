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

/**
 * Environment is the class that contains all elements of the world 
 */
public class Environment {

	/* Matrix of pixels */
	private WorldPixel[][] world;
	
	/* Height and width of the world */
	private int worldHeight = 0;
	private int worldWidth = 0;
	
	/* Color of pixels of the world */
	private Color lightBrown = new Color(205,133,63);
	private Color yellow = new Color(255,218,57);
	private Color brown = new Color(153,102,51);
	
	/* Entry and Exit **/
	private Point entry;
	private boolean isArrived=false;
	
	/* QTable **/
	private QTable qtable = new QTable();
	
	/* List of agent bodies */
	public final Map<UUID,LemmingBody> agentBodies = new TreeMap<UUID,LemmingBody>();
	
	/** 
	 * Default Constructor
	 */
	public Environment(){
		this.world = new WorldPixel[this.worldWidth][this.worldHeight];	
	}
	
	/**
	 * @param image the image representing the environment without the Lemmings
	 */
	public Environment(BufferedImage image){
		initializeWorld(image);
		printEnvironment();
	}
	
	/**
	 * World initializing
	 * 
	 * @param image the image representing the environment without the Lemmings
	 */
	private void initializeWorld (BufferedImage image){
		int id = 0;
		this.worldWidth = image.getWidth();
		this.worldHeight = image.getHeight();
		this.world = new WorldPixel[this.worldWidth][this.worldHeight];
		
		for (int yPixel = 0; yPixel < this.worldHeight; yPixel++)
		{
			for (int xPixel = 0; xPixel < this.worldWidth; xPixel++)
		    {
		        int color = image.getRGB(xPixel, yPixel);
		        /* int alpha = (color >> 24) & 0xFF;
		        int red =   (color >> 16) & 0xFF;
		        int green = (color >>  8) & 0xFF;
		        int blue =  (color      ) & 0xFF;
		        System.out.println(alpha + " " + red + " " + green + " " + blue); */
		        if (color==Color.BLACK.getRGB()) {
		            this.world[xPixel][yPixel] = new WorldPixel("empty", id);
		        } else if (color==this.lightBrown.getRGB()) {
		            this.world[xPixel][yPixel] = new WorldPixel("dig", id);
		        } else if (color==this.yellow.getRGB()) {
		            this.world[xPixel][yPixel] = new WorldPixel("entry", id);
		            this.entry = new Point(xPixel, yPixel);
		        } else if (color==Color.WHITE.getRGB()) {
		            this.world[xPixel][yPixel] = new WorldPixel("exit", id);
		        } else if (color==this.brown.getRGB()) {
		            this.world[xPixel][yPixel] = new WorldPixel("ground", id);
		        }
		        id++;
		    }
		}
	}
	
	/**
	 * Print the environment
	 */
	public void printEnvironment(){
		for (int i = 0; i < this.worldHeight; i++)
		{
			System.out.print(i);
			for (int j = 0; j < this.worldWidth; j++)
		    {
				WorldPixel pixel = this.world[j][i];
				
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
	
	/**
	 * @return the width of the environment
	 */
	public int getWidth() {
		return this.worldWidth;
	}
	
	/**
	 * @return the height of the environment
	 */
	public int getHeight() {
		return this.worldHeight;
	}
	
	/**
	 * @return true is the Lemmings is arrived, false otherwise
	 */
	public boolean isArrived(){
		return this.isArrived;
	}
	
	/**
	 * @return bodies of an agent
	 */
	public Map<UUID,LemmingBody> getAgentBodies(){
		return this.agentBodies;
	}
	
	/**
	 * Create the body
	 */
	public void createLemming() {
		createBody();
		//TODO fusion with createBody ?
	}
	
	/**
	 * Create the body
	 */
	private void createBody(){
		LemmingBody body = new LemmingBody(this, MoveDirection.right, this.entry);
		this.agentBodies.put(new UUID(1, this.agentBodies.size()+1), body);
	}
	
	/**
	 * return the coefficients of a state
	 * 
	 * @param body the agent's body 
	 * @return the list of coefficients for the current state of a body
	 */
	public float[] getPerceptionCoef(Body body){
		List<PerceivableObject> list = new LinkedList<>();
		list = body.getPerception();
		return this.qtable.getCoef(list);
	}
		
	/** Return the perception of the body 
	 * 
	 * @param body 
	 * @return the perception of the body*/
	public List<PerceivableObject> getPerception(Body body) {
		List<PerceivableObject> list = new LinkedList<>();
		Point position = body.getPosition();
		if (position!=null) {
			/* Get all objects present in perception field */
			PerceivableObject perception;
			for (int i = position.x-1;i <= position.x + 1; i++){
				if (i >= 0 && i < this.worldWidth){
					for (int j = position.y-1;j <= position.y + 1; j++){
						if (j >= 0 && j < this.worldHeight){
							WorldPixel pixel = this.world[i][j];
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

	/**
	 * Move a body to a direction
	 * 
	 * @param body the body to move
	 * @param direction the direction of the body
	 */
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
				if (this.world[pos.x][pos.y].isEmpty()) {
					if (!this.world[pos.x][pos.y+MoveDirection.down.getYMove()].isEmpty()) {
						
						//Lemming can walk in his direction
						body.setPosition(new Point(pos.x, pos.y));
						this.qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.Living.getYourReward());
					
					} else {
						//Lemming is falling
						Point down = new Point (pos.x, pos.y+MoveDirection.down.getYMove());
						
						while(isInWorldDimensions(down) && 
								this.world[down.x][down.y].isEmpty() && 
								(down.y - pos.y) < 5){
							down = new Point (down.x, down.y+MoveDirection.down.getYMove());
						}
						
						if (!isInWorldDimensions(down) || (down.y - pos.y) < 5) {
							//He dies
							this.qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.KILL_HIMSELF.getYourReward());
						} else {
							body.setPosition(down);
							if (this.world[down.x][down.y].isExit()) {
								//He landed on exit
								this.qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.GET_OUT.getYourReward());
								this.isArrived = true;
							} else {
								//He landed successfully
								this.qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.Living.getYourReward());
							}
						}
						
					}
				} else if (this.world[pos.x][pos.y].isExit()) {
					//Lemming has arrived to exit!
					this.qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.GET_OUT.getYourReward());
					this.isArrived=true;
				} else {
					this.qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
				}
			} else {
				this.qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
			}
		}
	}
	
	/**
	 * Function that moves the lemming's body and change diggable pixel
	 * if he can dig the pixel in his direction,
	 * or if he is digging, carry on or moves in his direction
	 * 
	 * @param body the body to move
	 * @param direction the direction of the lemmings
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
					this.qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
					return;
				} else if (this.world[diggablePosition.x][diggablePosition.y].isDiggable()) {
					//Digging
					this.world[diggablePosition.x][diggablePosition.y].setEmpty();
					finalPosition = diggablePosition;
				} else if (this.world[diggablePosition.x][diggablePosition.y].isDiggable()) {
					//Dig through exit
					this.qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.GET_OUT.getYourReward());
					this.isArrived=true;
					body.setPosition(diggablePosition);
					body.setIsClimbing(false);
					return;
				} else {
					this.qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
					return;
				}
				
				if (isInWorldDimensions(finalPosition)) {
					body.setPosition(finalPosition);
					this.qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.Living.getYourReward());
				} else {
					this.qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+ActionEnum.NOTHING.getYourReward());
				}
				
			}
		}
	}
	
	/**
	 * Function that moves the lemmings if he can climb the pixel in his direction,
	 * or if he is climbing, carry on or land safely
	 * 
	 * @param body the body to move
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
						
					} else if (this.world[climbablePosition.x][climbablePosition.y].isClimbable() && this.world[onTop.x][onTop.y].isEmpty()) {
						
						//He can climb
						reward = ActionEnum.Living.getYourReward();
						finalPosition = onTop;
						if (this.world[nextClimbablePosition.x][nextClimbablePosition.y].isEmpty()){
							//He finished climbing
							finalPosition = nextClimbablePosition;
							landed = true;
						} else if (this.world[nextClimbablePosition.x][nextClimbablePosition.y].isExit()){
							//He lands on exit
							finalPosition = nextClimbablePosition;
							reward = ActionEnum.GET_OUT.getYourReward();
							landed = true;
						}
						
					} else if (this.world[climbablePosition.x][climbablePosition.y].isClimbable() && this.world[onTop.x][onTop.y].isExit()) {
						
						//He climbs through exit!
						finalPosition = onTop;
						reward = ActionEnum.GET_OUT.getYourReward();
						landed = true;
						this.isArrived = true;
						
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
			this.qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+reward);
		}
	}
	
	/**
	 * Function that moves the lemming if he can jump in his direction 
	 * or if he is jumping, carry on or land safely
	 * 
	 * @param body the body to move
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
							this.world[jumpablePosition.x][jumpablePosition.y].isEmpty() && 
							this.world[onBottom.x][onBottom.y].isEmpty()) {
							finalPosition = onBottom;
						} else {
							//He can't jump
							reward = ActionEnum.NOTHING.getYourReward();
							landed = true;
						}
						
					} else if (this.world[jumpablePosition.x][jumpablePosition.y].isEmpty()) {
						//He is jumping
						finalPosition = jumpablePosition;
						body.setIsJumping(true);
					} else if (this.world[jumpablePosition.x][jumpablePosition.y].isExit()) {
						//He landed through exit
						finalPosition = jumpablePosition;
						reward = ActionEnum.GET_OUT.getYourReward();
						landed = true;
						this.isArrived = true;
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
			this.qtable.UpdateCoef(this.getPerception(body), action, action.getYourReward()+reward);
		}
	}
	
	/**
	 * Get if a position belongs to the world
	 * 
	 * @param position the position to check
	 * @return true if the position belongs to the world, false otherwise
	 */
	public boolean isInWorldDimensions(Point position) {
		return (position.x >= 0 && position.x < this.worldWidth &&
				position.y >= 0 && position.y < this.worldHeight);
	}
	
	/**
	 * @return the QTable of the current environment
	 */
	public QTable getQTable() {
		return this.qtable;
	}
	
}
