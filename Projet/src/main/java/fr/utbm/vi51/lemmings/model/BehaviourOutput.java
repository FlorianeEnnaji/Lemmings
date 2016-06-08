package fr.utbm.vi51.lemmings.model;

import java.io.Serializable;

import fr.utbm.vi51.lemmings.utils.enums.ActionEnum;

/**
 * BehaviourOuptut
 */
public class BehaviourOutput implements Serializable {

	private static final long serialVersionUID = -490730862869133230L;
	
	private final ActionEnum action;
	
	/**
	 * Constructor
	 * @param act
	 */
	public BehaviourOutput(ActionEnum act) {
		this.action = act;
	}
	
	/**
	 * @param outputToCopy the BehaviourOutput to copy
	 */
	public BehaviourOutput(BehaviourOutput outputToCopy) {
		assert(outputToCopy!=null);
		this.action= outputToCopy.getAction();
		
	}

	/**
	 * @return the current action
	 */
	public ActionEnum getAction() {
		return this.action;
	}
	
	/**
	 * Set the output to copy
	 * @param outputToCopy the BehaviourOutput to copy
	 */
	public void set(BehaviourOutput outputToCopy) {
		if (outputToCopy!=null) {
			if (outputToCopy.getAction()!=getAction()) {
				throw new IllegalArgumentException();
			}
		}
	}
	

}
