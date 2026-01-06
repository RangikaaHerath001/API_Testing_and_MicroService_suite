package com.apitesting.tests;

import com.apitesting.models.User;
import com.apitesting.utils.APIHelper;
import com.apitesting.utils.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

public class UserAPITest {
    private APIHelper apiHelper;

    @BeforeEach
    public void setup() {
        apiHelper = new APIHelper();
    }

    @Test
    @DisplayName("Verify GET request returns all users")
    public void testGetAllUsers() {
        Response response = apiHelper.get("/users");

        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseTime(response, 2000);

        int userCount = response.jsonPath().getList("$").size();
        assert userCount > 0 : "User list should not be empty";
    }

    @Test
    @DisplayName("Verify GET request returns single user by ID")
    public void testGetSingleUser() {
        Response response = apiHelper.get("/users/{id}", 1);

        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateFieldValue(response, "id", 1);
        ResponseValidator.validateFieldNotNull(response, "username");
        ResponseValidator.validateFieldNotNull(response, "email");
    }

    @Test
    @DisplayName("Verify POST request creates new user")
    public void testCreateUser() {
        User newUser = new User("john.doe", "john@example.com");

        Response response = apiHelper.post("/users", newUser);

        ResponseValidator.validateStatusCode(response, 201);
        ResponseValidator.validateFieldValue(response, "username", "john.doe");
        ResponseValidator.validateFieldValue(response, "email", "john@example.com");
        ResponseValidator.validateFieldNotNull(response, "id");
    }

    @Test
    @DisplayName("Verify PUT request updates existing user")
    public void testUpdateUser() {
        User updatedUser = new User("john.updated", "john.updated@example.com");

        Response response = apiHelper.put("/users/{id}", 1, updatedUser);

        ResponseValidator.validateStatusCode(response, 200);
    }

    @Test
    @DisplayName("Verify DELETE request removes user")
    public void testDeleteUser() {
        Response response = apiHelper.delete("/users/{id}", 1);

        ResponseValidator.validateStatusCode(response, 200);
    }
}