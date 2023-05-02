package com.example.LogSystem.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateGeneralResponse(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        return new ResponseEntity<>(map, status);
    }
    public static ResponseEntity<Object> generateInfoResponse(HttpStatus status,Object object) {
        return new ResponseEntity<>(object,status);
    }
}
