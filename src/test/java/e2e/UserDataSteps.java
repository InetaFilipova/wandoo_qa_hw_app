package e2e;

import io.cucumber.java.en.*;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import static org.junit.Assert.*;

public class UserDataSteps {
    @When("the client submits personal data")
    public void the_client_submits_personal_data() {
        String endpoint = "/api/personal-data";
        Response response = ApiTestUtils.postJson(endpoint, ApiTestUtils.buildPersonalDataPayload(StepContext.getClientId()), StepContext.getSessionFilter());
        int status = response.getStatusCode();
        assertTrue("Expected 200 or 201, but got: " + status, status == 200 || status == 201);
    }

    @Then("the client's balance should be {double}")
    public void the_clients_balance_should_be(Double expectedBalance) {
        String endpoint = "/api/balance";
        Response response = ApiTestUtils.getJson(endpoint, StepContext.getClientId(), StepContext.getSessionFilter());
        assertEquals(200, response.getStatusCode());
        double balance = Double.parseDouble(response.getBody().asString());
        assertEquals(expectedBalance, balance, 0.001);
    }
}
