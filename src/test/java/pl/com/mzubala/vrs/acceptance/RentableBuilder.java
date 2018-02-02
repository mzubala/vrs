package pl.com.mzubala.vrs.acceptance;

import cucumber.api.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.com.mzubala.vrs.domain.Rentable;
import pl.com.mzubala.vrs.domain.RentableCategory;
import pl.com.mzubala.vrs.domain.repository.RentableRepository;

import java.util.HashMap;
import java.util.Map;

@Component
public class RentableBuilder {

  @Autowired
  private RentableRepository rentableRepository;

  private Map<String, Rentable> rentables = new HashMap<>();

  public Rentable get(String name) {
    return rentables.get(name);
  }

  public void buildAll(DataTable rentablesTable) {
    rentablesTable.asMaps(String.class, String.class).forEach((row) -> {
      Rentable rentable = new Rentable(row.get("name"), RentableCategory.valueOf(row.get("category")));
      rentables.put(row.get("name"), rentable);
      rentableRepository.put(rentable);
    });
  }
}
