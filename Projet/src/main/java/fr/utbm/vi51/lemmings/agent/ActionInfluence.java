package fr.utbm.vi51.lemmings.agent;

import fr.utbm.vi51.lemmings.utils.ActionEnum;

/**
 * Action Influence
 */
public class ActionInfluence extends Influence {
	
	private static final long serialVersionUID = 1L;
	private final ActionEnum action;

	/**
	 * @param act
	 */
	public ActionInfluence(ActionEnum act) {
		super(null);
		// TODO Auto-generated constructor stub
		this.action=act;
	}

	/**
	 * @return the type of the action
	 */
	public ActionEnum getType(){
		return this.action;		
	}
	

}
