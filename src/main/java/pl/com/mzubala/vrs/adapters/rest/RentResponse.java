package pl.com.mzubala.vrs.adapters.rest;

import java.util.UUID;

public class RentResponse {

  private UUID rentalId;

  public RentResponse(UUID rentalId) {
    this.rentalId = rentalId;
  }

  public UUID getRentalId() {
    return rentalId;
  }

  public void setRentalId(UUID rentalId) {
    this.rentalId = rentalId;
  }
}
