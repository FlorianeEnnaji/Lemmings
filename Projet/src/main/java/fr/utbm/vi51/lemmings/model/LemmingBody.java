package fr.utbm.vi51.lemmings.model;

import java.awt.Point;

import fr.utbm.vi51.lemmings.utils.enums.MoveDirection;

public class LemmingBody extends Body {

	private static final long serialVersionUID = -4636419559142339321L;
	
	public LemmingBody(Environment environment, MoveDirection direction, Point position) {
		super(environment, direction, position);
		// TODO Auto-generated constructor stub
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

}
