package pl.com.mzubala.vrs.infrastructure.repository;

import pl.com.mzubala.commons.domain.Repository;
import pl.com.mzubala.commons.infrastructure.InMemoryRepository;
import pl.com.mzubala.vrs.domain.Rental;
import pl.com.mzubala.vrs.domain.repository.RentalRepository;

public class InMemoryRentalRepository extends InMemoryRepository<Rental> implements RentalRepository {
}
