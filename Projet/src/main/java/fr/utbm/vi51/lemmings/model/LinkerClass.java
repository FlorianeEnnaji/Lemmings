package fr.utbm.vi51.lemmings.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fr.utbm.vi51.lemmings.agent.LemmingAgent;

/**
 * @author antonin.waltz@utbm.fr, floriane.ennaji@utbm.fr, lucille.gomez@utbm.fr, romain.thibaud@utbm.fr
 * @brief Links agents and their perceptions
 * @see fr.utbm.vi51.lemmings.model.LemmingAgent
 */
public class LinkerClass {
	
	private final ScheduledExecutorService executorService;

	private final Map<UUID, LemmingAgent> agentMind = new TreeMap<UUID,LemmingAgent>();
	private final Map<UUID, List<PerceivableObject>> perceptions = new TreeMap<UUID,List<PerceivableObject>>();
	
	//private Kernel ja ;	
	//private OpenEventSpace space;
	//private UUID spaceId;


	
	/**
	 * @brief Default Constructor
	 */
	public LinkerClass(){
		executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(new Runnable() {
		       public void run() { sendPerception(); }
	     }, 0, 1, TimeUnit.SECONDS);
	}
	
	/**
	 * @brief Send perception to the agent
	 */
	public Runnable sendPerception(){
		for (UUID i : agentMind.keySet()) {
			if (perceptions.get(i)!=null && perceptions.get(i).size() > 0){
				List<PerceivableObject> clone = new ArrayList<>();
				clone.addAll(perceptions.get(i));
				perceptions.replace(i, new ArrayList<>());
				agentMind.get(i).perception_Event(clone);
			}
			
		}
		return null;		
	}
	
	/**
	 * @brief Set perception from Environment
	 * @param body (UUID) the id of the body
	 * @param percept (List<PerceivableObject>) the body's perception
	 */
	public void setPerception(UUID body, List<PerceivableObject> percept){
		if(perceptions.containsKey(body)){
			perceptions.replace(body, percept);
		}
	}
	
	/**
	 * @brief Creation of an agent
	 * @param ID (UUID) the id of the agent
	 * @param body (LemmingBody) the body corresponding to the agent
	 */
	public void createAgent(UUID ID, LemmingBody body){
		LemmingAgent agent=new LemmingAgent(ID,body);
		agentMind.put(ID, agent);
		perceptions.put(ID, null);
		
		
		/*System.out.println("linker");
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
				spaceId=new UUID(0, 1);
				 AgentContext defaultContext=getSkill(io.sarl.core.DefaultContextInteractions.class).getDefaultContext();;
				space=defaultContext.<OpenEventSpace>getSpace(spaceId);
				EventListener asEventListenner=getSkill(io.sarl.core.Behaviors.class).asEventListener() ;
				space.register(asEventListenner);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			ja.spawn(ID, Lemming.class,arg);
		}*/
		
		
	}
	
	
	/*
	/**
	 * @brief sending onPerception event to agent
	 * @param ID (UUID)the id of the agent
	 * @param perception (List<Perceivable>) the agent's perception
	 */
	/*public void givePerception(UUID ID, List<PerceivableObject> perception, LemmingBody body) {
		System.out.println("GIVING PERCEPTION, is space not null ? " + (space != null));
		PerceptionEvent event;
		if (ID != null) {
			event = new PerceptionEvent(perception, body);
			space.emit(event, new AddressUUIDScope(ID));
			
		}
	}*/

}
