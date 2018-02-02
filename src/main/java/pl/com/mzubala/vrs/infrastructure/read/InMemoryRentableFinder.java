package pl.com.mzubala.vrs.infrastructure.read;

import pl.com.mzubala.vrs.api.read.RentableDto;
import pl.com.mzubala.vrs.api.read.RentableFinder;
import pl.com.mzubala.vrs.api.read.RentableSearchCriteria;
import pl.com.mzubala.vrs.domain.Rentable;
import pl.com.mzubala.vrs.infrastructure.repository.InMemoryRentableRepository;

import java.util.LinkedList;
import java.util.List;

public class InMemoryRentableFinder implements RentableFinder {

  @Override
  public List<RentableDto> search(RentableSearchCriteria criteria) {
    return InMemoryRentableRepository.REPOSITORY.values().stream().reduce(new LinkedList<RentableDto>(), (acc, aggregateRoot) -> {
      if (aggregateRoot instanceof Rentable)
        acc.add(new RentableDto((Rentable) aggregateRoot));
      return acc;
    }, (acc1, acc2) -> {
      LinkedList<RentableDto> combined = new LinkedList<>(acc1);
      combined.addAll(acc2);
      return combined;
    });
  }

}
