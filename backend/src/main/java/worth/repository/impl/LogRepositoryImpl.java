package worth.repository.impl;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import worth.repository.LogRepository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class LogRepositoryImpl implements LogRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public LogRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public int insertLog(String firstPostCode, String secondPostCode, double distance) {

        String query = "INSERT INTO log_postcode_distance \n" +
                "(post_code_1, post_code_2, distance_km, insert_time) \n" +
                "VALUES(:firstPostCode, :secondPostCode, :distance, current_timestamp)";

        Map<String,Object> params = new HashMap<>();
        params.put("firstPostCode", firstPostCode);
        params.put("secondPostCode", secondPostCode);
        params.put("distance", distance);

        return namedParameterJdbcTemplate.update(query, params);
    }
}
