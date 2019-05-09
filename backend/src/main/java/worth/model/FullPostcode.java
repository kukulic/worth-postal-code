package worth.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FullPostcode {

    private Long id;
    private String postcode;
    private double latitude;
    private double longitude;
}
