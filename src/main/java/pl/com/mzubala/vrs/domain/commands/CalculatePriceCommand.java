package pl.com.mzubala.vrs.domain.commands;

import java.util.Collection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.hibernate.validator.constraints.NotEmpty;
import pl.com.mzubala.commons.domain.Command;

public class CalculatePriceCommand implements Command {

  @NotEmpty
  private Map<UUID, Integer> toRent = new HashMap<>();

  public Map<UUID, Integer> getToRent() {
    return toRent;
  }

  public void setToRent(Map<UUID, Integer> toRent) {
    this.toRent = toRent;
  }

  public void addItem(UUID rentableId, Integer days) {
    toRent.put(rentableId, days);
  }

  public Collection<UUID> getRenatbleIds() {
    if(toRent != null)
      return toRent.keySet();
    else
      return null;
  }

  public Integer getDays(UUID id) {
    return toRent.get(id);
  }
}
