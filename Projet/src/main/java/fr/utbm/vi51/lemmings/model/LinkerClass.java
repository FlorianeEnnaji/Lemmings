package fr.utbm.vi51.lemmings.model;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import fr.utbm.vi51.lemmings.agent.Lemming;
import io.sarl.core.Initialize;

public class LinkerClass {
	
	public final Map<UUID,Lemming> agentMind = new TreeMap<UUID,Lemming>();
	
	public LinkerClass(){
		
	}
	
	public void createAgent(UUID ID){
		System.out.println("linker");
		Lemming agent=new Lemming(null, ID, ID);
		Initialize occurrence = new Initialize();
		UUID [] arg=new UUID[2] ;
		arg[0]=ID;
		arg[1]=ID;
		occurrence.parameters=arg;
		agent._handle_Initialize_0(occurrence);
		agentMind.put(ID, agent);
	}

}
