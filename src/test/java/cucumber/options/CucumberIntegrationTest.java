package cucumber.options;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    glue = {"cucumber.stepdefs"},
    features = {"src/test/features"},
    plugin = {
      "pretty",
      "json:target/cucumber-reports/cucumber.json",
      "html:target/cucumber-reports/cucumber.html"
    })
public class CucumberIntegrationTest {}
