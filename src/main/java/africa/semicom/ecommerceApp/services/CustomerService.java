package africa.semicom.ecommerceApp.services;

import africa.semicom.ecommerceApp.dtos.request.CustomerRegistrationRequest;
import africa.semicom.ecommerceApp.dtos.request.UpdateCustomerRequest;
import africa.semicom.ecommerceApp.dtos.response.ApiResponse;
import africa.semicom.ecommerceApp.dtos.response.CustomerRegistrationResponse;
import africa.semicom.ecommerceApp.dtos.response.UpdateCustomerResponse;
import africa.semicom.ecommerceApp.exceptions.CustomerNotFoundException;

public interface CustomerService {

    CustomerRegistrationResponse register(CustomerRegistrationRequest request);


    ApiResponse<UpdateCustomerResponse> updateCustomer(Long id, UpdateCustomerRequest request)throws CustomerNotFoundException;



}


