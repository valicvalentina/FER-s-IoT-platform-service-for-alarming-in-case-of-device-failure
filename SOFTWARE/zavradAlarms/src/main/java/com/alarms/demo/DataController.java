package com.alarms.demo;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.domain.File;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.alarms.demo.Data.DataRequest;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ConfigurationPropertiesScan
@EnableConfigurationProperties
@ConfigurationProperties("telegram.bot")
@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class DataController {
	 String url = "http://localhost:8086";
	 private NotificationScheduler notificationScheduler;
	 private MyBot mybot;
   String token = "W2xpJzb0r2KuTNenYZJWjAMAg8Zh2GP7eqF4P31pcbXFUrQyvb6rj8fmqQGUWp5w8_Tpm11XrqOWYAPGxRhYJA==";
   private TeamsConfiguration teamsconfiguration;
   private TeamsController teamscontroller;
   
   public DataController(NotificationScheduler notificationScheduler,MyBot mybot,TeamsConfiguration teamsconfiguration, TeamsController teamscontroller) {
       this.notificationScheduler = notificationScheduler;
       this.mybot=mybot;
       this.teamsconfiguration = teamsconfiguration;
       this.teamscontroller = teamscontroller;
   }
   @Autowired
   public void setNotificationScheduler(NotificationScheduler notificationScheduler) {
       this.notificationScheduler = notificationScheduler;
   }
   @Autowired
   public void setMyBot(MyBot mybot) {
	   this.mybot=mybot;
   }
   @Autowired
   public void setTeamsConfiguration(TeamsConfiguration teamsconfiguration) {
	   this.teamsconfiguration = teamsconfiguration;
   }
   @Autowired
   public void setTeamsController(TeamsController teamscontrolle) {
	   this.teamscontroller = teamscontroller;
   }
   
   
   
   
    @PostMapping(value="/data")
    public ResponseEntity<String> postData() {
        RestTemplate restTemplate = new RestTemplate();
 // String url = "https://iotat.tel.fer.hr:57786/api/v2/query?org=fer&Authorization=W2xpJzb0r2KuTNenYZJWjAMAg8Zh2GP7eqF4P31pcbXFUrQyvb6rj8fmqQGUWp5w8_Tpm11XrqOWYAPGxRhYJA==";
       String url = "https://iotat.tel.fer.hr:57786/api/v2/query?org=fer";
        HttpHeaders headers = new HttpHeaders();
       headers.set("Authorization", "Token " + "W2xpJzb0r2KuTNenYZJWjAMAg8Zh2GP7eqF4P31pcbXFUrQyvb6rj8fmqQGUWp5w8_Tpm11XrqOWYAPGxRhYJA==");

        headers.set("Accept", "application/csv");
        headers.set("Content-type", "application/vnd.flux");

        String query = "from(bucket:\"telegraf\")\n" +
                       "  |> range(start: -1h)\n" +
                      // "  |> filter(fn: (r) => r[\"_measurement\"] == \"TC\")\n" +
                       "  |> filter(fn: (r) => r[\"id_wasp\"] == \"SAP02\")";

         HttpEntity<String> entity = new HttpEntity<>(query, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

       return response;
        
    }
    
    @Scheduled(fixedRate = 10000)
    @GetMapping(value="/sendmessage")
     public void sendMessage() {
    	
            List<String> alarmEntries = new AlarmFileReader().readDataFromFile(FILE_PATH);
            List<AlarmData> alarms = new AlarmFileReader().parseAlarms(alarmEntries);
            
            for (AlarmData alarm : alarms) {
               RestTemplate restTemplate = new RestTemplate();
               String url = "https://iotat.tel.fer.hr:57786/api/v2/query?org=fer";
               HttpHeaders headers = new HttpHeaders();
               headers.set("Authorization", "Token " + "W2xpJzb0r2KuTNenYZJWjAMAg8Zh2GP7eqF4P31p"
               	+ "cbXFUrQyvb6rj8fmqQGUWp5w8_Tpm11XrqOWYAPGxRhYJA==");
               headers.set("Accept", "application/csv");
               headers.set("Content-type", "application/vnd.flux");

               HttpEntity<String> entity = new HttpEntity<>(alarm.getPayload(), headers);
             
     ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                
                if (response.getBody().isBlank()) {
                   if(alarm.getAlarmChannel().equals("telegram")){
                       int semicolonIndex = alarm.getAlarmUrl().indexOf(';');
                       mybot.setBotToken(alarm.getAlarmUrl().substring(0,semicolonIndex));
         
                    String message = alarm.getMessage();
      notificationScheduler.sendNotification(alarm.getAlarmUrl().substring(semicolonIndex+1 ),message);

                   }  else if(alarm.getAlarmChannel().equals("teams")){
            	       teamsconfiguration.setWebhookUrl(alarm.getAlarmUrl());
            	       teamscontroller.sendNotificationScheduled(alarm.getMessage());
                            }
               		
    	            }
            }
            alarms.clear();
         }
    
    @PostMapping("/alarms")
    public ResponseEntity<List<AlarmData>> getAlarms(Authentication authentication){
        List<String> alarmEntries = new AlarmFileReader().readDataFromFile(FILE_PATH);
        String name = authentication.getName();
        List<AlarmData> alarms = new ArrayList<>();
        for (String entry : alarmEntries) {
            String[] lines = entry.split("\\r?\\n");
            String alarmId = lines[0];
            String deviceId = lines[1];
            String payload = lines[3] + "\n" +lines[4]+ "\n" + lines[5] + "\n" + lines[6];
            String user = lines[8];
            String message = lines[9];
            String alarmUrl = lines[10];
            String alarmChannel = lines[11];
            AlarmData alarm = new AlarmData(alarmId, deviceId, payload, user, message,alarmUrl,alarmChannel);
            if(alarm.getUser().equals(name)) {
                alarms.add(alarm);
            }
        }
        return ResponseEntity.ok(alarms);
    }
    


    
    private static final String FILE_PATH = "src/main/java/com/alarms/demo/alarms.txt";
    @JsonCreator
    @PostMapping(value="/create")
    public ResponseEntity<String> addAlarm(@RequestBody String jsonData) {

        ObjectMapper mapper = new ObjectMapper();
        Data data;
        try {
            data = mapper.readValue(jsonData, Data.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(String.format("###\n%s\n%s\n---\n%s\n---\n%s\n%s\n%s\n%s\n", 
             data.getAlarmId(), data.getDeviceId(),
             data.getDataRequest().getPayload(), data.getAlarmTarget(),
             data.getAlarmMessage(),data.getAlarmUrl(),data.getAlarmChannel()));
             return ResponseEntity.ok("Data added successfully");
               } catch (IOException e) {
                 e.printStackTrace();
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add data");
        }
    }
    
    

    @PutMapping("/update/{alarmId}")
    public ResponseEntity<String> updateAlarm(@PathVariable String alarmId, @RequestBody Data newData) {
        try {
            List<String> lines = Files.readAllLines(Path.of(FILE_PATH));
            boolean found = false;

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.equals("###")) {
                    String currentAlarmId = lines.get(i + 1);
                    if (currentAlarmId.equals(alarmId)) {
                        found = true;

                        lines.set(i + 2, newData.getDeviceId());
                        lines.set(i + 3, "---");
                        lines.set(i + 4, newData.getDataRequest().getPayload());
                        lines.set(i + 5, "---");
                        lines.set(i + 6, newData.getAlarmTarget());
                        lines.set(i + 7, newData.getAlarmMessage());
                        lines.set(i + 8, newData.getAlarmUrl());
                        lines.set(i + 9, newData.getAlarmChannel());
                        lines.subList(i + 10, i + 11).clear();
                        lines.subList(i + 11, i + 12).clear();
                        lines.subList(i + 10, i + 11).clear();
                        break;
                    }
                }
            }

            if (found) {
                Files.write(Path.of(FILE_PATH), lines);
                return ResponseEntity.ok("Alarm updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update alarm");
        }
    }


    @DeleteMapping("/delete/{alarmId}")
    public ResponseEntity<String> deleteAlarm(@PathVariable String alarmId) {
        try {
            List<String> lines = Files.readAllLines(Path.of(FILE_PATH));
            boolean found = false;

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.equals("###")) {
                    String currentAlarmId = lines.get(i + 1);
                    if (currentAlarmId.equals(alarmId)) {
                        found = true;
                        lines.subList(i, i + 13).clear(); 

                        break;
                    }
                }
            }

            if (found) {
                Files.write(Path.of(FILE_PATH), lines);
                return ResponseEntity.ok("Alarm deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete alarm");
        }
    }
    

}







