package fr.utbm.vi51.lemmings.model;

import java.util.List;
import java.util.UUID;

import fr.utbm.vi51.lemmings.QTable;
import fr.utbm.vi51.lemmings.utils.enums.ActionEnum;
import fr.utbm.vi51.lemmings.model.ActionInfluence;
import fr.utbm.vi51.lemmings.model.Environment;

public class LemmingAgent implements Perception_Event {
	
	private UUID ID;
	private LemmingBody body;
	
	public LemmingAgent(UUID i, LemmingBody b){
		ID=i;
		body=b;
	}

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

	public void emitInfluence(ActionInfluence action){
		body.influenceGame(action);
		//body.influence(action);
	}
	
	

}
