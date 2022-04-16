package com.demo.customer.service;

import com.demo.customer.model.Customer;
import com.demo.customer.model.PhoneNumberDetails;
import com.demo.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

  @Autowired private RestTemplate restTemplate;

  @Autowired private CustomerRepository customerRepository;

  public Customer saveCustomer(Customer customer) {
    System.out.println("CustomerService -- saveCustomer");
    PhoneNumberDetails details =
        restTemplate.getForObject(
            "http://localhost:9001/validators/" + customer.getPhoneNumber(),
            PhoneNumberDetails.class);
    System.out.println(details.toString());
    return customerRepository.save(customer);
  }
}
