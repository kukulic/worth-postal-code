package worth.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Outcode {

    private Long id;
    private String outcode;
    private double latitude;
    private double longitude;
}
