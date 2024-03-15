package africa.semicom.ecommerceApp.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRegistrationRequest {
    private String email;
    private String password;
}
