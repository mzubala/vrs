package pl.com.mzubala.vrs.api.read;

import java.util.List;

public interface RentableFinder {

  List<RentableDto> search(RentableSearchCriteria criteria);

}
