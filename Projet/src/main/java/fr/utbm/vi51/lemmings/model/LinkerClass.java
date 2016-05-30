package fr.utbm.vi51.lemmings.model;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import fr.utbm.vi51.lemmings.agent.Lemming;

public class LinkerClass {
	
	public final Map<UUID,Lemming> agentMind = new TreeMap<UUID,Lemming>();
	
	public LinkerClass(){
		
	}
	
	public void createAgent(UUID ID){
		System.out.println("linker");
		Lemming agent=new Lemming(null, ID, ID);
		agentMind.put(ID, agent);
	}

}
