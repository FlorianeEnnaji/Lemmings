package fr.utbm.vi51.lemmings.agent;

import fr.utbm.vi51.lemmings.model.PerceivableObject;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.Event;
import java.util.List;
import javax.annotation.Generated;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.3")
@SuppressWarnings("all")
public class PerceptionEvent extends Event {
  public final PerceivableObject body;
  
  public final List<PerceivableObject> perceptions;
  
  public PerceptionEvent(final List<PerceivableObject> p, final PerceivableObject b) {
    this.perceptions = p;
    this.body = b;
  }
  
  @Override
  @Pure
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PerceptionEvent other = (PerceptionEvent) obj;
    if (this.body == null) {
      if (other.body != null)
        return false;
    } else if (!this.body.equals(other.body))
      return false;
    if (this.perceptions == null) {
      if (other.perceptions != null)
        return false;
    } else if (!this.perceptions.equals(other.perceptions))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.body== null) ? 0 : this.body.hashCode());
    result = prime * result + ((this.perceptions== null) ? 0 : this.perceptions.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the PerceptionEvent event's attributes only.
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("body  = ").append(this.body);
    result.append("perceptions  = ").append(this.perceptions);
    return result.toString();
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  private final static long serialVersionUID = 3189619065L;
}
