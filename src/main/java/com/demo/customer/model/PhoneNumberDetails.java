package com.demo.customer.model;

public class PhoneNumberDetails {

  String countryCode;

  public PhoneNumberDetails(String countryCode, String countryName, String operatorName) {
    this.countryCode = countryCode;
    this.countryName = countryName;
    this.operatorName = operatorName;
  }

  public PhoneNumberDetails() {}

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public String getOperatorName() {
    return operatorName;
  }

  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  String countryName;
  String operatorName;
}
