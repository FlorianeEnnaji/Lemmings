package fr.utbm.vi51.lemmings.utils.enums;

public enum ActionEnum {
	
	//Action Enumeration
	WALK_EAST("walk",MoveDirection.right,-1),
	WALK_SOUTH("walk",MoveDirection.down,-1),
	WALK_WEST("walk",MoveDirection.left,-2),
	DIG_EAST("dig",MoveDirection.right,-2),
	DIG_SOUTH("dig",MoveDirection.down,-2),
	DIG_WEST("dig",MoveDirection.left,-3),
	CLIMB("climb",MoveDirection.up,-3),
	JUMP("jump",MoveDirection.down,-4),
	KILL_HIMSELF("kill",-10);

	//Att
	private final String actionName;
	private final MoveDirection direction;
	private final int reward;
	
	//Constructors
	ActionEnum(String name, MoveDirection dir, int rew){
		this.actionName=name;
		this.direction=dir;
		this.reward=rew;
	}
	
	ActionEnum(String name,int rew){
		this.actionName=name;
		this.direction=null;
		this.reward=rew;
	}
	
	//get method
	public String getName(){return this.actionName;}
	public MoveDirection getDir(){return this.direction;}
	public int getYourReward(){return this.reward;}
	
}
