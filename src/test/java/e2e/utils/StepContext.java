package e2e.utils;

import io.restassured.filter.session.SessionFilter;

public class StepContext {
    private String clientId;
    private String authToken;
    private SessionFilter sessionFilter;

    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getAuthToken() {
        return authToken;
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    public SessionFilter getSessionFilter() {
        return sessionFilter;
    }
    public void setSessionFilter(SessionFilter sessionFilter) {
        this.sessionFilter = sessionFilter;
    }
}
