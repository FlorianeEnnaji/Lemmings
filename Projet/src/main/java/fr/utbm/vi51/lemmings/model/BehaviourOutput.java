package fr.utbm.vi51.lemmings.model;

import java.io.Serializable;

import fr.utbm.vi51.lemmings.utils.enums.ActionEnum;

public class BehaviourOutput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -490730862869133230L;
	
	private final ActionEnum action;
	
	public BehaviourOutput(ActionEnum act) {
		this.action = act;
	}
	
	public BehaviourOutput(BehaviourOutput outputToCopy) {
		assert(outputToCopy!=null);
		this.action= outputToCopy.getAction();
		
	}

	public ActionEnum getAction() {
		return this.action;
	}
	
	public void set(BehaviourOutput outputToCopy) {
		if (outputToCopy!=null) {
			if (outputToCopy.getAction()!=getAction()) {
				throw new IllegalArgumentException();
			}
		}
	}
	

}
