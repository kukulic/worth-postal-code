package worth.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import worth.model.FullPostcode;


public interface FullPostcodeRepository {

    String deleteFullPostcode(Long id);

    String updateFullPostcode(FullPostcode fullPostcode);

    FullPostcode getFullPostcode(String postcode) throws EmptyResultDataAccessException;
}
