package pl.com.mzubala.vrs.api.handlers;

import pl.com.mzubala.commons.api.Handler;
import pl.com.mzubala.vrs.domain.commands.ReturnCommand;
import pl.com.mzubala.vrs.domain.repository.RentableRepository;

public class ReturnHandler implements Handler<ReturnCommand, Void> {

  private RentableRepository rentableRepository;

  public ReturnHandler(RentableRepository rentableRepository) {
    this.rentableRepository = rentableRepository;
  }

  @Override
  public Void handle(ReturnCommand cmd) {
    rentableRepository.getAll(cmd.getToReturn()).forEach((rentable -> rentable.returned()));
    return null;
  }
}
