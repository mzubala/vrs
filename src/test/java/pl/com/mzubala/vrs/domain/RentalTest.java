package pl.com.mzubala.vrs.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.mzubala.commons.infrastructure.time.TimeMachine;
import pl.com.mzubala.vrs.domain.prices.PriceCalculation;
import pl.com.mzubala.vrs.domain.repository.RentableRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RentalTest {

  @Mock
  private RentableRepository rentableRepository;

  private UUID customerId = UUID.randomUUID();

  private Rentable r1 = new Rentable("spider man", RentableCategory.NEW_RELEASE);
  private Rentable r2 = new Rentable("gone with the wind", RentableCategory.OLD_FILM);
  private Rentable r3 = new Rentable("matrix", RentableCategory.REGULAR);
  private Rentable r4 = new Rentable("batman", RentableCategory.REGULAR);

  private TimeMachine timeMachine = new TimeMachine();

  private Map<UUID, Integer> rentItems = new HashMap<>();

  private Rental sut;

  @Before
  public void setUp() {
    r1.rentTo(customerId);
    r2.rentTo(customerId);
    r3.rentTo(customerId);
    when(rentableRepository.getAll(anyCollection())).thenReturn(Arrays.asList(r1, r2, r3, r4));
    rentItems.put(r1.id(), 8);
    rentItems.put(r2.id(), 6);
    rentItems.put(r3.id(), 7);
    rentItems.put(r4.id(), 3);
    sut = new Rental(customerId, rentItems, timeMachine, rentableRepository);
  }

  @Test
  public void calculatesPrices() {
    PriceCalculation priceCalculation = sut.prices();

    assertThat(priceCalculation.getItems()).contains(
        new PriceCalculation.PriceCalculationItem(
            r1.id(), r1.name(), r1.price(8), 8
        ),
        new PriceCalculation.PriceCalculationItem(
            r2.id(), r2.name(), r2.price(6), 6
        ),
        new PriceCalculation.PriceCalculationItem(
            r3.id(), r3.name(), r3.price(7), 7
        ),
        new PriceCalculation.PriceCalculationItem(
            r4.id(), r4.name(), r4.price(3), 3
        )
    );
  }

  @Test
  public void calculatesTotalPrices() {
    PriceCalculation priceCalculation = sut.prices();

    assertThat(priceCalculation.getTotal()).isEqualTo(
        r1.price(8).
            add(r2.price(6)).
            add(r3.price(7)).
            add(r4.price(3)));
  }

  @Test
  public void calculatesSurcharges() {
    timeMachine.travel().days(10);

    PriceCalculation priceCalculation = sut.surcharges();

    assertThat(priceCalculation.getItems()).contains(
        new PriceCalculation.PriceCalculationItem(
            r1.id(), r1.name(), r1.surcharge(2), 2
        ),
        new PriceCalculation.PriceCalculationItem(
            r2.id(), r2.name(), r2.surcharge(4), 4
        ),
        new PriceCalculation.PriceCalculationItem(
            r3.id(), r3.name(), r3.surcharge(3), 3
        )
    );
  }

  @Test
  public void calculatesSurchargesTotal() {
    timeMachine.travel().days(10);

    PriceCalculation priceCalculation = sut.surcharges();

    assertThat(priceCalculation.getTotal()).isEqualTo(
        r1.surcharge(2).
            add(r2.surcharge(4).
                add(r3.surcharge(3)))
    );
  }

  @Test
  public void calculatesBonusPoints() {
    assertThat(sut.bonusPoints()).isEqualTo(r1.bonusPoints() + r2.bonusPoints() + r3.bonusPoints() + r4.bonusPoints());
  }

}
