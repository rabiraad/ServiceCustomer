package cucumber.stepdefs;

import com.demo.customer.ServiceCustomerApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = ServiceCustomerApplication.class)
public class SpringIntegrationTest {}
