package pl.com.mzubala.vrs.domain;

import pl.com.mzubala.commons.domain.Money;
import pl.com.mzubala.vrs.domain.prices.BasicPricePolicy;
import pl.com.mzubala.vrs.domain.prices.PremiumPricePolicy;
import pl.com.mzubala.vrs.domain.prices.PricePolicy;

public enum RentableCategory {

  NEW_RELEASE(new PremiumPricePolicy(), 2),
  REGULAR(new BasicPricePolicy(3), 1),
  OLD_FILM(new BasicPricePolicy(5), 1);

  private PricePolicy pricePolicy;

  private int bonusPoints;

  RentableCategory(PricePolicy pricePolicy, int bonusPoints) {
    this.pricePolicy = pricePolicy;
    this.bonusPoints = bonusPoints;
  }

  public Money price(Integer length) {
    return pricePolicy.price(length);
  }

  public Money surcharge(Integer lateDays) {
    return pricePolicy.surcharge(lateDays);
  }

  public int bonusPoints() {
    return bonusPoints;
  }
}
