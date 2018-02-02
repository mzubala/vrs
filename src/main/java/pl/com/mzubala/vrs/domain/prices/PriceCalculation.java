package pl.com.mzubala.vrs.domain.prices;

import com.google.common.base.Objects;
import pl.com.mzubala.commons.domain.Money;
import pl.com.mzubala.vrs.domain.Rentable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PriceCalculation {

  List<PriceCalculationItem> items = new LinkedList<>();
  private boolean total;

  public PriceCalculation(Collection<PriceCalculationItem> items) {
    this.items.addAll(items);
  }

  public List<PriceCalculationItem> getItems() {
    return items;
  }

  public Money getTotal() {
    return
        items.stream().reduce(Money.ZERO, (acc, item) -> acc.add(item.getPrice()), (m1, m2) -> m1.add(m2));
  }

  public static class PriceCalculationItem {
    private UUID rentableId;
    private String name;
    private Money price;
    private Integer days;

    public PriceCalculationItem(UUID rentableId, String name, Money money, Integer days) {
      this.rentableId = rentableId;
      this.name = name;
      this.price = money;
      this.days = days;
    }

    public UUID getRentableId() {
      return rentableId;
    }

    public String getName() {
      return name;
    }

    public Money getPrice() {
      return price;
    }

    public Integer getDays() {
      return days;
    }

    @Override
    public String toString() {
      return "PriceCalculationItem{" +
          "rentableId=" + rentableId +
          ", name='" + name + '\'' +
          ", price=" + price +
          ", days=" + days +
          '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      PriceCalculationItem that = (PriceCalculationItem) o;
      return Objects.equal(rentableId, that.rentableId) &&
          Objects.equal(name, that.name) &&
          Objects.equal(price, that.price) &&
          Objects.equal(days, that.days);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(rentableId, name, price, days);
    }
  }

}
