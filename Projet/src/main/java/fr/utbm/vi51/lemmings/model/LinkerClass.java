package fr.utbm.vi51.lemmings.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import com.hazelcast.mapreduce.impl.task.DefaultContext;

import fr.utbm.vi51.lemmings.agent.Lemming;
import fr.utbm.vi51.lemmings.agent.PerceptionEvent;
import fr.utbm.vi51.lemmings.agent.PhysicEnvironment;
import io.janusproject.Boot;
import io.janusproject.kernel.Kernel;
import io.sarl.util.OpenEventSpace;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Lifecycle;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.EventListener;
import io.sarl.core.Behaviors;
import io.sarl.core.AgentKilled;
import io.sarl.core.AgentSpawned;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Destroy;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.lang.annotation.EarlyExit;
import io.sarl.lang.annotation.FiredEvent;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.Behavior;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.EventSpace;
import io.sarl.lang.core.Percept;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.Space;
import io.sarl.lang.core.SpaceID;
import io.sarl.util.OpenEventSpace;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Generated;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;



import io.sarl.core.Initialize;

/**
 * LinkerClass
 */
public class LinkerClass {
	
	private final ScheduledExecutorService executorService;

	private final Map<UUID, LemmingAgent> agentMind = new TreeMap<UUID,LemmingAgent>();
	private final Map<UUID, List<PerceivableObject>> perceptions = new TreeMap<UUID,List<PerceivableObject>>();
	
	//private Kernel ja ;	
	//private OpenEventSpace space;
	//private UUID spaceId;


	
	/**
	 * Default Constructor
	 */
	public LinkerClass(){
		 executorService = Executors.newSingleThreadScheduledExecutor();
		 executorService.scheduleAtFixedRate(sendPerception(), 0, 1, TimeUnit.SECONDS);
	}
	
	public Runnable sendPerception(){
		for (UUID i : agentMind.keySet()) {
			if (perceptions.get(i)!=null){
				agentMind.get(i).perception_Event(perceptions.get(i));
				perceptions.replace(i, null);
			}
		}
		return null;		
	}
	
	public void setPerception(UUID body, List<PerceivableObject> percept){
		if(perceptions.containsKey(body)){
			perceptions.replace(body, percept);
		}
	}
	
	/**
	 * creation of an agent
	 * @param ID the id of the agent
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
