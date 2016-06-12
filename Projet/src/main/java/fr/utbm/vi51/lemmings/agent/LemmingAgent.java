package fr.utbm.vi51.lemmings.agent;

import java.util.List;
import java.util.UUID;

import fr.utbm.vi51.lemmings.learning.QTable;
import fr.utbm.vi51.lemmings.model.LemmingBody;
import fr.utbm.vi51.lemmings.model.PerceivableObject;
import fr.utbm.vi51.lemmings.utils.ActionEnum;

public class LemmingAgent implements Perception_Event {
	
	private UUID ID;
	private LemmingBody body;
	
	public LemmingAgent(UUID i, LemmingBody b){
		ID=i;
		body=b;
	}

	@Override
	public void perception_Event(List<PerceivableObject> percept) {
		if (percept.size() != 0) {
			ActionEnum action = ActionEnum.WALK_EAST;
			float[] rewardTable = body.getEnvironment().getQTable().getCoef(percept);
			if (rewardTable != null) {
				int index = 0;
				int idAction = 0;
				float bestReward = (float) -100.0;
				for (float reward : rewardTable){
					if (bestReward < reward){
						bestReward = reward;
						// The current action is better
						idAction = index;
					}
					index ++;
				}
				action = ActionEnum.values()[idAction]; //ActionEnum.WALK_EAST for example
			}
			emitInfluence(new ActionInfluence(action));
		} else {
			System.out.println("AGENT GOT EMPTY PERCEPTION");
		}
	}

	public void emitInfluence(ActionInfluence action){
		body.influenceGame(action);
		//body.influence(action);
	}
	
	

}
