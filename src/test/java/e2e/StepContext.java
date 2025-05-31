package e2e;

import io.restassured.filter.session.SessionFilter;

public class StepContext {
    private static String clientId;
    private static String authToken;
    private static SessionFilter sessionFilter;

    public static String getClientId() {
        return clientId;
    }
    public static void setClientId(String clientId) {
        StepContext.clientId = clientId;
    }
    public static String getAuthToken() {
        return authToken;
    }
    public static void setAuthToken(String authToken) {
        StepContext.authToken = authToken;
    }
    public static SessionFilter getSessionFilter() {
        return sessionFilter;
    }
    public static void setSessionFilter(SessionFilter sessionFilter) {
        StepContext.sessionFilter = sessionFilter;
    }
}
