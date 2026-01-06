package com.apitesting.utils;

import io.restassured.response.Response;
import java.util.Objects;

public class ResponseValidator {

    public static void validateStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        if (expectedStatusCode != actualStatusCode) {
            throw new AssertionError("Status code mismatch. Expected: " + expectedStatusCode +
                    " but got: " + actualStatusCode);
        }
    }

    public static void validateResponseTime(Response response, long maxTimeInMs) {
        long responseTime = response.getTime();
        if (!(responseTime < maxTimeInMs)) {
            throw new AssertionError("Response time exceeded. Expected: < " + maxTimeInMs +
                    "ms but got: " + responseTime + "ms");
        }
    }

    public static void validateFieldNotNull(Response response, String fieldPath) {
        Object value = response.path(fieldPath);
        if (value == null) {
            throw new AssertionError("Field '" + fieldPath + "' should not be null");
        }
    }

    public static void validateFieldValue(Response response, String fieldPath, Object expectedValue) {
        Object actualValue = response.path(fieldPath);
        if (!Objects.equals(expectedValue, actualValue)) {
            throw new AssertionError("Field '" + fieldPath + "' value mismatch. Expected: " + expectedValue +
                    " but got: " + actualValue);
        }
    }

    public static void validateResponseContainsField(Response response, String fieldPath) {
        boolean fieldExists = response.path(fieldPath) != null;
        if (!fieldExists) {
            throw new AssertionError("Response should contain field: " + fieldPath);
        }
    }
}
