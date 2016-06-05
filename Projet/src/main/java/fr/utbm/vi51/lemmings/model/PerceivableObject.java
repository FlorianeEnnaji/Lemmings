package fr.utbm.vi51.lemmings.model;

import java.awt.Point;
import java.io.Serializable;

/**
 * PerceivableObject
 */
public class PerceivableObject implements Serializable {
	
	private static final long serialVersionUID = -7641509704551775448L;
	
	private Point coordOfObject;
	private final boolean is_diggable;
	private final boolean is_empty;
	private final boolean is_climbable;
	private final boolean is_entry;
	private final boolean is_exit;
	
	/**
	 * Constructor
	 * @param coord the coordinates of the perceivable object
	 * @param dig true if the perceivable object is diggable, false otherwise
	 * @param empty true if the perceivable object is empty, false otherwise
	 * @param climb true if the perceivable object is climbable, false otherwise
	 * @param entry true if the perceivable object is the entry, false otherwise
	 * @param exit true if the perceivable object is the exit, false otherwise
	 */
	public PerceivableObject(Point coord, boolean dig, boolean empty, boolean climb, boolean entry, boolean exit){
		this.coordOfObject = coord;
		this.is_diggable = dig;
		this.is_empty = empty;
		this.is_climbable = climb;
		this.is_entry = entry;
		this.is_exit = exit;
	}
	
	/**
	 * @return the x-coordinate of the object
	 */
	public int getX(){
		return this.coordOfObject.x;
	}
	
	/**
	 * @return the x-coordinate of the object
	 */
	public int getY(){
		return this.coordOfObject.y;
	}
	
	/**
	 * @return true if the object is diggable, false otherwise
	 */
	public boolean isDiggable(){
		return this.is_diggable;
	}
	/**
	 * @return true if the object is empty, false otherwise
	 */
	public boolean isEmpty(){
		return this.is_empty;
	}
	
	/**
	 * @return true if the object is climbable, false otherwise
	 */
	public boolean isClimbable(){
		return this.is_climbable;
	}
	
	/**
	 * @return true if the object is the entry, false otherwise
	 */
	public boolean isEntry(){
		return this.is_entry;
	}
	
	/**
	 * @return true if the object is the exit, false otherwise
	 */
	public boolean isExit(){
		return this.is_exit;
	}
	
	/**
	 * Override boolean equals(Object o)
	 * @param pObject
	 * @return true if the two PerceivableObject are the same, false otherwise
	 */
	public boolean equals(PerceivableObject pObject){
	    if(this == pObject) 
	    	return true;
	    if(pObject == null || !(pObject instanceof PerceivableObject)) 
	    	return false;
	    return ( this.coordOfObject == pObject.coordOfObject
	    		&& this.is_diggable == pObject.is_diggable
	    		&& this.is_empty == pObject.is_empty
	    		&& this.is_climbable == pObject.is_climbable
	    		&& this.is_entry == pObject.is_entry
	    		&& this.is_exit == pObject.is_exit);
    }
		
}
