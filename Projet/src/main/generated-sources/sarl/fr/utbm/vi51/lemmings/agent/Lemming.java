package fr.utbm.vi51.lemmings.agent;

import fr.utbm.info.vi51.framework.agent.StandardPhysicEnvironment;
import fr.utbm.vi51.lemmings.agent.BehaviourOutput;
import fr.utbm.vi51.lemmings.agent.Influence;
import fr.utbm.vi51.lemmings.agent.PerceptionEvent;
import fr.utbm.vi51.lemmings.agent.PhysicEnvironment;
import fr.utbm.vi51.lemmings.utils.ActionEnum;
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
import org.eclipse.xtext.xbase.lib.InputOutput;

@SarlSpecification("0.3")
@SuppressWarnings("all")
public class Lemming extends Agent {
  @Percept
  public void _handle_Initialize_0(final Initialize occurrence) {
    InputOutput.<String>println("agent");
    Object _get = occurrence.parameters[0];
    Object _get_1 = occurrence.parameters[1];
    StandardPhysicEnvironment physicSkill = new StandardPhysicEnvironment(
      ((UUID) _get), 
      ((UUID) _get_1));
    this.<StandardPhysicEnvironment>setSkill(PhysicEnvironment.class, physicSkill);
  }
  
  @Percept
  public void _handle_PerceptionEvent_1(final PerceptionEvent occurrence) {
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
   * See the capacity {@link fr.utbm.vi51.lemmings.agent.PhysicEnvironment#influenceAction(fr.utbm.vi51.lemmings.agent.Influence[])}.
   * 
   * @see fr.utbm.vi51.lemmings.agent.PhysicEnvironment#influenceAction(fr.utbm.vi51.lemmings.agent.Influence[])
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(PhysicEnvironment.class)
  protected void influenceAction(final Influence... otherInfluences) {
    getSkill(fr.utbm.vi51.lemmings.agent.PhysicEnvironment.class).influenceAction(otherInfluences);
  }
  
  /**
   * See the capacity {@link fr.utbm.vi51.lemmings.agent.PhysicEnvironment#influenceAction(fr.utbm.vi51.lemmings.utils.ActionEnum,fr.utbm.vi51.lemmings.agent.Influence[])}.
   * 
   * @see fr.utbm.vi51.lemmings.agent.PhysicEnvironment#influenceAction(fr.utbm.vi51.lemmings.utils.ActionEnum,fr.utbm.vi51.lemmings.agent.Influence[])
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
  protected UUID spawnInContext(final Class<? extends Agent> arg0, final AgentContext arg1, final Object... arg2) {
    return getSkill(io.sarl.core.Lifecycle.class).spawnInContext(arg0, arg1, arg2);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Lifecycle#spawnInContextWithID(java.lang.Class<? extends io.sarl.lang.core.Agent>,java.util.UUID,io.sarl.lang.core.AgentContext,java.lang.Object[])}.
   * 
   * @see io.sarl.core.Lifecycle#spawnInContextWithID(java.lang.Class<? extends io.sarl.lang.core.Agent>,java.util.UUID,io.sarl.lang.core.AgentContext,java.lang.Object[])
   */
  @FiredEvent(AgentSpawned.class)
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Lifecycle.class)
  protected UUID spawnInContextWithID(final Class<? extends Agent> arg0, final UUID arg1, final AgentContext arg2, final Object... arg3) {
    return getSkill(io.sarl.core.Lifecycle.class).spawnInContextWithID(arg0, arg1, arg2, arg3);
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
