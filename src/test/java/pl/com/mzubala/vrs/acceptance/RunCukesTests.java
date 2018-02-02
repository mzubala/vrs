package pl.com.mzubala.vrs.acceptance;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.util.Locale;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
public class RunCukesTests {

  @BeforeClass
  public static void setLocale() {
    Locale.setDefault(Locale.US);
  }

}
