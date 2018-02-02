package pl.com.mzubala.vrs.adapters.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.com.mzubala.commons.domain.InvalidCommandException;
import pl.com.mzubala.commons.domain.NoSuchAggregateException;
import pl.com.mzubala.vrs.domain.RentableNotAvailableException;

@ControllerAdvice
public class ErrorHandling {

  @ExceptionHandler(InvalidCommandException.class)
  public ResponseEntity<InvalidCommandException.ValidationErrors> handleInvalidCommand(InvalidCommandException ex) {
    return new ResponseEntity<InvalidCommandException.ValidationErrors>(ex.getErrors(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(NoSuchAggregateException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Specified id is invalid")
  public void handleNoSuchAggregate() {}

  @ExceptionHandler(RentableNotAvailableException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Specified rentable has already been rent")
  public void handleRentableNotAvailable() {}

}
