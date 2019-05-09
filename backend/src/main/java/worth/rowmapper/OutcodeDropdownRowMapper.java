package worth.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import worth.model.OutcodeDropdown;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OutcodeDropdownRowMapper implements RowMapper<OutcodeDropdown> {
    @Override
    public OutcodeDropdown mapRow(ResultSet rs, int rowNum) throws SQLException {

        return OutcodeDropdown.builder()
                .value(String.valueOf(rs.getLong("ID")))
                .label(rs.getString("OUTCODE"))
                .build();
    }

}
