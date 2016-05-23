package fr.utbm.vi51.lemmings.agent;

import fr.utbm.vi51.lemmings.model.Influence;
import io.sarl.core.Initialize;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.Percept;
import java.util.UUID;
import javax.annotation.Generated;
import javax.inject.Inject;

@SarlSpecification("0.3")
@SuppressWarnings("all")
public class Lemming extends Agent {
  @Percept
  public void _handle_Initialize_0(final Initialize occurrence) {
  }
  
  protected void emitInfluence(final Influence... influence) {
  }
  
  /**
   * Construct an agent.
   * @param builtinCapacityProvider - provider of the built-in capacities.
   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.
   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.
   */
  @Inject
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  public Lemming(final BuiltinCapacitiesProvider builtinCapacityProvider, final UUID parentID, final UUID agentID) {
    super(builtinCapacityProvider, parentID, agentID);
  }
}
