package pl.com.mzubala.vrs.acceptance;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.com.mzubala.commons.domain.TimeProvider;
import pl.com.mzubala.commons.infrastructure.time.StandardTimeProvider;
import pl.com.mzubala.commons.infrastructure.time.TimeMachine;

@Configuration
public class TestConfig {

  @Bean
  public TimeMachine timeProvider() {
    return new TimeMachine();
  }

}
