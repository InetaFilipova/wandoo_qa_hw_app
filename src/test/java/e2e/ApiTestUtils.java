package e2e;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ApiTestUtils {
    public static final String BASE_URL = "http://localhost:8080";
    private static final JSONObject testData = loadTestData();

    private static JSONObject loadTestData() {
        try (InputStream is = ApiTestUtils.class.getClassLoader().getResourceAsStream("testdata.json")) {
            if (is == null) throw new RuntimeException("testdata.json not found!");
            String json = new Scanner(is, StandardCharsets.UTF_8).useDelimiter("\\A").next();
            return new JSONObject(json);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load testdata.json", e);
        }
    }

    public static JSONObject buildRegistrationPayload() {
        JSONObject payload = new JSONObject();
        payload.put("email", "testuser" + System.currentTimeMillis() + "@example.com");
        payload.put("password", testData.getJSONObject("registration").getString("password"));
        payload.put("firstName", testData.getJSONObject("registration").getString("firstName"));
        payload.put("lastName", testData.getJSONObject("registration").getString("lastName"));
        return payload;
    }

    public static JSONObject buildPersonalDataPayload(String clientId) {
        JSONObject personalData = new JSONObject();
        personalData.put("clientId", clientId);
        personalData.put("address", testData.getJSONObject("personalData").getString("address"));
        personalData.put("phone", testData.getJSONObject("personalData").getString("phone"));
        return personalData;
    }

    public static JSONObject buildFundsPayload(String clientId, Double amount) {
        JSONObject funds = new JSONObject();
        funds.put("accountHolderFullName", testData.getString("accountHolderFullName"));
        funds.put("accountHolderPersonalId", testData.getString("accountHolderPersonalId"));
        funds.put("transactionType", testData.getString("transactionType"));
        funds.put("investorId", clientId);
        JSONObject amountObj = new JSONObject();
        amountObj.put("currency", testData.getString("currency"));
        amountObj.put("amount", amount);
        funds.put("amount", amountObj);
        funds.put("bookingDate", testData.getString("bookingDate"));
        funds.put("accountNumber", testData.getString("accountNumber"));
        return funds;
    }

    public static JSONObject buildFundsPayload(String clientId, Double amount, String transactionType) {
        JSONObject funds = new JSONObject();
        funds.put("accountHolderFullName", testData.getString("accountHolderFullName"));
        funds.put("accountHolderPersonalId", testData.getString("accountHolderPersonalId"));
        funds.put("transactionType", transactionType);
        funds.put("investorId", clientId);
        JSONObject amountObj = new JSONObject();
        amountObj.put("currency", testData.getString("currency"));
        amountObj.put("amount", amount);
        funds.put("amount", amountObj);
        funds.put("bookingDate", testData.getString("bookingDate"));
        funds.put("accountNumber", testData.getString("accountNumber"));
        return funds;
    }

    public static JSONObject buildPaymentPayload(String clientId, Double amount) {
        JSONObject payment = new JSONObject();
        payment.put("clientId", clientId);
        payment.put("amount", amount);
        payment.put("currency", testData.getString("currency"));
        payment.put("description", "Test payment");
        return payment;
    }

    public static Response postJson(String endpoint, JSONObject body, SessionFilter sessionFilter) {
        return RestAssured.given()
                .filter(sessionFilter)
                .contentType("application/json")
                .body(body.toString())
                .post(BASE_URL + endpoint);
    }

    public static Response getJson(String endpoint, String clientId, SessionFilter sessionFilter) {
        return RestAssured.given()
                .filter(sessionFilter)
                .contentType("application/json")
                .queryParam("clientId", clientId)
                .get(BASE_URL + endpoint);
    }
}
