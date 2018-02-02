package pl.com.mzubala.commons.infrastructure;

import java.lang.reflect.ParameterizedType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import pl.com.mzubala.commons.domain.AggregateRoot;
import pl.com.mzubala.commons.domain.NoSuchAggregateException;
import pl.com.mzubala.commons.domain.Repository;

import static com.google.common.base.Preconditions.checkNotNull;

public class InMemoryRepository<AggregateT extends AggregateRoot> implements Repository<AggregateT> {

  public static final Map<UUID, AggregateRoot> REPOSITORY = new HashMap<>();

  private Class<AggregateT> klass;

  public InMemoryRepository() {
    klass = (Class<AggregateT>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];
  }

  @Override
  public void put(AggregateT agg) {
    checkNotNull(agg);
    REPOSITORY.put(agg.id(), agg);
  }

  @Override
  public AggregateT get(UUID id) {
    checkNotNull(id);
    AggregateT agg = (AggregateT) REPOSITORY.get(id);
    if (agg == null || !agg.getClass().equals(klass)) {
      throw new NoSuchAggregateException(id, klass);
    }
    return agg;
  }

  @Override
  public Collection<AggregateT> getAll(Collection<UUID> uids) {
    return uids.stream().map(this::get).collect(Collectors.toList());
  }
}
