package com.mindshare.core.common.controller;

import com.mindshare.core.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApiControllerAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Map<String, String>> handleException(Exception e) {
        Map<String, String> errMap = new HashMap<>();
        errMap.put("code", "400");
        errMap.put("detail", e.getMessage());

        return new ResponseEntity<>(errMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ApiException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Map<String, String>> handleApiException(ApiException e) {
        Map<String, String> errMap = new HashMap<>();
        errMap.put("code", "400");
        errMap.put("detail", e.getMessage());

        return new ResponseEntity<>(errMap, HttpStatus.BAD_REQUEST);
    }

}
