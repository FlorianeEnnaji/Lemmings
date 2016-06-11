package fr.utbm.vi51.lemmings.model;

import java.util.List;
import java.util.UUID;

public class LemmingAgent implements Perception_Event {
	
	private UUID ID;
	private LemmingBody body;
	
	public LemmingAgent(UUID i, LemmingBody b){
		ID=i;
		body=b;
	}

	@Override
	public void perception_Event(List<PerceivableObject> percept) {
		
		
	}
	
	public void emitInfluence(ActionInfluence action){
		body.influence(action);
	}
	
	

}
