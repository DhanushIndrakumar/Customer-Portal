package customer.company.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class AuthResponse {
    private String token; //token which will be generated
}
