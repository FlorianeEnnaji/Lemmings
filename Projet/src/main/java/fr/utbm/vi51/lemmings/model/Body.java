package fr.utbm.vi51.lemmings.model;

import java.awt.Point;
import java.util.List;

import fr.utbm.vi51.lemmings.utils.enums.MoveDirection;

/**
 * Body
 */
public abstract class Body extends DynamicObject {

	private final Environment environment;
	private MoveDirection direction;
	private Point position;
	private boolean isClimbing;
	private boolean isJumping;
	private boolean isFalling;
	private int isFallingHeight;
	
	/**
	 * Constructor
	 * @param environment the current environment
	 * @param dir the direction of the body
	 * @param position the position of the body
	 */
	public Body (Environment environment, MoveDirection dir, Point position){
		this.environment = environment;
		this.direction = dir;
		this.position = position;
		this.isClimbing = false;
		this.isJumping = false;
		this.isFalling = false;
		this.isFallingHeight = 0;
	}
	
	/**
	 * @return the environment
	 */
	public Environment getEnvironment(){
		return this.environment;
	}
	/**
	 * @param dir the direction to set
	 */
	public void setDirection(MoveDirection dir) {
		this.direction = dir;
	}
	
	/**
	 * @return the current direction
	 */
	public MoveDirection getDirection() {
		return this.direction;
	}
	
	/**
	 * @param position the position to set
	 */
	public void setPosition(Point position) {
		this.position = position;
	}
	
	/**
	 * @return the current position
	 */
	public Point getPosition() {
		return this.position;
	}
	
	/**
	 * @param dir the direction the body wants to move to
	 * @param learningPhase 
	 */
	public void move(MoveDirection dir, boolean learningPhase) {
		Environment e = getEnvironment();
		e.move(this, dir, learningPhase);
	}
	
	/**
	 * @return the state of the current body
	 */
	public List<PerceivableObject> getPerception() {
		Environment e = getEnvironment();
		return e.getPerception(this);
	}
	
	/**
	 * @return true if the body is jumping, false otherwise
	 */
	public boolean isJumping() {
		return this.isJumping;
	}
	
	/**
	 * @param jump true if the body is jumping, false otherwise
	 */
	public void setIsJumping(boolean jump) {
		this.isJumping = jump;
	}
	
	/**
	 * @return true if the body is climbing, false otherwise
	 */
	public boolean isClimbing() {
		return this.isClimbing;
	}
	
	/**
	 * @param climb true if the body is climbing, false otherwise
	 */
	public void setIsClimbing(boolean climb) {
		this.isClimbing = climb;
	}

	/**
	 * @return true if the body is falling, false otherwise
	 */
	public boolean isFalling() {
		return this.isFalling;
	}

	/**
	 * @param fall true if the body is falling, false otherwise
	 */
	public void setIsFalling(boolean fall) {
		this.isFalling = fall;
		if (fall) {
			this.isFallingHeight = 1;
		} else {
			this.isFallingHeight = 0;
		}
	}

	/**
	 * @return the number of pixel of the fall height
	 */
	public int getFallingHeight() {
		return this.isFallingHeight;
	}

	/**
	 * @param height the number of pixel of the current fall
	 */
	public void setFallingHeight(int height) {
		this.isFallingHeight = height;
	}
}
