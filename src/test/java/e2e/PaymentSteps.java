package e2e;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.json.JSONObject;
import static org.junit.Assert.*;

public class PaymentSteps {
    private Response lastAddFundsResponse;

    @When("funds of {double} are added to the client's wallet")
    public void funds_are_added_to_the_clients_wallet(Double amount) {
        String endpoint = "/api/add-funds";
        Response response = ApiTestUtils.postJson(endpoint, ApiTestUtils.buildFundsPayload(StepContext.getClientId(), amount), StepContext.getSessionFilter());
        assertEquals(200, response.getStatusCode());
    }

    @When("a repayment of {double} is made for the client")
    public void a_repayment_is_made_for_the_client(Double amount) {
        String endpoint = "/api/add-funds";
        JSONObject repayment = ApiTestUtils.buildFundsPayload(StepContext.getClientId(), amount, "REPAYMENT");
        Response response = ApiTestUtils.postJson(endpoint, repayment, StepContext.getSessionFilter());
        assertEquals(200, response.getStatusCode());
    }

    @Then("the payments list should contain {int} item(s|)")
    @Then("the payments list should contain {int} items")
    public void the_payments_list_should_contain_items(Integer expectedCount) {
        String endpoint = "/api/payments";
        Response response = ApiTestUtils.getJson(endpoint, StepContext.getClientId(), StepContext.getSessionFilter());
        assertEquals(200, response.getStatusCode());
        org.json.JSONArray arr = new org.json.JSONArray(response.getBody().asString());
        assertEquals(expectedCount.intValue(), arr.length());
    }
}
