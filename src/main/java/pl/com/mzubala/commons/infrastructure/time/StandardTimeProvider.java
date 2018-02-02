package pl.com.mzubala.commons.infrastructure.time;

import pl.com.mzubala.commons.domain.TimeProvider;

import java.time.Clock;

public class StandardTimeProvider implements TimeProvider {
  @Override
  public Clock clock() {
    return Clock.systemUTC();
  }
}
