package com.apitesting.tests;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class MockAPITest {
    private WireMockServer wireMockServer;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void testMockedGetUser() {
        // Setup mock response
        stubFor(get(urlEqualTo("/users/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"id\": 1, \"username\": \"john.doe\", \"email\": \"john@example.com\" }")));

        // Make request to mock server
        given()
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .body("username", equalTo("john.doe"));

        // Verify the request was made
        verify(getRequestedFor(urlEqualTo("/users/1")));
    }

    @Test
    public void testMockedPostUser() {
        stubFor(post(urlEqualTo("/users"))
                .withRequestBody(containing("john.doe"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"id\": 100, \"username\": \"john.doe\", \"email\": \"john@example.com\" }")));

        given()
                .contentType("application/json")
                .body("{ \"username\": \"john.doe\", \"email\": \"john@example.com\" }")
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("id", equalTo(100));
    }

    @Test
    public void testMockedErrorResponse() {
        stubFor(get(urlEqualTo("/users/999"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"error\": \"User not found\" }")));

        given()
                .when()
                .get("/users/999")
                .then()
                .statusCode(404)
                .body("error", equalTo("User not found"));
    }
}