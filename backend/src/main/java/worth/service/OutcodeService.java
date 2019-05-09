package worth.service;

import org.springframework.dao.EmptyResultDataAccessException;
import worth.model.Outcode;
import worth.model.OutcodeDropdown;

import java.util.List;

public interface OutcodeService {

    Outcode getOutcode(Long id);

    List<OutcodeDropdown> getAllOutcodes();

    String updateOutcode(Outcode outcode);

    String deleteOutcode(Long id);

    String calculateDistance(String firstOutcode, String secondOutcode) throws EmptyResultDataAccessException;

}
