package com.demo.customer.controller;

import com.demo.customer.exception.CustomerNotFoundException;
import com.demo.customer.model.Customer;
import com.demo.customer.repository.CustomerRepository;
import com.demo.customer.service.CustomerService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1/")
public class CustomerController {

  @Autowired private CustomerRepository customerRepository;

  @Autowired private CustomerService customerService;

  @GetMapping("/customers")
  public List<Customer> getAllCustomers() {
    return customerRepository.findAll();
  }

  @PostMapping("/customers")
  public Customer addCustomer(@RequestBody Customer customer) {
    return customerService.saveCustomer(customer);
  }

  @GetMapping("/customers/{id}")
  public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(
                () -> new CustomerNotFoundException("Customer with id: " + id + " not found"));
    return ResponseEntity.ok(customer);
  }

  @PutMapping("/customers/{id}")
  public ResponseEntity<Customer> updateCustomer(
      @PathVariable Long id, @RequestBody Customer updatedCustomer) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(
                () -> new CustomerNotFoundException("Customer with id: " + id + " not found"));
    customer.setFirstName(updatedCustomer.getFirstName());
    customer.setLastName(updatedCustomer.getLastName());
    customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
    Customer savedCustomer = customerRepository.save(customer);
    return ResponseEntity.ok(savedCustomer);
  }

  @DeleteMapping("/customers/{id}")
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
