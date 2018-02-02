package pl.com.mzubala.vrs.domain.commands;

import pl.com.mzubala.commons.domain.Command;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CalculateSurchargesCommand implements Command {

  @NotNull
  private UUID rentalId;

  public UUID getRentalId() {
    return rentalId;
  }

  public void setRentalId(UUID rentalId) {
    this.rentalId = rentalId;
  }
}
