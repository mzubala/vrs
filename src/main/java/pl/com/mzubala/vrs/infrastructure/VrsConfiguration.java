package pl.com.mzubala.vrs.infrastructure;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.com.mzubala.commons.api.CommandGateway;
import pl.com.mzubala.commons.domain.TimeProvider;
import pl.com.mzubala.commons.infrastructure.HandlerRegistry;
import pl.com.mzubala.commons.infrastructure.SpringHandlerRegistry;
import pl.com.mzubala.commons.infrastructure.StandardCommandGateway;
import pl.com.mzubala.commons.infrastructure.time.StandardTimeProvider;
import pl.com.mzubala.vrs.api.handlers.*;
import pl.com.mzubala.vrs.api.read.RentableFinder;
import pl.com.mzubala.vrs.domain.repository.CustomerRepository;
import pl.com.mzubala.vrs.domain.repository.RentableRepository;
import pl.com.mzubala.vrs.domain.repository.RentalRepository;
import pl.com.mzubala.vrs.infrastructure.read.InMemoryRentableFinder;
import pl.com.mzubala.vrs.infrastructure.repository.InMemoryCustomerRepository;
import pl.com.mzubala.vrs.infrastructure.repository.InMemoryRentableRepository;
import pl.com.mzubala.vrs.infrastructure.repository.InMemoryRentalRepository;

import javax.validation.Validator;

@Configuration
public class VrsConfiguration {

  @Bean
  public HandlerRegistry handlerRegistry(ConfigurableListableBeanFactory factory) {
    return new SpringHandlerRegistry(factory);
  }

  @Bean
  public CommandGateway commandGateway(HandlerRegistry handlerRegistry, Validator validator) {
    return new StandardCommandGateway(handlerRegistry, validator);
  }

  @Bean
  public CalculatePriceHandler calculatePriceHandler(RentableRepository rentableRepository, TimeProvider timeProvider) {
    return new CalculatePriceHandler(rentableRepository, timeProvider);
  }

  @Bean
  public RentHandler rentHandler(RentableRepository rentableRepository, CustomerRepository customerRepository,
                                 TimeProvider timeProvider, RentalRepository rentalRepository) {
    return new RentHandler(rentableRepository, customerRepository, rentalRepository, timeProvider);
  }

  @Bean
  public RentableRepository rentableRepository() {
    return new InMemoryRentableRepository();
  }

  @Bean
  public CustomerRepository customerRepository() {
    return new InMemoryCustomerRepository();
  }

  @Bean
  public RentalRepository rentalRepository() {
    return new InMemoryRentalRepository();
  }

  @Bean
  public TimeProvider timeProvider() {
    return new StandardTimeProvider();
  }

  @Bean
  public CalculateSurchargesHandler calculateSurchargesHandler(RentalRepository rentalRepository) {
    return new CalculateSurchargesHandler(rentalRepository);
  }

  @Bean
  public ReturnHandler returnHandler(RentableRepository rentableRepository) {
    return new ReturnHandler(rentableRepository);
  }

  @Bean
  public RentableFinder rentableFinder() {
    return new InMemoryRentableFinder();
  }

  @Bean
  public CreateCustomerHandler createCustomerHandler(CustomerRepository customerRepository) {
    return new CreateCustomerHandler(customerRepository);
  }

}
