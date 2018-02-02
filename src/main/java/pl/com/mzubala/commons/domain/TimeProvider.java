package pl.com.mzubala.commons.domain;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TimeProvider {

  Clock clock();

  default LocalDateTime now() {
    return LocalDateTime.now(clock());
  }

  default LocalDate today() {
    return LocalDate.now(clock());
  }

}
