package com.demo.customer.service;

import com.demo.customer.exception.CustomerNotFoundException;
import com.demo.customer.exception.InvalidCustomerDetailsException;
import com.demo.customer.model.Customer;
import com.demo.customer.model.PhoneNumberDetails;
import com.demo.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class CustomerService {

  @Autowired private RestTemplate restTemplate;

  @Autowired private CustomerRepository customerRepository;

  public Customer saveCustomer(Customer customer) {
    log.info("CustomerService -- saveCustomer: id= " + customer.getId());
    try {
      PhoneNumberDetails details =
          restTemplate.getForObject(
              "http://PHONE-VALIDATOR/api/v1/validators/" + customer.getPhoneNumber(),
              PhoneNumberDetails.class);
      return customerRepository.save(customer);
    } catch (HttpClientErrorException e) {
      throw new InvalidCustomerDetailsException(e.getMessage());
    }
  }

  public Customer updateCustomer(Customer updatedCustomer, Long id) {
    log.info("CustomerService -- updateCustomer: id= " + updatedCustomer.getId());
    try {
      PhoneNumberDetails details =
          restTemplate.getForObject(
              "http://PHONE-VALIDATOR/api/v1/validators/" + updatedCustomer.getPhoneNumber(),
              PhoneNumberDetails.class);
      Customer customer =
          customerRepository
              .findById(id)
              .orElseThrow(
                  () -> new CustomerNotFoundException("Customer with id: " + id + " not found"));
      customer.setFirstName(updatedCustomer.getFirstName());
      customer.setLastName(updatedCustomer.getLastName());
      customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
      return customerRepository.save(customer);
    } catch (HttpClientErrorException e) {
      throw new InvalidCustomerDetailsException(e.getMessage());
    }
  }
}
