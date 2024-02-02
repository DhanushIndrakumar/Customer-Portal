package customer.company.dto;

import lombok.Builder;
import lombok.Data;

@Data


public class UserDTO {
    private Long userId;

    private String first_name;

    private String last_name;

    private String street;

    private String address;

    private String city;

    private String state;

    private String email;

    private String phone;

}
