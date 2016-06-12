package fr.utbm.vi51.lemmings.agent;

import java.util.List;
import java.util.UUID;

import fr.utbm.vi51.lemmings.learning.QTable;
import fr.utbm.vi51.lemmings.model.LemmingBody;
import fr.utbm.vi51.lemmings.model.PerceivableObject;
import fr.utbm.vi51.lemmings.utils.ActionEnum;

/**
 * @author antonin.waltz@utbm.fr, floriane.ennaji@utbm.fr, lucille.gomez@utbm.fr, romain.thibaud@utbm.fr
 * @brief The Lemming agent
 * @see fr.utbm.vi51.lemmings.agent.Perception_Event
 * 
 * The autonomous entity that plays the game by itself
 */
public class LemmingAgent implements Perception_Event {
	
	private UUID ID;
	private LemmingBody body;
	
	/**
	 * @brief Constructor
	 * @param i (UUID) the identifier of the agent
	 * @param b (LemmingBody) the body of the agent
	 */
	public LemmingAgent(UUID i, LemmingBody b){
		ID=i;
		body=b;
	}

	/**
	 * @brief Determines the action to perform regarding the perception received
	 * @param percept (List<PerceivableObject>) the perception of the agent
	 * @see perception_Event
	 */
	@Override
	public void perception_Event(List<PerceivableObject> percept) {
		System.out.println("AGENT GOT PERCEPTIONNNNN");
		ActionEnum action = ActionEnum.WALK_EAST;
		float[] rewardTable = body.getEnvironment().getQTable().getCoef(percept);
		if (rewardTable != null) {
			int index = 0;
			int idAction = 0;
			float bestReward = (float) -100.0;
			for (float reward : rewardTable){
				if (bestReward < reward){
					// The current action is better
					idAction = index;
				}
				index ++;
			}
			action = ActionEnum.values()[idAction]; //ActionEnum.WALK_EAST for example
		}
		emitInfluence(new ActionInfluence(action));
		
	}

	/**
	 * @brief Emits the influence to perform the action
	 * @param action (ActionInfluence) the influence of the action the agent wants to perform
	 */
	public void emitInfluence(ActionInfluence action){
		body.influenceGame(action);
		//body.influence(action);
	}
	
	

}
