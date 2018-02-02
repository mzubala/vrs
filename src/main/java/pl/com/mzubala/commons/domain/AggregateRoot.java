package pl.com.mzubala.commons.domain;

import java.util.UUID;

public abstract class AggregateRoot {

  private UUID id = UUID.randomUUID();

  public UUID id() {
    return id;
  }

}
