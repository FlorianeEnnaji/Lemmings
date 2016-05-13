package fr.utbm.vi51.lemmings.utils.enums;

public enum ActionEnum {
	
	//Action Enumeration
	WALK_EAST("walk","east",-1),
	WALK_SOUTH("walk","south",-1),
	WALK_WEST("walk","west",-2),
	WALK_NORTH("walk","north",-2),
	DIG_EAST("dig","east",-2),
	DIG_SOUTH("dig","south",-2),
	DIG_WEST("dig","west",-3),
	CLIMB("climb",-3),
	JUMP("jump",-4),
	KILL_HIMSELF("kill",-10);

	//Att
	private final String actionName;
	private final String direction;
	private final int reward;
	
	//Constructors
	ActionEnum(String name, String dir, int rew){
		this.actionName=name;
		this.direction=dir;
		this.reward=rew;
	}
	
	ActionEnum(String name,int rew){
		this.actionName=name;
		this.direction="";
		this.reward=rew;
	}
	
	//get method
	public String getName(){return this.actionName;}
	public String getDir(){return this.direction;}
	public int getYourReward(){return this.reward;}
	
}
