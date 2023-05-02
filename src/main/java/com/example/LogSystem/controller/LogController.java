package com.example.LogSystem.controller;

import com.example.LogSystem.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class LogController {
    @Autowired
    private LogService logService;

    @Autowired
    public LogController(LogService logService){
        this.logService = logService;
    }
    @GetMapping("/info/")
    public ResponseEntity<Object> getLogs(@RequestParam(name = "id") String id) {
        return logService.getLogsByUserId(id);
    }

    @GetMapping("/info")
    public CompletableFuture getLogs() {
        return logService.getAllLogs();
    }

    @PostMapping("/enter/")
    public ResponseEntity<Object> enterLog(@RequestParam(name = "id") String id) {
        return logService.enterLog(id);
    }

    @PostMapping("/exit/")
    public ResponseEntity<Object> exitLog(@RequestParam(name = "id") String id) {
        return logService.exitLog(id);
    }
}
