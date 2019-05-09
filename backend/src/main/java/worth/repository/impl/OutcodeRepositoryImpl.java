package worth.repository.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import worth.model.Outcode;
import worth.model.OutcodeDropdown;
import worth.repository.OutcodeRepository;
import worth.rowmapper.OutcodeDropdownRowMapper;
import worth.rowmapper.OutcodeRowMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OutcodeRepositoryImpl implements OutcodeRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public OutcodeRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                 JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Outcode getOutcode(Long id) {

        String query = "SELECT * FROM outcodepostcodes \n" +
                "WHERE id = :id";

        Map<String,Object> params = new HashMap<>();
        params.put("id", id);

        return namedParameterJdbcTemplate.queryForObject(query, params, new OutcodeRowMapper());
    }

    public List<OutcodeDropdown> getAllOutcodes() {

        String query = "SELECT id, outcode FROM outcodepostcodes ";

        return jdbcTemplate.query(query, new OutcodeDropdownRowMapper());
    }

    public String deleteOutcode(Long id) {

        String query = "DELETE FROM outcodepostcodes \n" +
                "WHERE id = :id";

        Map<String,Object> params = new HashMap<>();
        params.put("id", id);

        return (namedParameterJdbcTemplate.update(query, params) != 0) ? "Delete sucessfully" : "No such postal found";
    }

    public String updateOutcode(Outcode outcode) {

        String query = "UPDATE outcodepostcodes SET outcode = :outcode, lat = :latitude, lng = :longitude \n" +
                "WHERE id = :id";

        Map<String,Object> params = new HashMap<>();
        params.put("id", outcode.getId());
        params.put("outcode", outcode.getOutcode());
        params.put("latitude", outcode.getLatitude());
        params.put("longitude", outcode.getLongitude());

        return (namedParameterJdbcTemplate.update(query, params) != 0) ? "Update sucessfully" : "No such postal found";
    }

    public Outcode getOutcode(String outcode) throws EmptyResultDataAccessException {

        String query = "SELECT id, outcode, lat, lng FROM outcodepostcodes \n" +
                "WHERE outcode = :outcode";

        Map<String,Object> params = new HashMap<>();
        params.put("outcode", outcode);

        return namedParameterJdbcTemplate.queryForObject(query, params, new OutcodeRowMapper());
    }
}
