package pl.com.mzubala.commons.infrastructure;

import pl.com.mzubala.commons.api.CommandGateway;
import pl.com.mzubala.commons.api.Handler;
import pl.com.mzubala.commons.domain.Command;
import pl.com.mzubala.commons.domain.InvalidCommandException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class StandardCommandGateway implements CommandGateway {

  private HandlerRegistry handlerRegistry;

  private Validator validator;

  public StandardCommandGateway(HandlerRegistry handlerRegistry, Validator validator) {
    this.handlerRegistry = handlerRegistry;
    this.validator = validator;
  }

  @Override
  public <ReturnT, CommandT extends Command> ReturnT execute(CommandT cmd) {
    validate(cmd);
    Handler<CommandT, ReturnT> handler = handlerRegistry.handlerFor(cmd);
    return handler.handle(cmd);
  }

  private <CommandT extends Command> void validate(CommandT cmd) {
    Set<ConstraintViolation<CommandT>> errors = validator.validate(cmd);
    if(!errors.isEmpty())
      throw new InvalidCommandException(errors);
  }
}
