package com.alarms.demo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlarmFileReader {
	
	
	
	public List<AlarmData> parseAlarms(List<String> alarmEntries){
        List<AlarmData> alarms= new ArrayList<AlarmData>();
        for (String entry : alarmEntries) {
            String[] lines = entry.split("\\r?\\n");
            System.out.println(lines[0]);
            String alarmId = lines[0];
            String deviceId = lines[1];
            String payload = lines[3] + "\n" + lines[4] + "\n" + lines[5] + "\n" + lines[6];
            String user = lines[8];
            String message = lines[9];
            String alarmUrl=lines[10];
            String alarmChannel=lines[11];
            AlarmData alarm = new AlarmData(alarmId, deviceId, payload, user, message,alarmUrl,alarmChannel);
           
            alarms.add(alarm);
            
        }
        return alarms;
    }
    public List<String> readDataFromFile(String filePath) {
        List<String> dataList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder entryBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.equals("###")) {
                    if (entryBuilder.length() > 0) {
                        dataList.add(entryBuilder.toString());
                        entryBuilder.setLength(0);
                    }
                } else {
                    entryBuilder.append(line).append("\n");
                }
            }

            // Add the last entry if it exists
            if (entryBuilder.length() > 0) {
                dataList.add(entryBuilder.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }
}
