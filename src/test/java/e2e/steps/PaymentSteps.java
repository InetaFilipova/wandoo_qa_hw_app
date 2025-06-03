package e2e.steps;

import e2e.utils.ApiTestUtils;
import e2e.utils.StepContext;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.json.JSONObject;
import static org.junit.Assert.*;

public class PaymentSteps {
    private final StepContext context;

    public PaymentSteps(StepContext context) {
        this.context = context;
    }

    @When("funds of {double} are added to the client's wallet")
    public void funds_are_added_to_the_clients_wallet(Double amount) {
        String endpoint = System.getProperty("endpoint.addfunds");
        Response response = ApiTestUtils.postJson(endpoint, ApiTestUtils.buildFundsPayload(context.getClientId(), amount), context.getSessionFilter());
        assertEquals(200, response.getStatusCode());
    }

    @When("a repayment of {double} is made for the client")
    public void a_repayment_is_made_for_the_client(Double amount) {
        String endpoint = System.getProperty("endpoint.addfunds");
        JSONObject repayment = ApiTestUtils.buildFundsPayload(context.getClientId(), amount, "REPAYMENT");
        Response response = ApiTestUtils.postJson(endpoint, repayment, context.getSessionFilter());
        assertEquals(200, response.getStatusCode());
    }

    @Then("the payments list should contain {int} item(s|)")
    @Then("the payments list should contain {int} items")
    public void the_payments_list_should_contain_items(Integer expectedCount) {
        String endpoint = System.getProperty("endpoint.payments");
        Response response = ApiTestUtils.getJson(endpoint, context.getClientId(), context.getSessionFilter());
        assertEquals(200, response.getStatusCode());
        org.json.JSONArray arr = new org.json.JSONArray(response.getBody().asString());
        assertEquals(expectedCount.intValue(), arr.length());
    }
}
