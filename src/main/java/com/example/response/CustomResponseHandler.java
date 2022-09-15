package com.example.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CustomResponseHandler {

    public static ResponseEntity<?> generateResponse(String message, HttpStatus status, Object body) {
        Map<String, Object> map = new HashMap<>();

        map.put("message", message);
        map.put("status", status.value());
        map.put("data", body);

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<?> generateResponse(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();

        map.put("message", message);
        map.put("status", status.value());

        return new ResponseEntity<>(map, status);
    }


}
