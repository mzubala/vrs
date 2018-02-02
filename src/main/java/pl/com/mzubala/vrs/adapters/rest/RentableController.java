package pl.com.mzubala.vrs.adapters.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mzubala.vrs.api.read.RentableDto;
import pl.com.mzubala.vrs.api.read.RentableFinder;
import pl.com.mzubala.vrs.api.read.RentableSearchCriteria;

import java.util.List;

@RestController
@RequestMapping("/rentables")
public class RentableController {

  private RentableFinder rentableFinder;

  public RentableController(RentableFinder rentableFinder) {
    this.rentableFinder = rentableFinder;
  }

  @GetMapping
  public List<RentableDto> search(RentableSearchCriteria rentableSearchCriteria) {
    return rentableFinder.search(rentableSearchCriteria);
  }

}
