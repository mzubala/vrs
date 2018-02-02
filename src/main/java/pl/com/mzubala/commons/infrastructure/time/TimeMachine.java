package pl.com.mzubala.commons.infrastructure.time;

import pl.com.mzubala.commons.domain.TimeProvider;

import java.time.Clock;
import java.time.Duration;

public class TimeMachine implements TimeProvider {

  private Clock clock = Clock.systemUTC();

  @Override
  public Clock clock() {
    return clock;
  }

  public TimeTravel travel() {
    return new TimeTravel();
  }

  public class TimeTravel {

    public TimeTravel days(int days) {
      return updateClock(Duration.ofDays(days));
    }

    public TimeTravel hours(int hours) {
      return updateClock(Duration.ofHours(hours));
    }

    public TimeTravel minutes(int min) {
      return updateClock(Duration.ofMinutes(min));
    }

    private TimeTravel updateClock(Duration duration) {
      clock = Clock.offset(clock, duration);
      return this;
    }

  }

}
