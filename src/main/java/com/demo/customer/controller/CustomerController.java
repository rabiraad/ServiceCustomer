package com.demo.customer.controller;

import com.demo.customer.exception.CustomerNotFoundException;
import com.demo.customer.model.Customer;
import com.demo.customer.repository.CustomerRepository;
import com.demo.customer.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1/")
public class CustomerController {

  @Autowired private CustomerRepository customerRepository;

  @Autowired private CustomerService customerService;

  @GetMapping("/customers")
  @ApiOperation(value = "Returns a list of all customers in the database")
  public List<Customer> getAllCustomers() {
    return customerRepository.findAll();
  }

  @PostMapping("/customers")
  @ApiOperation(
      value =
          "Creates a new customer after validating his phone number\n"
              + "Returns the created customer")
  @ResponseStatus(value = HttpStatus.CREATED)
  public Customer addCustomer(@RequestBody Customer customer) {
    return customerService.saveCustomer(customer);
  }

  @GetMapping("/customers/{id}")
  @ApiOperation(value = "Returns customer with id: id")
  public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(
                () -> new CustomerNotFoundException("Customer with id: " + id + " not found"));
    return ResponseEntity.ok(customer);
  }

  @PutMapping("/customers/{id}")
  @ApiOperation(
      value =
          "Updates customer with id: id after validating the updated phone number\n"
              + "Returns the updated customer")
  public ResponseEntity<Customer> updateCustomer(
      @PathVariable Long id, @RequestBody Customer updatedCustomer) {
    Customer savedCustomer = customerService.updateCustomer(updatedCustomer, id);
    return ResponseEntity.ok(savedCustomer);
  }

  @DeleteMapping("/customers/{id}")
  @ApiOperation(
      value =
          "Deletes customer with id: id\n"
              + "Returns a boolean indicating whether the customer was successfully deleted and a message"
              + "containing the id of the deleted customer")
  public ResponseEntity<Map<String, Boolean>> deleteCustomer(@PathVariable Long id) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(
                () -> new CustomerNotFoundException("Customer with id: " + id + " not found"));
    customerRepository.delete(customer);
    Map<String, Boolean> response = new HashMap<>();
    response.put("Customer with id: " + id + "successfully deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }
}
