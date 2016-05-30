package fr.utbm.vi51.lemmings.model;

import java.awt.Point;
import java.util.List;

import fr.utbm.vi51.lemmings.agent.PerceptionEvent;
import fr.utbm.vi51.lemmings.utils.enums.ActionEnum;
import fr.utbm.vi51.lemmings.utils.enums.MoveDirection;

public class LemmingBody extends Body {

	private static final long serialVersionUID = -4636419559142339321L;
	
	private List<PerceivableObject> perception;
	
	public LemmingBody(Environment environment, MoveDirection direction, Point position) {
		super(environment, direction, position);
		//LearningRoutine();
		//setPerception(null);
		learn();
		
	}

	public void influence(Influence influence) {
		if(influence!=null){
			if(influence instanceof ActionInfluence){
				switch(((ActionInfluence) influence).getType()){
				case WALK_EAST : 
					walk(((ActionInfluence) influence).getType().getDir());
					break;
				case WALK_WEST :
					walk(((ActionInfluence) influence).getType().getDir());
					break;
				case DIG_EAST : 
					dig(((ActionInfluence) influence).getType().getDir());
					break;				
				case DIG_WEST : 
					dig(((ActionInfluence) influence).getType().getDir());
					break;
				case DIG_SOUTH : 
					dig(((ActionInfluence) influence).getType().getDir());
					break;
				case CLIMB : 
					climb();
					break;
				case JUMP : 
					jump();
					break;
				default : break;
				}
			}
		}
		//LearningRoutine();
	}
	
	public void walk(MoveDirection dir){
		move(dir);
	}
	
	public void dig (MoveDirection dir){
		Environment e = getEnvironment();
		e.dig(this, dir);	
	}
	
	public void climb (){
		Environment e = getEnvironment();
		e.climb(this);
	}
	
	public void jump (){
		Environment e = getEnvironment();
		e.jump(this);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<PerceivableObject> getPerception() {
		Environment e = getEnvironment();
		List<PerceivableObject> percept=e.getPerception(this);
		setPerception(percept);		
		return perception;
	}

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
	
	public void learn(){
		Environment e = getEnvironment();
		int samePosition = 0;
		Point currentPos = this.getPosition();
		while(!e.isArrived() && samePosition < 50) {
			ActionInfluence a = LearningRoutine();
			System.out.println(a.getType().getName());
			influence(a);
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
			System.out.println("BLOCKED");
		}
	}
	
	

}
