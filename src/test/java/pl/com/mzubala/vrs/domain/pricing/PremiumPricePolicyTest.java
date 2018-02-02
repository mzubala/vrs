package pl.com.mzubala.vrs.domain.pricing;

import org.junit.Test;
import pl.com.mzubala.vrs.domain.prices.PremiumPricePolicy;

import static org.assertj.core.api.Assertions.assertThat;

public class PremiumPricePolicyTest {

  private PremiumPricePolicy sut = new PremiumPricePolicy();

  @Test
  public void chargesPremiumPriceForEveryDay() {
    assertThat(sut.price(5)).isEqualTo(PremiumPricePolicy.PREMIUM_PRICE.multiplyBy(5));
  }

  @Test
  public void chargesPremiumPriceForEachLateDay() {
    assertThat(sut.surcharge(5)).isEqualTo(PremiumPricePolicy.PREMIUM_PRICE.multiplyBy(5));
  }

}
