package com.example.LogSystem.utils;

import com.example.LogSystem.model.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Helper {
    public static void addNoExitIfNeeded(ArrayList<ArrayList<Log>> logsList) {
        ArrayList<Log> logs = logsList.get(logsList.size()-1);
        if (logs.get(logs.size() - 1).getDescription().equals(Constants.ENTER)) {
            logs.add(new Log(Constants.NO_EXIT_LOG,Constants.ONLY_ENTER_RECEIEVED));
        }
    }

    public static String createDate() {
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String formattedDate = formatter.format(date);
        return formattedDate;
    }
}
