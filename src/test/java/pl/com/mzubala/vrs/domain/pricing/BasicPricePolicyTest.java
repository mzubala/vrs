package pl.com.mzubala.vrs.domain.pricing;

import org.junit.Test;
import pl.com.mzubala.vrs.domain.prices.BasicPricePolicy;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicPricePolicyTest {

  private BasicPricePolicy sut = new BasicPricePolicy(3);

  @Test
  public void calculatesBasicPriceForLengthShorterThanBaiscDays() {
    assertThat(sut.price(2)).isEqualTo(BasicPricePolicy.BASIC_PRICE);
  }

  @Test
  public void calculatesBasicPriceForLengthEqualToBaiscDays() {
    assertThat(sut.price(3)).isEqualTo(BasicPricePolicy.BASIC_PRICE);
  }

  @Test
  public void chargesBasicPriceForEachDayOverBasicDays() {
    assertThat(sut.price(6)).isEqualTo(
        BasicPricePolicy.BASIC_PRICE.add(BasicPricePolicy.BASIC_PRICE.multiplyBy(3))
    );
  }

  @Test
  public void chargesBasicPriceForEachLateDay() {
    assertThat(sut.surcharge(2)).isEqualTo(BasicPricePolicy.BASIC_PRICE.multiplyBy(2));
  }

}
