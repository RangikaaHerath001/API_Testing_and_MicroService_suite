package com.apitesting.utils;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class AuthHelper {
    private String baseUrl;
    private String accessToken;
    private String refreshToken;

    public AuthHelper(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAccessToken(String username, String password) {
        Response response = given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .body("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }")
                .when()
                .post("/auth/login");

        if (response.getStatusCode() == 200) {
            this.accessToken = response.path("access_token");
            this.refreshToken = response.path("refresh_token");
            return this.accessToken;
        }
        throw new RuntimeException("Authentication failed with status: " + response.getStatusCode());
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public String refreshAccessToken() {
        Response response = given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .header("Authorization", "Bearer " + this.refreshToken)
                .when()
                .post("/auth/refresh");

        if (response.getStatusCode() == 200) {
            this.accessToken = response.path("access_token");
            return this.accessToken;
        }
        throw new RuntimeException("Token refresh failed with status: " + response.getStatusCode());
    }

    public boolean validateToken(String token) {
        Response response = given()
                .baseUri(baseUrl)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/auth/validate");

        return response.getStatusCode() == 200;
    }
}