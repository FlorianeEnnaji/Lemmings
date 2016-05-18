package fr.utbm.vi51.lemmings.model;

import java.awt.Point;

public class PerceivableObject {
	
	private Point m_coord;
	private final boolean is_diggable;
	private final boolean is_empty;
	private final boolean is_climbable;
	private final boolean is_entry;
	private final boolean is_exit;
	
	public PerceivableObject(Point coord, boolean dig, boolean empty, boolean climb, boolean entry, boolean exit){
		m_coord = coord;
		is_diggable = dig;
		is_empty = empty;
		is_climbable = climb;
		is_entry = entry;
		is_exit = exit;
	}
	
	public int getX(){
		return m_coord.x;
	}
	
	public int getY(){
		return m_coord.y;
	}
	
	public boolean isDiggable(){
		return this.is_diggable;
	}
	public boolean isEmpty(){
		return this.is_empty;
	}
	
	public boolean isClimbable(){
		return this.is_climbable;
	}
	
	public boolean isEntry(){
		return this.is_entry;
	}
	
	public boolean isExit(){
		return this.is_exit;
	}
		
}
