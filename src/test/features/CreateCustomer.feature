@integration-level

Feature: Create new customer

  @happy-path
  Scenario: Creating new customer with valid parameter
    When we create a customer with
      | firstName   | Rebecca3     |
      | lastName    | Abi Raad     |
      | phoneNumber | +96170173804 |
    Then the status code is 201
    And we save "id" value as "customerId"
    When we get customer with id= "customerId"
    Then the status code is 200
    And the response contains
      | id          | customerId   |
      | firstName   | Rebecca3     |
      | lastName    | Abi Raad     |
      | phoneNumber | +96170173804 |
    When we delete customer with id= "customerId"
    Then the status code is 200
    When we get customer with id= "customerId"
    Then the status code is 404

