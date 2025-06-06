package e2e.steps;

import e2e.utils.ApiTestUtils;
import e2e.utils.StepContext;
import io.cucumber.java.en.*;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import org.json.JSONObject;
import static org.junit.Assert.*;

public class AuthenticationSteps {
    private String clientId;
    private String authToken;
    private JSONObject registrationPayload;
    private final SessionFilter sessionFilter = new SessionFilter();
    private final StepContext context;

    public AuthenticationSteps(StepContext context) {
        this.context = context;
    }

    @Given("a client registration payload exists")
    public void a_client_registration_payload_exists() {
        registrationPayload = ApiTestUtils.buildRegistrationPayload();
    }

    @When("the client is registered")
    public void the_client_is_registered() {
        String endpoint = System.getProperty("endpoint.signup");
        Response response = ApiTestUtils.postJson(endpoint, registrationPayload, sessionFilter);
        int status = response.getStatusCode();
        Object idObj = response.jsonPath().get("user.id");
        if (idObj != null) {
            clientId = String.valueOf(idObj);
        } else if (response.jsonPath().get("id") != null) {
            clientId = response.jsonPath().getString("id");
        }
        if (response.jsonPath().get("token") != null) {
            authToken = response.jsonPath().getString("token");
        }
        assertTrue("Expected 200 or 201, but got: " + status, status == 200 || status == 201);
        context.setClientId(clientId);
        context.setAuthToken(authToken);
        context.setSessionFilter(sessionFilter);
    }
}
