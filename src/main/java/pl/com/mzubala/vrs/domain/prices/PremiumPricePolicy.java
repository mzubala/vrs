package pl.com.mzubala.vrs.domain.prices;

import pl.com.mzubala.commons.domain.Money;

public class PremiumPricePolicy implements PricePolicy {

  public static final Money PREMIUM_PRICE = new Money(40.0, "SEK");

  @Override
  public Money price(Integer length) {
    return PREMIUM_PRICE.multiplyBy(length);
  }

  @Override
  public Money surcharge(Integer lateDays) {
    return PREMIUM_PRICE.multiplyBy(lateDays);
  }
}
