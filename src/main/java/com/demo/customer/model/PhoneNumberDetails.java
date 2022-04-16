package com.demo.customer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberDetails {

  String countryCode;
  String countryName;
  String operatorName;
}
