package pl.com.mzubala.vrs.acceptance;

import cucumber.api.DataTable;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pl.com.mzubala.commons.api.CommandGateway;
import pl.com.mzubala.commons.domain.Money;
import pl.com.mzubala.commons.infrastructure.time.TimeMachine;
import pl.com.mzubala.vrs.VrsApplication;
import pl.com.mzubala.vrs.domain.Customer;
import pl.com.mzubala.vrs.domain.Rentable;
import pl.com.mzubala.vrs.domain.RentableNotAvailableException;
import pl.com.mzubala.vrs.domain.commands.CalculatePriceCommand;
import pl.com.mzubala.vrs.domain.commands.CalculateSurchargesCommand;
import pl.com.mzubala.vrs.domain.commands.RentCommand;
import pl.com.mzubala.vrs.domain.commands.ReturnCommand;
import pl.com.mzubala.vrs.domain.prices.PriceCalculation;
import pl.com.mzubala.vrs.domain.repository.CustomerRepository;
import pl.com.mzubala.vrs.domain.repository.RentableRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ContextConfiguration(classes = {VrsApplication.class, TestConfig.class})
public class RentalStepDefinitionsTest implements En {

  @Autowired
  private CommandGateway commandGateway;

  @Autowired
  private RentableBuilder rentableBuilder;

  @Autowired
  private CustomerBuilder customerBuilder;

  @Autowired
  private RentableRepository rentableRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private TimeMachine timeMachine;

  private RentCommand rentCommand;
  private boolean notAvailableExThrown;
  private PriceCalculation priceCalculation;
  private UUID rentalId;

  public RentalStepDefinitionsTest() {
    {

      Given("^inventory contains following items:$", (DataTable rentablesTable) -> {
        rentableBuilder.buildAll(rentablesTable);
      });

      Given("^there is a customer \"([^\"]*)\"$", (String name) -> {
        customerBuilder.build(name);
      });

      When("^\"([^\"]*)\" rents following movies:$", (String customerName, DataTable data) -> {
        rent(customerName, data);
      });

      When("^(\\d+) days pass$", (Integer daysCount) -> {
        timeMachine.travel().days(daysCount);
      });

      When("^returns following movies:$", (DataTable toReturn) -> {
        ReturnCommand returnMoviesCommand = new ReturnCommand();
        toReturn.asList(String.class).forEach((name) ->
            returnMoviesCommand.addToReturn(rentableBuilder.get(name).id()));
        commandGateway.execute(returnMoviesCommand);
      });

      Then("^the movies are marked as rent$", () -> {
        Collection<Rentable> rentables = rentableRepository.getAll(rentCommand.getRenatbleIds());
        rentables.stream().forEach((r) -> assertThat(r.available()).isFalse());
      });

      Then("^the system responds that movies are unavailable$", () -> {
        assertThat(notAvailableExThrown).isTrue();
      });

      Then("^customer gets following prices:$", (DataTable pricesTable) -> {
        calculatePrices(pricesTable);
        assertPrices(pricesTable);
      });

      Then("^the total price is \"([^\"]*)\"$", (String total) -> {
        Money expected = Money.parse(total);
        assertThat(priceCalculation.getTotal()).isEqualTo(expected);
      });

      Then("^the system calculates following surcharges:$", (DataTable expectedSurcharges) -> {
        CalculateSurchargesCommand calculateSurcharges = new CalculateSurchargesCommand();
        calculateSurcharges.setRentalId(rentalId);
        priceCalculation = commandGateway.execute(calculateSurcharges);
        assertPrices(expectedSurcharges);
      });

      Then("^\"([^\"]*)\" is available for rent$", (String name) -> {
        Rentable rentable = rentableRepository.get(rentableBuilder.get(name).id());
        assertThat(rentable.available()).isTrue();
      });

      Then("^\"([^\"]*)\" has (\\d+) bonus points$", (String customerName, Integer bonusPoints) -> {
        Customer customer = customerRepository.get(customerBuilder.get(customerName));
        assertThat(customer.bonusPoints()).isEqualTo(bonusPoints);
      });

    }
  }

  private void assertPrices(DataTable pricesTable) {
    DataTable actual = toDataTable(priceCalculation);
    actual.unorderedDiff(pricesTable);
  }

  private DataTable toDataTable(PriceCalculation calculation) {
    List raw = calculation.getItems().stream().map((item) -> {
      return Arrays.asList(item.getName(), item.getDays(), item.getPrice().toString());
    }).collect(Collectors.toList());
    raw.add(0, Arrays.asList("name", "length", "money"));
    DataTable table = DataTable.create(raw);
    return table;
  }

  private void calculatePrices(DataTable pricesTable) {
    CalculatePriceCommand calculatePriceCommand = new CalculatePriceCommand();
    pricesTable.asMaps(String.class, String.class).forEach((row) -> {
      Rentable rentable = rentableBuilder.get(row.get("name"));
      calculatePriceCommand.addItem(rentable.id(), Integer.parseInt(row.get("length")));
    });
    priceCalculation = commandGateway.execute(calculatePriceCommand);
  }

  private void rent(String customerName, DataTable toRent) {
    rentCommand = new RentCommand();
    rentCommand.setCustomerId(customerBuilder.get(customerName));
    toRent.asMaps(String.class, String.class).forEach((row) -> {
      Rentable rentable = rentableBuilder.get(row.get("name"));
      rentCommand.addItem(rentable.id(), Integer.parseInt(row.get("length")));
    });
    executeRent();
  }

  private void executeRent() {
    try {
      rentalId = commandGateway.execute(rentCommand);
    } catch (RentableNotAvailableException ex) {
      notAvailableExThrown = true;
    }
  }
}