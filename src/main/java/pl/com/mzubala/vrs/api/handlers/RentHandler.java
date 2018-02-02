package pl.com.mzubala.vrs.api.handlers;

import pl.com.mzubala.commons.api.Handler;
import pl.com.mzubala.commons.domain.TimeProvider;
import pl.com.mzubala.vrs.domain.Customer;
import pl.com.mzubala.vrs.domain.Rentable;
import pl.com.mzubala.vrs.domain.Rental;
import pl.com.mzubala.vrs.domain.commands.RentCommand;
import pl.com.mzubala.vrs.domain.repository.CustomerRepository;
import pl.com.mzubala.vrs.domain.repository.RentableRepository;
import pl.com.mzubala.vrs.domain.repository.RentalRepository;

import java.util.Collection;
import java.util.UUID;

public class RentHandler implements Handler<RentCommand, UUID> {

  private RentableRepository rentableRepository;
  private CustomerRepository customerRepository;
  private RentalRepository rentalRepository;
  private TimeProvider timeProvider;

  public RentHandler(RentableRepository rentableRepository,
                     CustomerRepository customerRepository,
                     RentalRepository rentalRepository, TimeProvider timeProvider) {
    this.rentableRepository = rentableRepository;
    this.customerRepository = customerRepository;
    this.rentalRepository = rentalRepository;
    this.timeProvider = timeProvider;
  }

  @Override
  public UUID handle(RentCommand cmd) {
    Collection<Rentable> rentables = rentableRepository.getAll(cmd.getRenatbleIds());
    Customer customer = customerRepository.get(cmd.getCustomerId());
    rentables.forEach(rentable -> rentable.rentTo(customer.id()));
    Rental rental = new Rental(cmd.getCustomerId(), cmd.getToRent(), timeProvider, rentableRepository);
    customer.giveBonus(rental.bonusPoints());
    rentalRepository.put(rental);
    return rental.id();
  }
}
