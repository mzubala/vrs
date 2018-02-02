package pl.com.mzubala.vrs.domain;

import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RentableTest {

  private final RentableCategory category = RentableCategory.NEW_RELEASE;
  private Rentable sut = new Rentable("tes", category);
  private UUID someCustomer = UUID.randomUUID();
  private UUID otherCustomer = UUID.randomUUID();

  @Test
  public void isAvailableByDefault() {
    assertThat(sut.available()).isTrue();
  }

  @Test
  public void becomesNotAvailableWhenRent() {
    sut.rentTo(someCustomer);

    assertThat(sut.available()).isFalse();
  }

  @Test(expected = RentableNotAvailableException.class)
  public void cannotRentWhenItsAlreadyRent() {
    sut.rentTo(someCustomer);

    sut.rentTo(otherCustomer);
  }

  @Test(expected = RentableNotAvailableException.class)
  public void cantRentTwiceToSameCustomer() {
    sut.rentTo(someCustomer);

    sut.rentTo(someCustomer);
  }

  @Test
  public void delegatesPriceCalculationToCategory() {
    assertThat(sut.price(5)).isEqualTo(category.price(5));
  }

  @Test
  public void delegatesSurchargeCalculationToCategory() {
    assertThat(sut.surcharge(5)).isEqualTo(category.surcharge(5));
  }

  @Test
  public void delgatesBonusPointsCalculationToCategory() {
    assertThat(sut.bonusPoints()).isEqualTo(category.bonusPoints());
  }

  @Test
  public void becomesAvailableAfterReturning() {
    sut.rentTo(someCustomer);

    sut.returned();

    assertThat(sut.available()).isTrue();
  }

}
