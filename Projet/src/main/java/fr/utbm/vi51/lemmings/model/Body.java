package fr.utbm.vi51.lemmings.model;

import java.awt.Point;

import fr.utbm.vi51.lemmings.utils.enums.MoveDirection;

public abstract class Body extends DynamicObject {

	private MoveDirection m_direction;
	private Point m_position;
	
	public Body (MoveDirection dir, Point position){
		this.m_direction = dir;
		this.m_position = position;
	}
	
	public void setDirection(MoveDirection dir) {
		this.m_direction = dir;
	}
	
	public MoveDirection getDirection() {
		return this.m_direction;
	}
	public void setPosition(Point position) {
		this.m_position = position;
	}
	
	public Point getPosition() {
		return this.m_position;
	}
}
