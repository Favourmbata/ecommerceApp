package africa.semicom.ecommerceApp.services;

import africa.semicom.ecommerceApp.dtos.request.CustomerRegistrationRequest;
import africa.semicom.ecommerceApp.dtos.request.UpdateCustomerRequest;
import africa.semicom.ecommerceApp.dtos.response.ApiResponse;
import africa.semicom.ecommerceApp.dtos.response.CustomerRegistrationResponse;

import africa.semicom.ecommerceApp.dtos.response.UpdateCustomerResponse;
import africa.semicom.ecommerceApp.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService; //field injection


    @Test
    public void registerTest(){
        CustomerRegistrationRequest request = new CustomerRegistrationRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("password");

        CustomerRegistrationResponse response = customerService.register(request);

        assertNotNull(response);
        assertNotNull(response.getId());

    }



    @Test
    public void updateCustomer()throws CustomerNotFoundException {
        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setEmail("johnbello@gmail.com");
        request.setPhoneNumber("0819347889");
        request.setAddress("312,herbert marcualay way,sabo yaba");

      ApiResponse<UpdateCustomerResponse> response =
              customerService.updateCustomer(1L,request);
          assertThat(response).isNotNull();
          assertThat(response.getData().getMessage()).isNotNull();
    }



}
