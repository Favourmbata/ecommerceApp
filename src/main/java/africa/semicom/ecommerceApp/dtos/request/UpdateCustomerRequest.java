package africa.semicom.ecommerceApp.dtos.request;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateCustomerRequest {
    private  String email;
    private String address;
    private String phoneNumber;

}
