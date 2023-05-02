package com.example.LogSystem.service;

import com.example.LogSystem.model.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.example.LogSystem.utils.ResponseHandler;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class LogService {

    private HashMap<String, ArrayList<ArrayList<Log>>> logMap;
    public LogService() {
        this.logMap = new HashMap<>();
    }
    @Async("asyncExecutor")
    public ResponseEntity<Object> getLogsByUserId(String userId) {
        ArrayList<ArrayList<Log>> logs = logMap.get(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("employee_id", userId);
        map.put("dates",logs);
        return ResponseHandler.generateInfoResponse(HttpStatus.OK,map);
    }
    @Async("asyncExecutor")
    public ResponseEntity<Object> enterLog(String userId) {
        if (logMap.containsKey((userId))) {
            if (validateLog("enter", userId)) {
                return ResponseHandler.generateGeneralResponse("BAD REQUEST", HttpStatus.BAD_REQUEST);
            }
            ArrayList<ArrayList<Log>> logs = logMap.get(userId);
            Log log = new Log("enter",new Date().toString());
            ArrayList<Log> list = new ArrayList<>();
            list.add(log);
            logs.add(list);
        } else {
            Log log = new Log("enter",new Date().toString());
            ArrayList<Log> list = new ArrayList<>();
            ArrayList<ArrayList<Log>> logsList = new ArrayList<>();
            logsList.add(list);
            list.add(log);
            logMap.put(userId,logsList);
        }
        return ResponseHandler.generateGeneralResponse("OK", HttpStatus.OK);
    }

    @Async("asyncExecutor")
    public ResponseEntity<Object> exitLog(String userId) {
        if (logMap.containsKey((userId))) {
            if (validateLog("exit",userId)) {
                return ResponseHandler.generateGeneralResponse("BAD REQUEST", HttpStatus.BAD_REQUEST);
            }
            ArrayList<ArrayList<Log>> logs = logMap.get(userId);
            ArrayList<Log> lastLogArray = logs.get(logs.size() - 1);
            Log log = new Log("exit",new Date().toString());
            lastLogArray.add((log));
            return ResponseHandler.generateGeneralResponse("OK", HttpStatus.OK);
        } else {
            return ResponseHandler.generateGeneralResponse("BAD REQUEST", HttpStatus.BAD_REQUEST);
        }
    }

    @Async("asyncExecutor")
    public CompletableFuture<ResponseEntity<Object>> getAllLogs() {
        ArrayList<Object> list = new ArrayList<>();
        for (Map.Entry<String, ArrayList<ArrayList<Log>>> entry : logMap.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("employee_id", entry.getKey());
            map.put("dates", entry.getValue());
            list.add(map);
        }
        return CompletableFuture.completedFuture(ResponseHandler.generateInfoResponse(HttpStatus.OK, list));
    }

    @Async("asyncExecutor")
    public boolean validateLog(String description, String userId) {
        ArrayList<ArrayList<Log>> logs = logMap.get(userId);
        ArrayList<Log> lastLogArray = logs.get(logs.size() - 1);
        Log lastLog = lastLogArray.get(lastLogArray.size() - 1);
        return description.equals(lastLog.getDescription());
    }

}
