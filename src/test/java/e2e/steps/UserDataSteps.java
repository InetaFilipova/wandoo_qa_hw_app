package e2e.steps;

import e2e.utils.ApiTestUtils;
import e2e.utils.StepContext;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import static org.junit.Assert.*;

public class UserDataSteps {
    private final StepContext context;

    public UserDataSteps(StepContext context) {
        this.context = context;
    }

    @When("the client submits personal data")
    public void the_client_submits_personal_data() {
        String endpoint = System.getProperty("endpoint.personaldata");
        Response response = ApiTestUtils.postJson(endpoint, ApiTestUtils.buildPersonalDataPayload(context.getClientId()), context.getSessionFilter());
        int status = response.getStatusCode();
        assertTrue("Expected 200 or 201, but got: " + status, status == 200 || status == 201);
    }

    @Then("the client's balance should be {double}")
    public void the_clients_balance_should_be(Double expectedBalance) {
        String endpoint = System.getProperty("endpoint.balance");
        Response response = ApiTestUtils.getJson(endpoint, context.getClientId(), context.getSessionFilter());
        assertEquals(200, response.getStatusCode());
        double balance = Double.parseDouble(response.getBody().asString());
        assertEquals(expectedBalance, balance, 0.001);
    }
}
