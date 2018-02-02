package pl.com.mzubala.vrs.api.handlers;

import pl.com.mzubala.commons.api.Handler;
import pl.com.mzubala.vrs.domain.Rental;
import pl.com.mzubala.vrs.domain.commands.CalculateSurchargesCommand;
import pl.com.mzubala.vrs.domain.prices.PriceCalculation;
import pl.com.mzubala.vrs.domain.repository.RentalRepository;

public class CalculateSurchargesHandler implements Handler<CalculateSurchargesCommand, PriceCalculation> {

  private RentalRepository rentalRepository;

  public CalculateSurchargesHandler(RentalRepository rentalRepository) {
    this.rentalRepository = rentalRepository;
  }

  @Override
  public PriceCalculation handle(CalculateSurchargesCommand cmd) {
    Rental rental = rentalRepository.get(cmd.getRentalId());
    return rental.surcharges();
  }
}
