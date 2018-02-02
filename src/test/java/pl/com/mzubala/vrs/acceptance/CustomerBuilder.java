package pl.com.mzubala.vrs.acceptance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.com.mzubala.vrs.domain.Customer;
import pl.com.mzubala.vrs.domain.CustomerTest;
import pl.com.mzubala.vrs.domain.repository.CustomerRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class CustomerBuilder {

  private Map<String, UUID> customers = new HashMap<>();

  @Autowired
  private CustomerRepository customerRepository;

  public void build(String name) {
    Customer customer = new Customer(name);
    customers.put(name, customer.id());
    customerRepository.put(customer);
  }

  public UUID get(String name) {
    return customers.get(name);
  }
}
