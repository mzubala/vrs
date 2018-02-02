package pl.com.mzubala.vrs.domain;

import pl.com.mzubala.commons.domain.AggregateRoot;
import pl.com.mzubala.commons.domain.TimeProvider;
import pl.com.mzubala.vrs.domain.prices.PriceCalculation;
import pl.com.mzubala.vrs.domain.repository.RentableRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Rental extends AggregateRoot {

  private final TimeProvider timeProvider;
  private final RentableRepository rentableRepository;
  private LocalDate rentAt;
  private UUID customerId;

  private Map<UUID, Integer> rentItems = new HashMap<>();

  public Rental(UUID customerId, Map<UUID, Integer> rentItems, TimeProvider timeProvider, RentableRepository rentableRepository) {
    this.customerId = customerId;
    this.timeProvider = timeProvider;
    this.rentableRepository = rentableRepository;
    this.rentItems.putAll(rentItems);
    this.rentAt = timeProvider.today();
  }

  public Rental(Map<UUID, Integer> rentItems, TimeProvider timeProvider, RentableRepository rentableRepository) {
    this(null, rentItems, timeProvider, rentableRepository);
  }

  public PriceCalculation surcharges() {
    Integer rentalLength = Math.toIntExact(ChronoUnit.DAYS.between(rentAt, timeProvider.today()));
    return calculate((rentable) -> {
      if (!rentable.rentBy(customerId))
        return Optional.empty();
      Integer initialDays = rentItems.get(rentable.id());
      Integer lateDays = Math.max(rentalLength - initialDays, 0);
      return Optional.of(new PriceCalculation.PriceCalculationItem(rentable.id(), rentable.name(),
          rentable.surcharge(lateDays), lateDays));
    });
  }

  public PriceCalculation prices() {
    return calculate((rentable) -> {
      Integer days = rentItems.get(rentable.id());
      return Optional.of(new PriceCalculation.PriceCalculationItem(rentable.id(), rentable.name(),
          rentable.price(days), days));
    });
  }

  private PriceCalculation calculate(Function<Rentable, Optional<PriceCalculation.PriceCalculationItem>> f) {
    Collection<PriceCalculation.PriceCalculationItem> items = rentables().stream().
        reduce(new HashSet<>(), (acc, rentable) -> {
          Optional<PriceCalculation.PriceCalculationItem> optItem = f.apply(rentable);
          if (optItem.isPresent())
            acc.add(optItem.get());
          return acc;
        }, (acc1, acc2) -> {
          HashSet<PriceCalculation.PriceCalculationItem> combined = new HashSet<>(acc1);
          combined.addAll(acc2);
          return combined;
        });
    return new PriceCalculation(items);
  }

  private Collection<Rentable> rentables() {
    return rentableRepository.getAll(rentItems.keySet());
  }

  public int bonusPoints() {
    return rentables().stream().collect(Collectors.summingInt(Rentable::bonusPoints));
  }
}
