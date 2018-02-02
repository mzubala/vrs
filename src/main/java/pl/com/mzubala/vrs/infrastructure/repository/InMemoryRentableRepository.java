package pl.com.mzubala.vrs.infrastructure.repository;

import pl.com.mzubala.commons.infrastructure.InMemoryRepository;
import pl.com.mzubala.vrs.domain.Rentable;
import pl.com.mzubala.vrs.domain.RentableCategory;
import pl.com.mzubala.vrs.domain.repository.RentableRepository;

public class InMemoryRentableRepository extends InMemoryRepository<Rentable> implements RentableRepository {

  static {
    Rentable r1 = new Rentable("The Millers", RentableCategory.REGULAR);
    Rentable r2 = new Rentable("The Millers 2", RentableCategory.NEW_RELEASE);
    Rentable r3 = new Rentable("Batman", RentableCategory.OLD_FILM);
    REPOSITORY.put(r1.id(), r1);
    REPOSITORY.put(r2.id(), r2);
    REPOSITORY.put(r3.id(), r3);
  }

}
