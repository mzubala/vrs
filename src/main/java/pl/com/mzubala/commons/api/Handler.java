package pl.com.mzubala.commons.api;

import pl.com.mzubala.commons.domain.Command;

public interface Handler<CommandT extends Command, ReturnT> {

  ReturnT handle(CommandT cmd);

}
