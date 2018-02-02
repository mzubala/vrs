package pl.com.mzubala.vrs.domain;

import pl.com.mzubala.commons.domain.AggregateRoot;

public class Customer extends AggregateRoot {

  private String name;

  private int bonusPoints;

  public Customer(String name) {
    this.name = name;
  }

  public void giveBonus(int bonus) {
    bonusPoints += bonus;
  }

  public int bonusPoints() {
    return bonusPoints;
  }
}
