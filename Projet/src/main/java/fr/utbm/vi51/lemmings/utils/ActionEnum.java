package fr.utbm.vi51.lemmings.utils;

/**
 * Enumeration of possible actions
 */
public enum ActionEnum {
	
	WALK_EAST("walk",MoveDirection.right,-1),
	WALK_WEST("walk",MoveDirection.left,-2),
	DIG_EAST("dig",MoveDirection.right,-2),
	DIG_SOUTH("dig",MoveDirection.down,-2),
	DIG_WEST("dig",MoveDirection.left,-3),
	CLIMB("climb",MoveDirection.up,-3),
	JUMP("jump",MoveDirection.down,-4),
	KILL_HIMSELF("kill",-10),
	NOTHING("nothing",-5),
	Living("live",0),
	GET_OUT("get out",10);
	

	private final String actionName;
	private final MoveDirection direction;
	private final int reward;
	
	/**
	 * Constructor of ActionEnum
	 * @param name the name of the action
	 * @param dir the direction of the action
	 * @param rew the reward of the action
	 */
	ActionEnum(String name, MoveDirection dir, int rew){
		this.actionName=name;
		this.direction=dir;
		this.reward=rew;
	}
	
	/**
	 * Constructor of ActionEnum
	 * @param name the name of the action
	 * @param rew the reward of the action
	 */
	ActionEnum(String name,int rew){
		this.actionName=name;
		this.direction=null;
		this.reward=rew;
	}
	
	/**
	 * @return the name of the current action
	 */
	public String getName(){return this.actionName;}
	
	/**
	 * @return the direction of the current action
	 */
	public MoveDirection getDir(){return this.direction;}
	
	/**
	 * @return the reward of the current action
	 */
	public int getYourReward(){return this.reward;}
	
}
