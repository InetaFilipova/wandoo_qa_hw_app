package e2e.utils;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ApiTestUtils {
    public static final String BASE_URL = System.getProperty("base.url", "http://localhost:8080");

    private static JSONObject loadTestData(String fileName) {
        try (InputStream is = ApiTestUtils.class.getClassLoader().getResourceAsStream("testdata/" + fileName)) {
            if (is == null) throw new RuntimeException(fileName + " not found!");
            String json = new Scanner(is, StandardCharsets.UTF_8).useDelimiter("\\A").next();
            return new JSONObject(json);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load " + fileName, e);
        }
    }

    public static JSONObject buildRegistrationPayload() {
        JSONObject registration = loadTestData("registration.json");
        JSONObject payload = new JSONObject();
        payload.put("email", "testuser" + System.currentTimeMillis() + "@example.com");
        payload.put("password", registration.getString("password"));
        payload.put("firstName", registration.getString("firstName"));
        payload.put("lastName", registration.getString("lastName"));
        return payload;
    }

    public static JSONObject buildPersonalDataPayload(String clientId) {
        JSONObject personalData = loadTestData("personal_data.json");
        JSONObject payload = new JSONObject();
        payload.put("clientId", clientId);
        payload.put("address", personalData.getString("address"));
        payload.put("phone", personalData.getString("phone"));
        return payload;
    }

    public static JSONObject buildFundsPayload(String clientId, Double amount) {
        JSONObject funds = new JSONObject();
        JSONObject account = loadTestData("account.json");
        JSONObject transaction = loadTestData("transaction.json");
        funds.put("accountHolderFullName", account.getString("accountHolderFullName"));
        funds.put("accountHolderPersonalId", account.getString("accountHolderPersonalId"));
        funds.put("transactionType", transaction.getString("transactionType"));
        funds.put("investorId", clientId);
        JSONObject amountObj = new JSONObject();
        amountObj.put("currency", account.getString("currency"));
        amountObj.put("amount", amount);
        funds.put("amount", amountObj);
        funds.put("bookingDate", transaction.getString("bookingDate"));
        funds.put("accountNumber", account.getString("accountNumber"));
        return funds;
    }

    public static JSONObject buildFundsPayload(String clientId, Double amount, String transactionType) {
        JSONObject funds = new JSONObject();
        JSONObject account = loadTestData("account.json");
        JSONObject transaction = loadTestData("transaction.json");
        funds.put("accountHolderFullName", account.getString("accountHolderFullName"));
        funds.put("accountHolderPersonalId", account.getString("accountHolderPersonalId"));
        funds.put("transactionType", transactionType);
        funds.put("investorId", clientId);
        JSONObject amountObj = new JSONObject();
        amountObj.put("currency", account.getString("currency"));
        amountObj.put("amount", amount);
        funds.put("amount", amountObj);
        funds.put("bookingDate", transaction.getString("bookingDate"));
        funds.put("accountNumber", account.getString("accountNumber"));
        return funds;
    }

    public static JSONObject buildPaymentPayload(String clientId, Double amount) {
        JSONObject payment = new JSONObject();
        JSONObject account = loadTestData("account.json");
        payment.put("clientId", clientId);
        payment.put("amount", amount);
        payment.put("currency", account.getString("currency"));
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
