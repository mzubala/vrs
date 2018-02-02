package pl.com.mzubala.vrs.api.handlers;

import pl.com.mzubala.commons.api.Handler;
import pl.com.mzubala.vrs.domain.Customer;
import pl.com.mzubala.vrs.domain.commands.CreateCustomerCommand;
import pl.com.mzubala.vrs.domain.repository.CustomerRepository;

import java.util.UUID;

public class CreateCustomerHandler implements Handler<CreateCustomerCommand, UUID> {

  private CustomerRepository customerRepository;

  public CreateCustomerHandler(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public UUID handle(CreateCustomerCommand cmd) {
    Customer customer = new Customer(cmd.getName());
    customerRepository.put(customer);
    return customer.id();
  }
}
