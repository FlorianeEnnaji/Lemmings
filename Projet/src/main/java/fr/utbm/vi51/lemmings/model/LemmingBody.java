package fr.utbm.vi51.lemmings.model;

import java.awt.Point;

import java.util.List;

import fr.utbm.vi51.lemmings.agent.ActionInfluence;
import fr.utbm.vi51.lemmings.agent.Influence;
import fr.utbm.vi51.lemmings.agent.PerceptionEvent;
import fr.utbm.vi51.lemmings.utils.ActionEnum;
import fr.utbm.vi51.lemmings.utils.MoveDirection;

/**
 * @author antonin.waltz@utbm.fr, floriane.ennaji@utbm.fr, lucille.gomez@utbm.fr, romain.thibaud@utbm.fr
 * @brief Represents the Lemming's body
 * @see fr.utbm.vi51.lemmings.model.Body
 */
public class LemmingBody extends Body {

	private static final long serialVersionUID = -4636419559142339321L;
	
	private List<PerceivableObject> perception;
	
	/**
	 * @brief Constructor used to learn
	 * @param environment the environment the body will be placed in
	 * @param direction the direction of the body
	 * @param position the position of the body
	 */
	public LemmingBody(Environment environment, MoveDirection direction, Point position) {
		super(environment, direction, position);
		LearningRoutine();
		setPerception(null);
		learn();		
	}

	/**
	 * @brief Constructor used to play
	 * @param environment the environment the body will be placed in
	 * @param direction the direction of the body
	 * @param position the position of the body
	 */
	public LemmingBody(Environment environment, MoveDirection direction, Point position, int a) {
		super(environment, direction, position);
	}
	
	/**
	 * @brief performs an action from an influence -- playing phase
	 * @param influence (Influence) the influence corresponding to the action we want to perform
	 */
	public void influenceGame(Influence influence) {
		if (influence != null && (influence instanceof ActionInfluence)) {
			ActionEnum action = ((ActionInfluence) influence).getType();
			System.out.println(action);
			if(action!=null){
				switch(action){
				case WALK_EAST : 
					walk(action.getDir(), false);
					break;
				case WALK_WEST :
					walk(action.getDir(), false);
					break;
				case DIG_EAST : 
					dig(action.getDir(), false);
					break;				
				case DIG_WEST : 
					dig(action.getDir(), false);
					break;
				case DIG_SOUTH : 
					dig(action.getDir(), false);
					break;
				case CLIMB : 
					climb(false);
					break;
				case JUMP : 
					jump(false);
					break;
				default : break;
				}
			}
		}
	}

	/**
	 * @brief performs an action from an influence -- learning phase
	 * @param influence (Influence) the influence corresponding to the action we want to perform
	 */
	public void influence(Influence influence) {
		if(influence!=null){
			if(influence instanceof ActionInfluence){
				switch(((ActionInfluence) influence).getType()){
				case WALK_EAST : 
					walk(((ActionInfluence) influence).getType().getDir(), true);
					break;
				case WALK_WEST :
					walk(((ActionInfluence) influence).getType().getDir(), true);
					break;
				case DIG_EAST : 
					dig(((ActionInfluence) influence).getType().getDir(), true);
					break;				
				case DIG_WEST : 
					dig(((ActionInfluence) influence).getType().getDir(), true);
					break;
				case DIG_SOUTH : 
					dig(((ActionInfluence) influence).getType().getDir(), true);
					break;
				case CLIMB : 
					climb(true);
					break;
				case JUMP : 
					jump(true);
					break;
				default : break;
				}
			}
		}
	}
	
	/**
	 * @brief Calls the environment move function
	 * @param dir (MoveDirection) the direction in which the body will walk
	 * @param learningPhase (boolean)
	 */
	public void walk(MoveDirection dir, boolean learningPhase){
		move(dir, learningPhase);
	}
	
	/**
	 * @brief Calls the environment dig function
	 * @param dir (MoveDirection) the direction in which the body will dig
	 * @param learningPhase (boolean)
	 */
	public void dig (MoveDirection dir, boolean learningPhase){
		Environment e = getEnvironment();
		e.dig(this, dir, learningPhase);	
	}
	
	/**
	 * @brief Calls the environment climb function
	 * @param learningPhase (boolean)
	 */
	public void climb (boolean learningPhase){
		Environment e = getEnvironment();
		e.climb(this, learningPhase);
	}
	
	/**
	 * @brief Calls the environment jump function
	 * @param learningPhase (boolean)
	 */
	public void jump (boolean learningPhase){
		Environment e = getEnvironment();
		e.jump(this, learningPhase);
	}

	/**
	 * @brief getter
	 * @return the serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @brief Gets and sets the perception of the body
	 * @return perception (List<PerceivableObject>)
	 */
	@Override
	public List<PerceivableObject> getPerception() {
		Environment e = getEnvironment();
		List<PerceivableObject> percept=e.getPerception(this);
		setPerception(percept);		
		return this.perception;
	}

	/**
	 * @brief Sets the perception
	 * @param perception (List<PerceivableObject>) the perception to set
	 */
	public void setPerception(List<PerceivableObject> perception) {
		assert(perception!=null);
		new PerceptionEvent(perception,this);
		this.perception = perception;
	}
	
	/**
	 * @brief Choose an action to perform in the perception
	 * @return act (ActionInfuence)
	 */
	private ActionInfluence LearningRoutine(){
		ActionEnum[] pos = ActionEnum.values();
		int r;
		do{
			r = (int)(Math.random() * (pos.length-1)) ;
		}while(pos[r].getDir()==null);
		ActionInfluence act= new ActionInfluence(pos[r]);
		return act;
	}
	
	/**
	 * @brief Tries action in the body's perception to fill the QTable
	 */
	public void learn(){
		Environment e = getEnvironment();
		int samePosition = 0;
		Point currentPos = this.getPosition();
	
		while(!e.isArrived() && samePosition < 100) {
			ActionInfluence a = LearningRoutine();
			System.out.println(a.getType().getName());
			influence(a);
			System.out.println(this.getPosition());
			if (currentPos != this.getPosition()) {
				currentPos = this.getPosition();
				samePosition = 0;
			} else {
				samePosition++;
			}
		}
			
		if (e.isArrived()) {
			System.out.println("ARRIVED");
		} else {
			System.out.println("BLOCKED in " + currentPos.x + ", " + currentPos.y);
		}
	}
	
}
