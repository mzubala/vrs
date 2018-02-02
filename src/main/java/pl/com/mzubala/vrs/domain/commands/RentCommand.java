package pl.com.mzubala.vrs.domain.commands;

import org.hibernate.validator.constraints.NotEmpty;
import pl.com.mzubala.commons.domain.Command;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RentCommand implements Command {

  @NotEmpty
  private Map<UUID, Integer> toRent = new HashMap<>();

  @NotNull
  private UUID customerId;

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
    return toRent.keySet();
  }

  public Integer getDays(UUID id) {
    return toRent.get(id);
  }

  public UUID getCustomerId() {
    return customerId;
  }

  public void setCustomerId(UUID customerId) {
    this.customerId = customerId;
  }
}
