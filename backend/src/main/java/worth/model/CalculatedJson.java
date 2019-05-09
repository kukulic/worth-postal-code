package worth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CalculatedJson {

    private final String unit = "km";

    private List<PostalcodeDegree> postalcodeList;
    private BigDecimal distance;
    private String error;

}
