package pl.com.mzubala.vrs.adapters.rest;

import java.util.UUID;

public class CreateCustomerResponse {
  private UUID customerId;

  public CreateCustomerResponse(UUID customerId) {
    this.customerId = customerId;
  }

  public UUID getCustomerId() {
    return customerId;
  }

  public void setCustomerId(UUID customerId) {
    this.customerId = customerId;
  }
}
