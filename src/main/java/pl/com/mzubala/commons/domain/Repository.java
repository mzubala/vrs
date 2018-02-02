package pl.com.mzubala.commons.domain;

import java.util.Collection;
import java.util.UUID;

public interface Repository<AggregateT extends AggregateRoot> {

  void put(AggregateT agg);

  AggregateT get(UUID id);

  Collection<AggregateT> getAll(Collection<UUID> uids);

}
