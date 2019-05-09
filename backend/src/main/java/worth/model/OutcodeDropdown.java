package worth.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OutcodeDropdown {

    private String value;
    private String label;
}
