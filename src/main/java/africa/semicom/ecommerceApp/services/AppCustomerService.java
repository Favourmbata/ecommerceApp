package africa.semicom.ecommerceApp.services;

import africa.semicom.ecommerceApp.dtos.request.CustomerRegistrationRequest;
import africa.semicom.ecommerceApp.dtos.request.UpdateCustomerRequest;
import africa.semicom.ecommerceApp.dtos.response.ApiResponse;
import africa.semicom.ecommerceApp.dtos.response.CustomerRegistrationResponse;
import africa.semicom.ecommerceApp.dtos.response.UpdateCustomerResponse;
import africa.semicom.ecommerceApp.exceptions.CustomerNotFoundException;
import africa.semicom.ecommerceApp.models.Customer;
import africa.semicom.ecommerceApp.repositories.CustomerRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
//reflection is used to inspect.you can use refection to gwt field
// jsonpatch has three field the name of the field the path and the value associated with it
// get declared field  return the field from the request object
// get class returns the type
// field object[]1
// field.get returns the value associated in request if it is not null.
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.stream;
@Service
@AllArgsConstructor
public class AppCustomerService implements CustomerService {
 private final CustomerRepository customerRepository;


 @Override
 public CustomerRegistrationResponse register(CustomerRegistrationRequest request) {
  Customer customer = new Customer();
  customer.setEmail(request.getEmail());
  customer.setPassword(request.getPassword());

  Customer savedCustomer = customerRepository.save(customer);

  CustomerRegistrationResponse response = new CustomerRegistrationResponse();
  response.setId(savedCustomer.getId());
  return response;
 }

 @Override
 public ApiResponse<UpdateCustomerResponse> updateCustomer(Long id,
                                                           UpdateCustomerRequest
                                                                   request) throws CustomerNotFoundException {
  Customer customer = findCustomerBy(id);
  List<JsonPatchOperation> jsonPatchOperations = new ArrayList<>();
  buildPatchOperations(request, jsonPatchOperations);
  customer = applyPatch(jsonPatchOperations, customer);
  customerRepository.save(customer);
  return new ApiResponse<>(buildUpdateCustomerResponse());
 }

 private static UpdateCustomerResponse buildUpdateCustomerResponse() {
  UpdateCustomerResponse response = new UpdateCustomerResponse();
  response.setMessage("Account updated successfully");
  return response;
 }

 private static void buildPatchOperations(UpdateCustomerRequest request, List<JsonPatchOperation> jsonPatchOperations) {
  Class<?> c = request.getClass();
  Field[] fields = c.getDeclaredFields();
  System.out.println(Arrays.toString(fields));
  stream(fields)
          .filter(field->isValidUpdate(field, request))
          .forEach(field->addOperation(request, field, jsonPatchOperations));
 }

 private static Customer applyPatch(List<JsonPatchOperation> jsonPatchOperations, Customer customer) {
  try {
   ObjectMapper mapper = new ObjectMapper();
   JsonPatch jsonPatch = new JsonPatch(jsonPatchOperations);
   //1. convert Customer to JsonNode
   JsonNode customerNode = mapper.convertValue(customer, JsonNode.class);
   //2. apply JsonPatch to customerNode
   JsonNode updatedNode = jsonPatch.apply(customerNode);
   //3. convert customerNode to Customer
   customer = mapper.convertValue(updatedNode, Customer.class);

  }catch (Exception exception){
   throw new RuntimeException(exception);
  }
  return customer;
 }

 private static boolean isValidUpdate(Field field, UpdateCustomerRequest request) {
  field.setAccessible(true);
  try {
   return field.get(request) != null;

  } catch (IllegalAccessException e) {
   throw new RuntimeException(e);
  }
 }

 private static void addOperation(UpdateCustomerRequest request, Field field, List<JsonPatchOperation> jsonPatchOperations) {
  try {
   JsonPointer path = new JsonPointer("/"+ field.getName());
   JsonNode value = new TextNode(field.get(request).toString());
   ReplaceOperation replaceOperation = new ReplaceOperation(path, value);
   jsonPatchOperations.add(replaceOperation);
  } catch (Exception e) {
   throw new RuntimeException(e);
  }
 }

 private Customer findCustomerBy(Long id) throws CustomerNotFoundException {
  return customerRepository.findById(id)
          .orElseThrow(() -> new CustomerNotFoundException(
                  String.format("Customer with id %d not found", id)));
 }


}

