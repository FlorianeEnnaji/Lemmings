package fr.utbm.vi51.lemmings.utils;

/**
 * @author antonin.waltz@utbm.fr, floriane.ennaji@utbm.fr, lucille.gomez@utbm.fr, romain.thibaud@utbm.fr
 * @brief Enum of all possible actions
 */
public enum ActionEnum {
	
	/**Moves the Lemming can make*/
	WALK_EAST("walk",MoveDirection.right,-1),
	WALK_WEST("walk",MoveDirection.left,-2),
	DIG_EAST("dig",MoveDirection.right,-2),
	DIG_SOUTH("dig",MoveDirection.down,-2),
	DIG_WEST("dig",MoveDirection.left,-3),
	CLIMB("climb",MoveDirection.up,-3),
	JUMP("jump",MoveDirection.down,-4),
	
	/**Condition of the Lemming if he makes a move*/
	KILL_HIMSELF("kill",-10),
	NOTHING("nothing",-5),
	Living("live",0),
	GET_OUT("get out",10);
	

	private final String actionName;
	private final MoveDirection direction;
	private final float reward;
	
	/**
	 * @brief Constructor of ActionEnum
	 * @param name the name of the action
	 * @param dir the direction of the action
	 * @param rew the reward of the action
	 */
	ActionEnum(String name, MoveDirection dir, float rew){
		this.actionName=name;
		this.direction=dir;
		this.reward=rew;
	}
	
	/**
	 * @brief Constructor of ActionEnum
	 * @param name the name of the action
	 * @param rew the reward of the action
	 */
	ActionEnum(String name,float rew){
		this.actionName=name;
		this.direction=null;
		this.reward=rew;
	}
	
	/**
	 * @brief Getter
	 * @return the name of the current action
	 */
	public String getName(){return this.actionName;}
	
	/**
	 * @brief Getter
	 * @return the direction of the current action
	 */
	public MoveDirection getDir(){return this.direction;}
	
	/**
	 * @brief Getter
	 * @return the reward of the current action
	 */
	public float getYourReward(){return this.reward;}
	
}
