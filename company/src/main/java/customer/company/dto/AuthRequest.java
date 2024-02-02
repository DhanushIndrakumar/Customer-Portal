package customer.company.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter

public class AuthRequest {
    //Getting authenticated through providing email and password
    private String email;
    private String password;
}
