package pl.com.mzubala.vrs.domain.prices;

import pl.com.mzubala.commons.domain.Money;

public class BasicPricePolicy implements PricePolicy {

  public static final Money BASIC_PRICE = new Money(30, "SEK");

  private Integer basicDays;

  public BasicPricePolicy(Integer basicDays) {
    this.basicDays = basicDays;
  }

  @Override
  public Money price(Integer length) {
    return BASIC_PRICE.add(BASIC_PRICE.multiplyBy(Math.max(0, length - basicDays)));
  }

  @Override
  public Money surcharge(Integer lateDays) {
    return BASIC_PRICE.multiplyBy(lateDays);
  }
}
