package pl.com.mzubala.commons.domain;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InvalidCommandException extends RuntimeException {

  private ValidationErrors errors;

  public <CommandT extends Command> InvalidCommandException(Set<ConstraintViolation<CommandT>> errors) {
    super("Invalid command params provided");
    this.errors = new ValidationErrors(errors);
  }

  public ValidationErrors getErrors() {
    return errors;
  }

  public class ValidationErrors {

    private Map<String, String> errors = new HashMap<>();

    <CommandT extends Command> ValidationErrors(Set<ConstraintViolation<CommandT>> errors) {
      errors.forEach((v) -> {
        this.errors.put(v.getPropertyPath().toString(), v.getMessage());
      });
    }

    public Map<String, String> getErrors() {
      return errors;
    }

  }

}
