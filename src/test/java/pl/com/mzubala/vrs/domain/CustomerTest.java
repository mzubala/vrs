package pl.com.mzubala.vrs.domain;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CustomerTest {

  private Customer sut = new Customer("test");

  @Test
  public void hasZeroBonusPointsAtTheBeginning() {
    assertThat(sut.bonusPoints()).isEqualTo(0);
  }

  @Test
  public void keepsTrackOfBonusPoints() {
    sut.giveBonus(5);
    sut.giveBonus(3);

    assertThat(sut.bonusPoints()).isEqualTo(8);
  }

}
