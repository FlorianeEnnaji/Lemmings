package fr.utbm.vi51.lemmings.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import fr.utbm.vi51.lemmings.agent.Lemming;
import fr.utbm.vi51.lemmings.agent.PerceptionEvent;
import fr.utbm.vi51.lemmings.agent.PhysicEnvironment;
import io.janusproject.Boot;
import io.janusproject.kernel.Kernel;



import io.sarl.core.Initialize;

/**
 * LinkerClass
 */
public class LinkerClass {
	
	public final Map<UUID,Lemming> agentMind = new TreeMap<UUID,Lemming>();

	private Kernel ja ;


	
	/**
	 * Default Constructor
	 */

	public LinkerClass(){
		
	}
	
	/**
	 * creation of an agent
	 * @param ID the id of the agent
	 */
	public void createAgent(UUID ID){
		System.out.println("linker");
		Lemming agent=new Lemming(null, ID, ID);
		Initialize occurrence = new Initialize();

		UUID [] arg=new UUID[2] ;
		arg[0]=ID;
		arg[1]=ID;
		occurrence.parameters=arg;
		if (agentMind.isEmpty()){
			Boot.setOffline(true);
			try {
				ja=Boot.startJanus((Class) null, Lemming.class,arg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			ja.spawn(ID, Lemming.class,arg);
		}
		agentMind.put(ID, agent);
		
	}
	
	/**
	 * @brief sending onPerception event to agent
	 * @param ID (UUID)the id of the agent
	 * @param perception (List<Perceivable>) the agent's perception
	 */
	public void givePerception(UUID ID, List<PerceivableObject> perception, LemmingBody body) {
		PerceptionEvent event;
		if (ID != null) {
			event = new PerceptionEvent(perception, body);
			/**TODO find a way to emit this event. usually 
			 * it's made with something like
			 * this.space.emit(event, new AddressUUIDScope(ID));
			 * (Searched in Lab Works but didn't really find how to get "space" and where to define emit..)
			 * */
		}
	}

}
