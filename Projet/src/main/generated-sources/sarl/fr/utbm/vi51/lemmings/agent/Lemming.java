package fr.utbm.vi51.lemmings.agent;

import fr.utbm.info.vi51.framework.agent.StandardPhysicEnvironment;
import fr.utbm.vi51.lemmings.agent.PhysicEnvironment;
import fr.utbm.vi51.lemmings.model.BehaviourOutput;
import fr.utbm.vi51.lemmings.model.Influence;
import fr.utbm.vi51.lemmings.utils.enums.ActionEnum;
import io.sarl.core.AgentKilled;
import io.sarl.core.AgentSpawned;
import io.sarl.core.Destroy;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.lang.annotation.EarlyExit;
import io.sarl.lang.annotation.FiredEvent;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AgentContext;
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
    Object _get = occurrence.parameters[0];
    Object _get_1 = occurrence.parameters[1];
    StandardPhysicEnvironment physicSkill = new StandardPhysicEnvironment(
      ((UUID) _get), 
      ((UUID) _get_1));
    this.<StandardPhysicEnvironment>setSkill(PhysicEnvironment.class, physicSkill);
  }
  
  protected void emitInfluence(final BehaviourOutput output, final Influence... influence) {
    if ((output != null)) {
      ActionEnum _action = output.getAction();
      this.influenceAction(_action, influence);
    } else {
      this.influenceAction(ActionEnum.NOTHING, influence);
    }
  }
  
  /**
   * See the capacity {@link fr.utbm.vi51.lemmings.agent.PhysicEnvironment#influenceAction(fr.utbm.vi51.lemmings.model.Influence[])}.
   * 
   * @see fr.utbm.vi51.lemmings.agent.PhysicEnvironment#influenceAction(fr.utbm.vi51.lemmings.model.Influence[])
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(PhysicEnvironment.class)
  protected void influenceAction(final Influence... otherInfluences) {
    getSkill(fr.utbm.vi51.lemmings.agent.PhysicEnvironment.class).influenceAction(otherInfluences);
  }
  
  /**
   * See the capacity {@link fr.utbm.vi51.lemmings.agent.PhysicEnvironment#influenceAction(fr.utbm.vi51.lemmings.utils.enums.ActionEnum,fr.utbm.vi51.lemmings.model.Influence[])}.
   * 
   * @see fr.utbm.vi51.lemmings.agent.PhysicEnvironment#influenceAction(fr.utbm.vi51.lemmings.utils.enums.ActionEnum,fr.utbm.vi51.lemmings.model.Influence[])
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(PhysicEnvironment.class)
  protected void influenceAction(final ActionEnum action, final Influence... otherInfluences) {
    getSkill(fr.utbm.vi51.lemmings.agent.PhysicEnvironment.class).influenceAction(action, otherInfluences);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Lifecycle#killMe()}.
   * 
   * @see io.sarl.core.Lifecycle#killMe()
   */
  @EarlyExit
  @FiredEvent({ AgentKilled.class, Destroy.class })
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Lifecycle.class)
  protected void killMe() {
    getSkill(io.sarl.core.Lifecycle.class).killMe();
  }
  
  /**
   * See the capacity {@link io.sarl.core.Lifecycle#spawnInContext(java.lang.Class<? extends io.sarl.lang.core.Agent>,io.sarl.lang.core.AgentContext,java.lang.Object[])}.
   * 
   * @see io.sarl.core.Lifecycle#spawnInContext(java.lang.Class<? extends io.sarl.lang.core.Agent>,io.sarl.lang.core.AgentContext,java.lang.Object[])
   */
  @FiredEvent(AgentSpawned.class)
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Lifecycle.class)
  protected UUID spawnInContext(final Class<? extends Agent> agentClass, final AgentContext context, final Object... params) {
    return getSkill(io.sarl.core.Lifecycle.class).spawnInContext(agentClass, context, params);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Lifecycle#spawnInContextWithID(java.lang.Class<? extends io.sarl.lang.core.Agent>,java.util.UUID,io.sarl.lang.core.AgentContext,java.lang.Object[])}.
   * 
   * @see io.sarl.core.Lifecycle#spawnInContextWithID(java.lang.Class<? extends io.sarl.lang.core.Agent>,java.util.UUID,io.sarl.lang.core.AgentContext,java.lang.Object[])
   */
  @FiredEvent(AgentSpawned.class)
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Lifecycle.class)
  protected UUID spawnInContextWithID(final Class<? extends Agent> agentClass, final UUID agentID, final AgentContext context, final Object... params) {
    return getSkill(io.sarl.core.Lifecycle.class).spawnInContextWithID(agentClass, agentID, context, params);
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
