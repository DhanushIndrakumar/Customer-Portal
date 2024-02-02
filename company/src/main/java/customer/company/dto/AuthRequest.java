package customer.company.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
public class AuthRequest {
    private String email;
    private String password;
}
