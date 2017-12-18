package com.baxter.myrecipes;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataRetrievalFailureException.class)
    public String handleDataRetrievalFailureException(DataRetrievalFailureException e) {
        if (StringUtils.isEmpty(e.getMessage())) {
            return "Could not retrieve data.";
        }
        return e.getMessage();
    }

}
