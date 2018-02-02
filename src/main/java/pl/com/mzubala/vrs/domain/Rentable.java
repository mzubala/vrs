package pl.com.mzubala.vrs.domain;

import pl.com.mzubala.commons.domain.AggregateRoot;
import pl.com.mzubala.commons.domain.Money;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class Rentable extends AggregateRoot {

  private String name;
  private RentableCategory category;

  private Optional<UUID> rentingCustomerId = Optional.empty();

  public Rentable(String name, RentableCategory category) {
    this.name = name;
    this.category = category;
  }

  public String name() {
    return name;
  }

  public RentableCategory category() {
    return category;
  }

  public Money price(Integer days) {
    return category.price(days);
  }

  public void rentTo(UUID customerId) {
    if(rentingCustomerId.isPresent())
      throw new RentableNotAvailableException(id());
    rentingCustomerId = Optional.of(customerId);
  }

  boolean rentBy(UUID customerId) {
    return rentingCustomerId.isPresent() && rentingCustomerId.get().equals(customerId);
  }

  public boolean available() {
    return !rentingCustomerId.isPresent();
  }

  public Money surcharge(Integer lateDays) {
    return category.surcharge(lateDays);
  }

  public void returned() {
    rentingCustomerId = Optional.empty();
  }

  public int bonusPoints() {
    return category.bonusPoints();
  }
}
