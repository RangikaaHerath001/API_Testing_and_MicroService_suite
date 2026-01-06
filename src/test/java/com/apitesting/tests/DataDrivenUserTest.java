package com.apitesting.tests;

import com.apitesting.models.User;
import com.apitesting.utils.APIHelper;
import com.apitesting.utils.DataProvider;
import com.apitesting.utils.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class DataDrivenUserTest {
    private APIHelper apiHelper;

    @BeforeEach
    public void setup() {
        apiHelper = new APIHelper();
    }

    static Stream<Object[]> userData() {
        Object[][] data = DataProvider.getTestData("testdata/users.csv");
        return Stream.of(data);
    }

    @ParameterizedTest
    @MethodSource("userData")
    public void testCreateUserWithMultipleDataSets(String username, String email, String expectedStatus) {
        User newUser = new User(username, email);

        Response response = apiHelper.post("/users", newUser);

        int expected = Integer.parseInt(expectedStatus);
        ResponseValidator.validateStatusCode(response, expected);

        System.out.println("Test completed for user: " + username +
                " with status: " + response.getStatusCode());
    }
}