package pl.com.mzubala.vrs.domain;

import java.util.UUID;

public class RentableNotAvailableException extends RuntimeException {
  public RentableNotAvailableException(UUID id) {
    super(String.format("Rentable %s is not available for rent", id.toString()));
  }
}
