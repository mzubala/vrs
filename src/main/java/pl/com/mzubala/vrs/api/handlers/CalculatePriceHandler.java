package pl.com.mzubala.vrs.api.handlers;

import pl.com.mzubala.commons.api.Handler;
import pl.com.mzubala.commons.domain.TimeProvider;
import pl.com.mzubala.vrs.domain.Rental;
import pl.com.mzubala.vrs.domain.repository.RentableRepository;
import pl.com.mzubala.vrs.domain.prices.PriceCalculation;
import pl.com.mzubala.vrs.domain.commands.CalculatePriceCommand;

public class CalculatePriceHandler implements Handler<CalculatePriceCommand, PriceCalculation> {

  private RentableRepository rentableRepository;
  private TimeProvider timeProvider;

  public CalculatePriceHandler(RentableRepository rentableRepository, TimeProvider timeProvider) {
    this.rentableRepository = rentableRepository;
    this.timeProvider = timeProvider;
  }

  @Override
  public PriceCalculation handle(CalculatePriceCommand cmd) {
    Rental rental = new Rental(cmd.getToRent(), timeProvider, rentableRepository);
    return rental.prices();
  }
}
