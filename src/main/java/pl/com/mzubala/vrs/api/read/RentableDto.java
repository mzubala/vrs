package pl.com.mzubala.vrs.api.read;

import pl.com.mzubala.commons.domain.AggregateRoot;
import pl.com.mzubala.vrs.domain.Rentable;
import pl.com.mzubala.vrs.domain.RentableCategory;

import java.util.UUID;

public class RentableDto {

  private UUID id;

  private String name;

  private RentableCategory category;

  public RentableDto(Rentable rentable) {
    name = rentable.name();
    category = rentable.category();
    id = rentable.id();
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RentableCategory getCategory() {
    return category;
  }

  public void setCategory(RentableCategory category) {
    this.category = category;
  }
}
