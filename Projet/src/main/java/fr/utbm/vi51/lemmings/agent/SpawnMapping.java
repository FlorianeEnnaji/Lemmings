/* 
 * $Id$
 * 
 * Copyright (c) 2014-15 Stephane GALLAND <stephane.galland@utbm.fr>.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package fr.utbm.vi51.lemmings.agent;


import fr.utbm.vi51.lemmings.model.LemmingBody;
import io.sarl.lang.core.Agent;

/**
 * Define the spawning mapping
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class SpawnMapping {

	/** Replies the agent type for a body.
	 * 
	 * @param body the body of the agent
	 * @return the type of a agent for the given body.
	 */
	public abstract Class<? extends Agent> getAgentTypeForBody(LemmingBody body);	
	
	/** Replies the agent type for a body.
	 * 
	 * @param body The body of the agent
	 * @return the type of a agent for the given body.
	 * @see #getAgentTypeForBody(AgentBody)
	 */
	public final Class<? extends Agent> operator_doubleArrow(LemmingBody body) {
		return getAgentTypeForBody(body);
	}

}