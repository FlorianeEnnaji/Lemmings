package fr.utbm.vi51.lemmings.model;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import fr.utbm.vi51.lemmings.agent.Lemming;

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

}
