package fr.utbm.vi51.lemmings.utils.enums;

public enum MoveDirection {
	up(0,-1), 
	down(0,1), 
	left(-1,0), 
	right(0,1);
	
	private final int x_move;
	private final int y_move;
	
	/** Constructor */
	MoveDirection(int x, int y){
		this.x_move = x;
		this.y_move = y;
	}
	
	/** Getters */
	public int getXMove(){return this.x_move;}
	public int getYMove(){return this.y_move;}
}
