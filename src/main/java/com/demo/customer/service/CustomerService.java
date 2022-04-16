package com.demo.customer.service;

import com.demo.customer.model.Customer;
import com.demo.customer.model.PhoneNumberDetails;
import com.demo.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class CustomerService {

  @Autowired private RestTemplate restTemplate;

  @Autowired private CustomerRepository customerRepository;

  public Customer saveCustomer(Customer customer) {
    log.debug("CustomerService -- saveCustomer: id= " + customer.getId());
    PhoneNumberDetails details =
        restTemplate.getForObject(
            "http://localhost:9001/validators/" + customer.getPhoneNumber(),
            PhoneNumberDetails.class);
    return customerRepository.save(customer);
  }
}
