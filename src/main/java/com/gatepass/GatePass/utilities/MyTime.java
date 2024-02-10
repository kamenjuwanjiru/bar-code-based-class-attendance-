package com.gatepass.GatePass.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MyTime {
    public String getTimeStamp(){
        return ""+ new Date(System.currentTimeMillis());
    }

    public String dateToday(){
        //dd-MM-yyyy
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return formatter.format(localDateTime);
    }
}
