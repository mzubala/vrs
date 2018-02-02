package pl.com.mzubala.commons.domain;

import java.util.UUID;

public class NoSuchAggregateException extends RuntimeException {

  public NoSuchAggregateException(UUID uuid, Class<? extends AggregateRoot> klass) {
    super(String.format("no %s with id %s", klass.getName(), uuid.toString()));
  }

}
