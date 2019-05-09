package worth.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import worth.model.FullPostcode;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FullPostcodeRowMapper implements RowMapper<FullPostcode> {
    @Override
    public FullPostcode mapRow(ResultSet rs, int rowNum) throws SQLException {

        return FullPostcode.builder()
                .id(rs.getLong("ID"))
                .postcode(rs.getString("POSTCODE"))
                .latitude(rs.getDouble("LATITUDE"))
                .longitude(rs.getDouble("LONGITUDE"))
                .build();
    }
}
