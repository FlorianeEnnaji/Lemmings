package fr.utbm.vi51.lemmings.model;

import java.awt.Point;
import java.util.List;

import fr.utbm.vi51.lemmings.utils.MoveDirection;

/**
 * @author antonin.waltz@utbm.fr, floriane.ennaji@utbm.fr, lucille.gomez@utbm.fr, romain.thibaud@utbm.fr
 * @brief The physic Body of a player
 * @see fr.utbm.vi51.lemmings.model.DynamicObject
 */
public abstract class Body extends DynamicObject {

	private final Environment environment;
	private MoveDirection direction;
	private Point position;
	
	/**
	 * @brief Constructor
	 * @param environment (Environment) the current environment of the body
	 * @param dir (MoveDirection) the direction of the body
	 * @param position (Point) the position of the body
	 */
	public Body (Environment environment, MoveDirection dir, Point position){
		this.environment = environment;
		this.direction = dir;
		this.position = position;
	}
	
	/**
	 * @brief Getter
	 * @return environment (Environment) the environment the body's in
	 */
	public Environment getEnvironment(){
		return this.environment;
	}
	
	/**
	 * @brief Setter
	 * @param dir (MoveDirection) the direction to set
	 */
	public void setDirection(MoveDirection dir) {
		this.direction = dir;
	}
	
	/**
	 * @brief Getter
	 * @return direction (MoveDirection) the current direction
	 */
	public MoveDirection getDirection() {
		return this.direction;
	}
	
	/**
	 * @brief Setter that also notifies the Environment the body just moved
	 * @param position (Point) the position to set
	 */
	public void setPosition(Point position) {
		System.out.println("MOVED FROM " + this.position.x + "-" + this.position.y + " TO " + position.x + "-" + position.y);
		this.position = position;
		getEnvironment().justMovedBody(this);
	}
	
	/**
	 * @brief Getter
	 * @return position (Point) the current position
	 */
	public Point getPosition() {
		return this.position;
	}
	
	/**
	 * @brief Asks the environment to move
	 * @param dir (MoveDirection) the direction the body wants to move to
	 * @param learningPhase (boolean)
	 */
	public void move(MoveDirection dir, boolean learningPhase) {
		Environment e = getEnvironment();
		e.move(this, dir, learningPhase);
	}
	
	/**
	 * @brief Gets the body's perception in the environment
	 * @return the state of the current body (List<PerceivableObject>)
	 */
	public List<PerceivableObject> getPerception() {
		Environment e = getEnvironment();
		return e.getPerception(this);
	}
	
}
