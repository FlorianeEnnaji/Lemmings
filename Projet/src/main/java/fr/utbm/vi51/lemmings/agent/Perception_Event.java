package fr.utbm.vi51.lemmings.agent;

import java.util.List;

import fr.utbm.vi51.lemmings.model.PerceivableObject;

/**
 * @author antonin.waltz@utbm.fr, floriane.ennaji@utbm.fr, lucille.gomez@utbm.fr, romain.thibaud@utbm.fr
 * @brief Event called when the agent's body has moved and has a new perception
 */
public interface Perception_Event {

	/**
	 * @brief Event containing the perception
	 */
	public void perception_Event (List<PerceivableObject> percept);
}
