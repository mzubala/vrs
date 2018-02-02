package pl.com.mzubala.commons.domain;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;

public class Money implements Serializable {

  public static final Currency DEFAULT_CURRENCY = Currency.getInstance("EUR");

  public static final Money ZERO = new Money(BigDecimal.ZERO);

  private BigDecimal denomination;

  private String currencyCode;

  protected Money() {
  }

  public Money(BigDecimal denomination, Currency currency) {
    this(denomination, currency.getCurrencyCode());
  }

  private Money(BigDecimal denomination, String currencyCode) {
    this.denomination = denomination.setScale(2, RoundingMode.HALF_EVEN);
    this.currencyCode = currencyCode;
  }

  public Money(BigDecimal denomination) {
    this(denomination, DEFAULT_CURRENCY);
  }

  public Money(double denomination, Currency currency) {
    this(new BigDecimal(denomination), currency.getCurrencyCode());
  }

  public Money(double denomination, String currencyCode) {
    this(new BigDecimal(denomination), currencyCode);
  }

  public Money(double denomination) {
    this(denomination, DEFAULT_CURRENCY);
  }

  public Money multiplyBy(double multiplier) {
    return multiplyBy(new BigDecimal(multiplier));
  }

  public Money multiplyBy(BigDecimal multiplier) {
    return new Money(denomination.multiply(multiplier), currencyCode);
  }

  public Money add(Money money) {
    if (!compatibleCurrency(money)) {
      throw new IllegalArgumentException("Currency mismatch");
    }

    return new Money(denomination.add(money.denomination), determineCurrencyCode(money));
  }

  public Money subtract(Money money) {
    if (!compatibleCurrency(money)) {
      throw new IllegalArgumentException("Currency mismatch");
    }
    return new Money(denomination.subtract(money.denomination), determineCurrencyCode(money));
  }

  private boolean compatibleCurrency(Money money) {
    return isZero(denomination) || isZero(money.denomination)
        || currencyCode.equals(money.getCurrencyCode());
  }

  private boolean isZero(BigDecimal testedValue) {
    return BigDecimal.ZERO.compareTo(testedValue) == 0;
  }

  private Currency determineCurrencyCode(Money otherMoney) {
    String resultingCurrenctCode = isZero(denomination) ? otherMoney.currencyCode : currencyCode;
    return Currency.getInstance(resultingCurrenctCode);
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public Currency getCurrency() {
    return Currency.getInstance(currencyCode);
  }

  public Double getAmount() {
    return denomination.doubleValue();
  }

  public boolean greaterThan(Money other) {
    return denomination.compareTo(other.denomination) > 0;
  }

  public boolean lessThan(Money other) {
    return denomination.compareTo(other.denomination) < 0;
  }

  public boolean lessOrEquals(Money other) {
    return denomination.compareTo(other.denomination) <= 0;
  }

  @Override
  public String toString() {
    return String.format("%0$.2f %s", denomination, getCurrency().getSymbol());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Money money = (Money) o;
    return Objects.equal(denomination, money.denomination)
        && Objects.equal(currencyCode, money.currencyCode);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(denomination, currencyCode);
  }

  public static Money parse(String s) {
    String[] split = s.split(" ");
    Currency currency = DEFAULT_CURRENCY;
    if(split.length > 1)
      currency = Currency.getInstance(split[1].trim());
    Number v = null;
    try {
      v = NumberFormat.getInstance().parse(split[0]);
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
    return new Money(v.doubleValue(), currency);
  }
}