package pl.com.mzubala.vrs.infrastructure.repository;

import pl.com.mzubala.commons.domain.Repository;
import pl.com.mzubala.commons.infrastructure.InMemoryRepository;
import pl.com.mzubala.vrs.domain.Customer;
import pl.com.mzubala.vrs.domain.repository.CustomerRepository;

public class InMemoryCustomerRepository extends InMemoryRepository<Customer> implements CustomerRepository {
}
