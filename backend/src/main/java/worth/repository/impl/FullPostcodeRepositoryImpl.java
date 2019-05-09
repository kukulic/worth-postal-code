package worth.repository.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import worth.model.FullPostcode;
import worth.repository.FullPostcodeRepository;
import worth.rowmapper.FullPostcodeRowMapper;

import java.util.HashMap;
import java.util.Map;

@Repository
public class FullPostcodeRepositoryImpl implements FullPostcodeRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public FullPostcodeRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public String deleteFullPostcode(Long id) {

        String query = "DELETE FROM postcodelatlng \n" +
                "WHERE id = :id";

        Map<String,Object> params = new HashMap<>();
        params.put("id", id);

        return (namedParameterJdbcTemplate.update(query, params) != 0) ? "Delete sucessfully" : "No such postal found";
    }

    @Override
    public String updateFullPostcode(FullPostcode fullPostcode) {

        String query = "UPDATE postcodelatlng SET postcode = :postcode, latitude = :latitude, longitude = :longitude \n" +
                "WHERE id = :id";

        Map<String,Object> params = new HashMap<>();
        params.put("id", fullPostcode.getId());
        params.put("postcode", fullPostcode.getPostcode());
        params.put("latitude", fullPostcode.getLatitude());
        params.put("longitude", fullPostcode.getLongitude());

        return (namedParameterJdbcTemplate.update(query, params) != 0) ? "Update sucessfully" : "No such postal found";
    }

    @Override
    public FullPostcode getFullPostcode(String postcode) throws EmptyResultDataAccessException {

        String query = "SELECT * FROM postcodelatlng \n" +
                "WHERE postcode = :postcode";

        Map<String,Object> params = new HashMap<>();
        params.put("postcode", postcode);

        return namedParameterJdbcTemplate.queryForObject(query, params, new FullPostcodeRowMapper());
    }
}
