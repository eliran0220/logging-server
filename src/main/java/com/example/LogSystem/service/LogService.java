package com.example.LogSystem.service;

import com.example.LogSystem.model.Log;
import com.example.LogSystem.utils.Constants;
import com.example.LogSystem.utils.Helper;
import com.example.LogSystem.utils.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public CompletableFuture<ResponseEntity<Object>> getLogsByUserId(String userId) {
         try {
             ArrayList<ArrayList<Log>> logs = logMap.get(userId);
             if (logs == null) {
                 return CompletableFuture.completedFuture(ResponseHandler.generateInfoResponse(HttpStatus.OK, "No data has been found for employee id " + userId));
             }
             Map<String, Object> map = new HashMap<>();
             map.put("employee_id", userId);
             map.put("dates", logs);
             Helper.addNoExitIfNeeded(logs);
             return CompletableFuture.completedFuture(ResponseHandler.generateInfoResponse(HttpStatus.OK, map));
         } catch (Exception e) {
             return CompletableFuture.completedFuture(ResponseHandler.generateGeneralResponse(Constants.GENERAL_ERROR, HttpStatus.BAD_REQUEST));
         }
    }
    @Async("asyncExecutor")
    public CompletableFuture<ResponseEntity<Object>> enterLog(String userId) {
        try {
            if (logMap.containsKey((userId))) {
                if (validateLog(Constants.ENTER, userId) || validateLog(Constants.NO_EXIT_LOG, userId)) {
                    return CompletableFuture.completedFuture(ResponseHandler.generateGeneralResponse(Constants.BAD_REQUEST, HttpStatus.BAD_REQUEST));
                }
                ArrayList<ArrayList<Log>> logs = logMap.get(userId);
                Log log = new Log(Constants.ENTER, Helper.createDate());
                ArrayList<Log> list = new ArrayList<>();
                list.add(log);
                logs.add(list);
            } else {
                Log log = new Log(Constants.ENTER, Helper.createDate());
                ArrayList<Log> list = new ArrayList<>();
                ArrayList<ArrayList<Log>> logsList = new ArrayList<>();
                logsList.add(list);
                list.add(log);
                logMap.put(userId, logsList);
            }
            return CompletableFuture.completedFuture(ResponseHandler.generateGeneralResponse(Constants.OK, HttpStatus.OK));
        }
        catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseHandler.generateGeneralResponse(Constants.GENERAL_ERROR, HttpStatus.BAD_REQUEST));
        }
    }

    @Async("asyncExecutor")
    public CompletableFuture<ResponseEntity<Object>> exitLog(String userId) {
        try {
            if (logMap.containsKey((userId))) {
                if (validateLog(Constants.EXIT, userId)) {
                    return CompletableFuture.completedFuture(ResponseHandler.generateGeneralResponse(Constants.BAD_REQUEST, HttpStatus.BAD_REQUEST));
                }
                ArrayList<ArrayList<Log>> logs = logMap.get(userId);
                ArrayList<Log> lastLogArray = logs.get(logs.size() - 1);
                if (validateLog(Constants.NO_EXIT_LOG, userId)) {
                    lastLogArray.remove(lastLogArray.size() - 1);
                }
                Log log = new Log(Constants.EXIT, Helper.createDate());
                lastLogArray.add((log));
                return CompletableFuture.completedFuture(ResponseHandler.generateGeneralResponse(Constants.OK, HttpStatus.OK));
            } else {
                return CompletableFuture.completedFuture(ResponseHandler.generateGeneralResponse(Constants.BAD_REQUEST, HttpStatus.BAD_REQUEST));
            }
        }
        catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseHandler.generateGeneralResponse(Constants.GENERAL_ERROR, HttpStatus.BAD_REQUEST));
        }
    }

    @Async("asyncExecutor")
    public CompletableFuture<ResponseEntity<Object>> getAllLogs() {
        try {
            ArrayList<Object> list = new ArrayList<>();
            for (Map.Entry<String, ArrayList<ArrayList<Log>>> entry : logMap.entrySet()) {
                Map<String, Object> map = new HashMap<>();
                map.put("employee_id", entry.getKey());
                map.put("dates", entry.getValue());
                Helper.addNoExitIfNeeded(entry.getValue());
                list.add(map);
            }
            return CompletableFuture.completedFuture(ResponseHandler.generateInfoResponse(HttpStatus.OK, list));
        }
        catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseHandler.generateGeneralResponse(Constants.GENERAL_ERROR, HttpStatus.BAD_REQUEST));
        }
    }

    @Async("asyncExecutor")
    public boolean validateLog(String description, String userId) {
        ArrayList<ArrayList<Log>> logs = logMap.get(userId);
        ArrayList<Log> lastLogArray = logs.get(logs.size() - 1);
        Log lastLog = lastLogArray.get(lastLogArray.size() - 1);
        return description.equals(lastLog.getDescription());
    }

}
