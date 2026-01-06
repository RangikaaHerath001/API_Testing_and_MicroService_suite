package com.apitesting.tests;

import com.apitesting.models.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserAPITest {

    @BeforeAll
    public static void setup() {
        // Set base URI for your API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testGetAllUsers() {
        // Send GET request and verify response
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    public void testGetSingleUser() {
        given()
                .pathParam("id", 1)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("username", notNullValue());
    }

    @Test
    public void testCreateUser() {
        User newUser = new User("john.doe", "john@example.com");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(newUser)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("username", equalTo("john.doe"))
                .body("email", equalTo("john@example.com"))
                .extract().response();

        // You can also extract and verify the ID
        int userId = response.path("id");
        System.out.println("Created user with ID: " + userId);
    }

    @Test
    public void testUpdateUser() {
        User updatedUser = new User("john.updated", "john.updated@example.com");

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .body(updatedUser)
                .when()
                .put("/users/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteUser() {
        given()
                .pathParam("id", 1)
                .when()
                .delete("/users/{id}")
                .then()
                .statusCode(200);
    }
}