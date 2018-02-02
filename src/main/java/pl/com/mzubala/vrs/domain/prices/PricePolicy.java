package pl.com.mzubala.vrs.domain.prices;

import pl.com.mzubala.commons.domain.Money;

public interface PricePolicy {

  Money price(Integer length);

  Money surcharge(Integer lateDays);
}
