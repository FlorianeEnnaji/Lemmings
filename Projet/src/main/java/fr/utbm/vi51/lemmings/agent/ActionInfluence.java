package fr.utbm.vi51.lemmings.agent;

import fr.utbm.vi51.lemmings.utils.ActionEnum;

/**
 * @author antonin.waltz@utbm.fr, floriane.ennaji@utbm.fr, lucille.gomez@utbm.fr, romain.thibaud@utbm.fr
 * @brief Used by the agent to perform an action
 * @see fr.utbm.vi51.lemmings.agent.Influence
 */
public class ActionInfluence extends Influence {
	
	private static final long serialVersionUID = 1L;
	private final ActionEnum action;

	/**
	 * @brief Constructor
	 * @param act (ActionEnum)
	 */
	public ActionInfluence(ActionEnum act) {
		super(null);
		// TODO Auto-generated constructor stub
		this.action=act;
	}

	/**
	 * @brief Getter
	 * @return the type of the action
	 */
	public ActionEnum getType(){
		return this.action;		
	}
	

}
