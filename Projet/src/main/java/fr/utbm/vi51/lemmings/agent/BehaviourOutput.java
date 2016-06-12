package fr.utbm.vi51.lemmings.agent;

import java.io.Serializable;

import fr.utbm.vi51.lemmings.utils.ActionEnum;

/**
 * @author antonin.waltz@utbm.fr, floriane.ennaji@utbm.fr, lucille.gomez@utbm.fr, romain.thibaud@utbm.fr
 * @brief Used by the agent to perform an action
 */
public class BehaviourOutput implements Serializable {

	private static final long serialVersionUID = -490730862869133230L;
	
	private final ActionEnum action;
	
	/**
	 * @brief Constructor
	 * @param act (ActionEnum)
	 */
	public BehaviourOutput(ActionEnum act) {
		this.action = act;
	}
	
	/**
	 * @brief Constructor
	 * @param outputToCopy the BehaviourOutput to copy
	 */
	public BehaviourOutput(BehaviourOutput outputToCopy) {
		assert(outputToCopy!=null);
		this.action= outputToCopy.getAction();
		
	}

	/**
	 * @brief Getter
	 * @return action (ActionEnum) the current action
	 */
	public ActionEnum getAction() {
		return this.action;
	}
	
	/**
	 * @brief setter
	 * @param outputToCopy (BehaviourOutput) the BehaviourOutput to copy
	 */
	public void set(BehaviourOutput outputToCopy) {
		if (outputToCopy!=null) {
			if (outputToCopy.getAction()!=getAction()) {
				throw new IllegalArgumentException();
			}
		}
	}
	

}
