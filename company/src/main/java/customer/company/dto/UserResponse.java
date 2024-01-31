package customer.company.dto;

import lombok.Data;

@Data

public class UserResponse {
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
