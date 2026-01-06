package com.apitesting.utils;

import com.apitesting.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class APIHelper {
    private ConfigManager config;

    public APIHelper() {
        this.config = ConfigManager.getInstance();
        RestAssured.baseURI = config.getBaseUrl();
    }

    public RequestSpecification getRequestSpec() {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    public RequestSpecification getRequestSpecWithAuth() {
        return getRequestSpec()
                .header("Authorization", "Bearer " + config.getApiKey());
    }

    public Response get(String endpoint) {
        return getRequestSpec()
                .when()
                .get(endpoint);
    }

    public Response get(String endpoint, Object pathParam) {
        return getRequestSpec()
                .pathParam("id", pathParam)
                .when()
                .get(endpoint);
    }

    public Response post(String endpoint, Object body) {
        return getRequestSpec()
                .body(body)
                .when()
                .post(endpoint);
    }

    public Response put(String endpoint, Object pathParam, Object body) {
        return getRequestSpec()
                .pathParam("id", pathParam)
                .body(body)
                .when()
                .put(endpoint);
    }

    public Response delete(String endpoint, Object pathParam) {
        return getRequestSpec()
                .pathParam("id", pathParam)
                .when()
                .delete(endpoint);
    }

    public Response patch(String endpoint, Object pathParam, Object body) {
        return getRequestSpec()
                .pathParam("id", pathParam)
                .body(body)
                .when()
                .patch(endpoint);
    }
}