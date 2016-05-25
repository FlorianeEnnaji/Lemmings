package fr.utbm.vi51.lemmings.model;

import java.awt.Point;
import java.util.List;

import fr.utbm.vi51.lemmings.utils.enums.MoveDirection;

public abstract class Body extends DynamicObject {

	private final Environment m_environment;
	private MoveDirection m_direction;
	private Point m_position;
	private boolean m_isClimbing;
	private boolean m_isJumping;
	
	public Body (Environment environment, MoveDirection dir, Point position){
		this.m_environment = environment;
		this.m_direction = dir;
		this.m_position = position;
		this.m_isClimbing = false;
		this.m_isJumping = false;
	}
	
	public Environment getEnvironment(){
		return this.m_environment;
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
	
	public void move(MoveDirection direction) {
		Environment e = getEnvironment();
		e.move(this, direction);
	}
	
	public List<PerceivableObject> getPerception() {
		Environment e = getEnvironment();
		return e.getPerception(this);
	}
	
	public boolean isJumping() {
		return this.m_isJumping;
	}
	
	public void setIsJumping(boolean jump) {
		this.m_isJumping = jump;
	}
	
	public boolean isClimbing() {
		return this.m_isClimbing;
	}
	
	public void setIsClimbing(boolean climb) {
		this.m_isClimbing = climb;
	}
}
