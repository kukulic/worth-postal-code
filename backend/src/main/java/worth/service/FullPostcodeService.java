package worth.service;

import org.springframework.dao.EmptyResultDataAccessException;
import worth.model.CalculatedJson;
import worth.model.FullPostcode;

public interface FullPostcodeService {

    FullPostcode getFullPostcode(String postcode) throws EmptyResultDataAccessException;

    String updateFullPostcode(FullPostcode fullPostcode);

    String deleteFullPostcode(Long id);

    String calculateDistance(String firstFullPostcode, String secondFullPostcode) throws EmptyResultDataAccessException ;

    CalculatedJson calculateJsonDocument(String firstFullPostcode, String secondFullPostcode);
}
