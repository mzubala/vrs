package pl.com.mzubala.vrs.adapters.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mzubala.commons.api.CommandGateway;
import pl.com.mzubala.vrs.domain.commands.CreateCustomerCommand;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private CommandGateway commandGateway;

  public CustomerController(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @PostMapping
  public CreateCustomerResponse create(@RequestBody CreateCustomerCommand cmd) {
    UUID id = commandGateway.execute(cmd);
    return new CreateCustomerResponse(id);
  }

}
