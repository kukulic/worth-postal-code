package worth.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import worth.model.Outcode;
import worth.model.OutcodeDropdown;

import java.util.List;

public interface OutcodeRepository {

    Outcode getOutcode(Long id);

    List<OutcodeDropdown> getAllOutcodes();

    String deleteOutcode(Long id);

    String updateOutcode(Outcode outcode);

    Outcode getOutcode(String outCode) throws EmptyResultDataAccessException;

}
