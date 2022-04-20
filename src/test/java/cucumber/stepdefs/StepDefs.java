package cucumber.stepdefs;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class StepDefs extends SpringIntegrationTest {

  public static final String SERVICE_URL = "http://localhost:8181/api/v1/customers/";
  public static final String FIRST_NAME_KEY = "firstName";
  public static final String LAST_NAME_KEY = "lastName";
  public static final String PHONE_NUMBER_KEY = "phoneNumber";
  private final String ID_KEY = "id";
  protected Response response;
  RequestSpecification request;

  @When("we create a customer with")
  public void we_post_customers_api(Map<String, String> dataTable) {
    Map<String, String> headerFields = new HashMap<>();
    headerFields.put("content-type", "application/json");
    request = given().headers(headerFields);
    JSONObject requestBodyJson = new JSONObject(dataTable);
    response =
        request
            .body(requestBodyJson.toString())
            .given()
            .redirects()
            .follow(false)
            .when()
            .post(SERVICE_URL);
    response.prettyPrint();
  }

  @Then("the status code is {int}")
  public void the_status_code_is(int status) {
    response.then().statusCode(status);
  }

  @And("^we save \"(.*)\" value as \"(.*)\"$")
  public void we_save_value(String jsonPath, String keyName) {
    System.setProperty(keyName, getResponseField(jsonPath));
    System.out.println("Updating " + keyName + " with value " + System.getProperty(keyName));
  }

  private String getResponseField(String jsonPath) {
    return response.then().extract().jsonPath().get(jsonPath).toString();
  }

  @When("^we get customer with id= \"(.*)\"$")
  public void we_get_customer(String idKey) {
    String id = System.getProperty(idKey);
    request = given();
    response = request.given().redirects().follow(false).when().get(SERVICE_URL + id);
    response.prettyPrint();
  }

  @And("the response contains")
  public void the_response_contains(Map<String, String> dataTable) {
    assertEquals(System.getProperty(dataTable.get(ID_KEY)), getResponseField(ID_KEY));
    assertEquals(dataTable.get(FIRST_NAME_KEY), getResponseField(FIRST_NAME_KEY));
    assertEquals(dataTable.get(LAST_NAME_KEY), getResponseField(LAST_NAME_KEY));
    assertEquals(dataTable.get(PHONE_NUMBER_KEY), getResponseField(PHONE_NUMBER_KEY));
  }

  @When("^we delete customer with id= \"(.*)\"$")
  public void delete_customer(String idKey) {
    String id = System.getProperty(idKey);
    request = given();
    response = request.given().redirects().follow(false).when().delete(SERVICE_URL + id);
    response.prettyPrint();
  }
}
