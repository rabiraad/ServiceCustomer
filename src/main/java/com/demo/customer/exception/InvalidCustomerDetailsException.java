package com.demo.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidCustomerDetailsException extends RuntimeException {
  public InvalidCustomerDetailsException(String message) {
    super(message);
  }
}
