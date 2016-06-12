package fr.utbm.vi51.lemmings.model;

import java.awt.Point;
import java.beans.ConstructorProperties;
import java.io.Serializable;

/**
 * @author antonin.waltz@utbm.fr, floriane.ennaji@utbm.fr, lucille.gomez@utbm.fr, romain.thibaud@utbm.fr
 * @brief What the body can see depending on where he is
 */
public class PerceivableObject implements Serializable {
	
	private static final long serialVersionUID = -7641509704551775448L;
	
	private Point coordOfObject;
	private final boolean diggable;
	private final boolean empty;
	private final boolean climbable;
	private final boolean entry;
	private final boolean exit;
	
	/**
	 * @brief Constructor
	 * @param coord the coordinates of the perceivable object
	 * @param dig true if the perceivable object is diggable, false otherwise
	 * @param empty true if the perceivable object is empty, false otherwise
	 * @param climb true if the perceivable object is climbable, false otherwise
	 * @param entry true if the perceivable object is the entry, false otherwise
	 * @param exit true if the perceivable object is the exit, false otherwise
	 */
	@ConstructorProperties({"coordOfObject","diggable","empty","climbable","entry","exit"})
	public PerceivableObject(Point coordOfObject, boolean diggable, boolean empty, boolean climbable, boolean entry, boolean exit){
		this.coordOfObject = coordOfObject;
		this.diggable = diggable;
		this.empty = empty;
		this.climbable = climbable;
		this.entry = entry;
		this.exit = exit;
	}
	
	/**
	 * @brief Getter
	 * @return the coordinate of the object
	 */
	public Point getCoordOfObject() {
		return this.coordOfObject;
	}
	
	/**
	 * @brief Getter
	 * @return the x-coordinate of the object
	 */
	public int getX(){
		return this.coordOfObject.x;
	}
	
	/**
	 * @brief Getter
	 * @return the y-coordinate of the object
	 */
	public int getY(){
		return this.coordOfObject.y;
	}
	
	/**
	 * @brief Getter
	 * @return true if the object is diggable, false otherwise
	 */
	public boolean isDiggable(){
		return this.diggable;
	}
	
	/**
	 * @brief Getter
	 * @return true if the object is empty, false otherwise
	 */
	public boolean isEmpty(){
		return this.empty;
	}
	
	/**
	 * @brief Getter
	 * @return true if the object is climbable, false otherwise
	 */
	public boolean isClimbable(){
		return this.climbable;
	}
	
	/**
	 * @brief Getter
	 * @return true if the object is the entry, false otherwise
	 */
	public boolean isEntry(){
		return this.entry;
	}
	
	/**
	 * @brief Getter
	 * @return true if the object is the exit, false otherwise
	 */
	public boolean isExit(){
		return this.exit;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordOfObject == null) ? 0 : coordOfObject.hashCode());
		result = prime * result + (climbable ? 1231 : 1237);
		result = prime * result + (diggable ? 1231 : 1237);
		result = prime * result + (empty ? 1231 : 1237);
		result = prime * result + (entry ? 1231 : 1237);
		result = prime * result + (exit ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PerceivableObject other = (PerceivableObject) obj;
		if (coordOfObject == null) {
			if (other.coordOfObject != null)
				return false;
		} else if (!coordOfObject.equals(other.coordOfObject))
			return false;
		if (climbable != other.climbable)
			return false;
		if (diggable != other.diggable)
			return false;
		if (empty != other.empty)
			return false;
		if (entry != other.entry)
			return false;
		if (exit != other.exit)
			return false;
		return true;
	}
		
}
