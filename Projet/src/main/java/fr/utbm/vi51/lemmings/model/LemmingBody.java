package fr.utbm.vi51.lemmings.model;

import java.awt.Point;

import java.util.List;

import fr.utbm.vi51.lemmings.agent.PerceptionEvent;
import fr.utbm.vi51.lemmings.utils.enums.ActionEnum;
import fr.utbm.vi51.lemmings.utils.enums.MoveDirection;

/**
 * LemmingBody
 */
public class LemmingBody extends Body {

	private static final long serialVersionUID = -4636419559142339321L;
	
	private List<PerceivableObject> perception;
	
	/**
	 * Constructor
	 * @param environment the environment the body will be placed in
	 * @param direction the direction of the body
	 * @param position the position of the body
	 */
	public LemmingBody(Environment environment, MoveDirection direction, Point position) {
		super(environment, direction, position);

		
		/*
		 * Comment the 3 following lines if you want to play
		 */
		LearningRoutine();
		setPerception(null);
		learn();		
	}

	public LemmingBody(Environment environment, MoveDirection direction, Point position, int a) {
		super(environment, direction, position);
		/*
		 * Uncomment the following line if you want to play
		 */
	}

	/**
	 * Game function for moving the body of lemmings
	 */
	public void moveLemmingBody() {
		Environment e = getEnvironment();
		Point currentPos = this.getPosition();
		
		int samePosition = 0;
		while(!e.isArrived() && samePosition < 100) {
			ActionEnum action = e.getBestMove(this);
			MoveDirection direction = action.getDir();
			Point nextPosition = new Point( this.getPosition().x + direction.getXMove(), this.getPosition().y + direction.getYMove()) ;
			System.out.println(action.getName() + "FROM" + this.getPosition() +" TO " + nextPosition);
			
			performAction(action);
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
	
	/**
	 * @param action the action we want to perform
	 * TODO Not call the same functions that update the stateList!!
	 */
	public void performAction(ActionEnum action) {
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

	/**
	 * @param influence
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
		//LearningRoutine();
	}
	
	/**
	 * @param dir the direction in which the body will walk
	 */
	public void walk(MoveDirection dir, boolean learningPhase){
		move(dir, learningPhase);
	}
	
	/**
	 * @param dir dir the direction in which the body will dig
	 */
	public void dig (MoveDirection dir, boolean learningPhase){
		Environment e = getEnvironment();
		e.dig(this, dir, learningPhase);	
	}
	
	/**
	 * The body climb
	 */
	public void climb (boolean learningPhase){
		Environment e = getEnvironment();
		e.climb(this, learningPhase);
	}
	
	/**
	 * The body jump
	 */
	public void jump (boolean learningPhase){
		Environment e = getEnvironment();
		e.jump(this, learningPhase);
	}

	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public List<PerceivableObject> getPerception() {
		Environment e = getEnvironment();
		List<PerceivableObject> percept=e.getPerception(this);
		setPerception(percept);		
		return this.perception;
	}

	/**
	 * Set the perception
	 * @param perception the perception to set
	 */
	public void setPerception(List<PerceivableObject> perception) {
		assert(perception!=null);
		new PerceptionEvent(perception,this);
		this.perception = perception;
	}
	
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
	 * Learn function
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
