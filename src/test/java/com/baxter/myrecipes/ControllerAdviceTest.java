package com.baxter.myrecipes;

import org.junit.Test;
import org.springframework.dao.DataRetrievalFailureException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ControllerAdviceTest {

    private ControllerAdvice controllerAdvice = new ControllerAdvice();

    @Test
    public void testThrownWithMessageReturnsMessage() {
        final String message = "message for the exception";

        String response = controllerAdvice.handleDataRetrievalFailureException(
                new DataRetrievalFailureException(message));

        assertThat(response, equalTo(message));
    }

    @Test
    public void testThrownWithoutMessageReturnsDefaultMessage() {
        String response = controllerAdvice.handleDataRetrievalFailureException(new DataRetrievalFailureException(""));

        assertThat(response, equalTo("Could not retrieve data."));
    }
}
