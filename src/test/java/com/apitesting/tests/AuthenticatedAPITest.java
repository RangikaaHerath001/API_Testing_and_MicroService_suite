package com.apitesting.tests;

import com.apitesting.utils.AuthHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthenticatedAPITest {
    private AuthHelper authHelper;
    private String accessToken;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "https://your-api.com";
        authHelper = new AuthHelper(RestAssured.baseURI);

        // Get access token before running tests
        // In real scenarios, use actual credentials
        accessToken = authHelper.getAccessToken("testuser", "testpass");
    }

    @Test
    public void testProtectedEndpointWithValidToken() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/protected/resource")
                .then()
                .statusCode(200)
                .body("data", notNullValue());
    }

    @Test
    public void testProtectedEndpointWithoutToken() {
        given()
                .when()
                .get("/api/protected/resource")
                .then()
                .statusCode(401);
    }

    @Test
    public void testTokenRefresh() {
        String newToken = authHelper.refreshAccessToken();

        assert newToken != null && !newToken.isEmpty() : "Refresh token should return valid access token";

        // Verify new token works
        given()
                .header("Authorization", "Bearer " + newToken)
                .when()
                .get("/api/protected/resource")
                .then()
                .statusCode(200);
    }
}