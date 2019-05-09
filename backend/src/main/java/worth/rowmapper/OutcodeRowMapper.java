package worth.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import worth.model.Outcode;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OutcodeRowMapper implements RowMapper<Outcode> {
    @Override
    public Outcode mapRow(ResultSet rs, int rowNum) throws SQLException {

        return Outcode.builder()
                .id(rs.getLong("ID"))
                .outcode(rs.getString("OUTCODE"))
                .latitude(rs.getDouble("LAT"))
                .longitude(rs.getDouble("LNG"))
                .build();
    }
}
