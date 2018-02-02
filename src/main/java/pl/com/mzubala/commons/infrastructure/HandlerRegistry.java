package pl.com.mzubala.commons.infrastructure;

import pl.com.mzubala.commons.api.Handler;
import pl.com.mzubala.commons.domain.Command;

public interface HandlerRegistry {

  Handler handlerFor(Command cmd);
}
