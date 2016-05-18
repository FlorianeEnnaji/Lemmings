package fr.utbm.vi51.lemmings.model;

import fr.utbm.vi51.lemmings.utils.enums.MoveDirection;

public abstract class Body extends DynamicObject {

	private MoveDirection m_direction;
	
	public Body (MoveDirection dir){
		this.m_direction = dir;
	}
	
	void setDirection(MoveDirection dir) {
		this.m_direction = dir;
	}
	
	public MoveDirection getDirection() {
		return this.m_direction;
	}
}
