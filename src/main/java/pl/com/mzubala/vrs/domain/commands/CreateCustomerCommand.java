package pl.com.mzubala.vrs.domain.commands;

import org.hibernate.validator.constraints.NotBlank;
import pl.com.mzubala.commons.domain.Command;

public class CreateCustomerCommand implements Command {

  @NotBlank
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
