package pl.com.mzubala.vrs.adapters.rest;

import org.springframework.web.bind.annotation.*;
import pl.com.mzubala.commons.api.CommandGateway;
import pl.com.mzubala.vrs.domain.commands.CalculatePriceCommand;
import pl.com.mzubala.vrs.domain.commands.CalculateSurchargesCommand;
import pl.com.mzubala.vrs.domain.commands.RentCommand;
import pl.com.mzubala.vrs.domain.commands.ReturnCommand;
import pl.com.mzubala.vrs.domain.prices.PriceCalculation;

import java.util.UUID;

@RestController
@RequestMapping("/rentals")
public class RentalController {

  private CommandGateway commandGateway;

  public RentalController(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @PutMapping("/prices")
  public PriceCalculation calculatePrices(@RequestBody CalculatePriceCommand cmd) {
    return commandGateway.execute(cmd);
  }

  @PostMapping
  public RentResponse rent(@RequestBody RentCommand rentCommand) {
    UUID rentalId = commandGateway.execute(rentCommand);
    return new RentResponse(rentalId);
  }

  @GetMapping("/{rentalId}/surcharges")
  public PriceCalculation calculateSurcharges(@PathVariable UUID rentalId) {
    CalculateSurchargesCommand cmd = new CalculateSurchargesCommand();
    cmd.setRentalId(rentalId);
    return commandGateway.execute(cmd);
  }

  @PutMapping("/return")
  public void returnItems(@RequestBody ReturnCommand returnCommand) {
    commandGateway.execute(returnCommand);
  }

}
