package fr.utbm.vi51.lemmings.model;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import fr.utbm.vi51.lemmings.agent.Lemming;

import io.sarl.core.Initialize;

/**
 * LinkerClass
 */
public class LinkerClass {
	
	public final Map<UUID,Lemming> agentMind = new TreeMap<UUID,Lemming>();
    //private janusKernel ja = Boot::startJanus(null,typeof(Lemming),args);
	
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
		agent._handle_Initialize_0(occurrence);
		this.agentMind.put(ID, agent);
	}

}
