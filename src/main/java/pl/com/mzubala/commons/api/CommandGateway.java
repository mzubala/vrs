package pl.com.mzubala.commons.api;

import pl.com.mzubala.commons.domain.Command;

public interface CommandGateway {

  <ReturnT, CommandT extends Command> ReturnT execute(CommandT cmd);

}
