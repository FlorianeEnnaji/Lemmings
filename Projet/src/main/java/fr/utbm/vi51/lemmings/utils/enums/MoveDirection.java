package fr.utbm.vi51.lemmings.utils.enums;

/**
 * Enumeration of move directions
 */
public enum MoveDirection {
	
	up(0,-1), 
	down(0,1), 
	left(-1,0), 
	right(1,0);
	
	private final int x_move;
	private final int y_move;
	
	/** 
	 * Constructor
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	MoveDirection(int x, int y){
		this.x_move = x;
		this.y_move = y;
	}
	
	/**
	 * @return the x-coordinate of the move direction
	 */
	public int getXMove(){return this.x_move;}
	
	/**
	 * @return the y-coordinate of the move direction
	 */
	public int getYMove(){return this.y_move;}
}
