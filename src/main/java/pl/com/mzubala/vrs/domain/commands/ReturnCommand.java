package pl.com.mzubala.vrs.domain.commands;

import org.hibernate.validator.constraints.NotEmpty;
import pl.com.mzubala.commons.domain.Command;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class ReturnCommand implements Command {

  @NotEmpty
  private Collection<UUID> toReturn = new HashSet<>();

  public Collection<UUID> getToReturn() {
    return toReturn;
  }

  public void setToReturn(Collection<UUID> toReturn) {
    this.toReturn = toReturn;
  }

  public void addToReturn(UUID uuid) {
    toReturn.add(uuid);
  }
}
