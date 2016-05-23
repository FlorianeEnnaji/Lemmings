package fr.utbm.vi51.lemmings.model;

import java.util.UUID;

import fr.utbm.vi51.lemmings.utils.enums.ActionEnum;

public class ActionInfluence extends Influence {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ActionEnum action;

	public ActionInfluence(ActionEnum act) {
		super(null);
		// TODO Auto-generated constructor stub
		this.action=act;
	}

	public ActionEnum getType(){
		return action;		
	}
	

}
