package fr.utbm.vi51.lemmings.agent;

import fr.utbm.vi51.lemmings.agent.Influence;
import fr.utbm.vi51.lemmings.utils.ActionEnum;
import io.sarl.lang.annotation.DefaultValue;
import io.sarl.lang.annotation.DefaultValueSource;
import io.sarl.lang.annotation.DefaultValueUse;
import io.sarl.lang.annotation.SarlSourceCode;
import io.sarl.lang.core.Capacity;
import javax.annotation.Generated;

@SuppressWarnings("all")
public interface PhysicEnvironment extends Capacity {
  @DefaultValueSource
  public abstract void influenceAction(@DefaultValue("fr.utbm.vi51.lemmings.agent.PhysicEnvironment#INFLUENCEACTION_0") final ActionEnum action, final Influence... otherInfluences);
  
  /**
   * Default value for the parameter action
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @SarlSourceCode(" null")
  public final static ActionEnum ___FORMAL_PARAMETER_DEFAULT_VALUE_INFLUENCEACTION_0 = null;
  
  @DefaultValueUse("fr.utbm.vi51.lemmings.utils.ActionEnum,fr.utbm.vi51.lemmings.agent.Influence*")
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  public abstract void influenceAction(final Influence... otherInfluences);
}
