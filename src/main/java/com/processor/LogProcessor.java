package com.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpers.HelperModules;
import com.model.DatabaseEntry;
import com.model.Row;
import com.model.State;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LogProcessor {
    public static void main(String args[]){
        System.out.println("Started log processing");
        // HelperModules.initDataBase();
        LogProcessor processor = new LogProcessor();
        processor.process("C:\\Users\\Ashish\\Desktop\\test.txt");
    }

    private void process(String filePath){
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br=new BufferedReader(fr);
            String line;
            HashMap<String, Row> entries = new HashMap<>();
            ObjectMapper objectMapper = HelperModules.getObjectMapper();
            int count = 0;
            while((line = br.readLine()) != null){
                Row row = objectMapper.readValue(line, Row.class);
                if(!entries.containsKey(row.getId())){
                    if(row.getState() == State.STARTED){
                        row.setStartTime(row.getTimestamp());
                    } else if(row.getState() == State.FINISHED){
                        row.setEndTime(row.getTimestamp());
                    }
                    entries.put(row.getId(), row);
                } else {
                    Row existingData = entries.get(row.getId());
                    if(row.getState() == State.STARTED){
                        row.setStartTime(row.getTimestamp());
                        row.setEndTime(existingData.getEndTime());
                    } else if(row.getState() == State.FINISHED){
                        row.setEndTime(row.getTimestamp());
                        row.setStartTime(existingData.getStartTime());
                    }
                    row.setTimestamp(null); // not a needed field anymore
                    entries.remove(row.getId()); // removing from map as not needed to store in memory
                    store(row);
                }
                count++;
            }
            System.out.println("Processed " + count + " rows and final data size is : " + entries.size());
        } catch (FileNotFoundException e) {
            System.out.println("Log processing finished with some exception. " + e.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void store(Row row){
        Long duration = row.getEndTime() - row.getStartTime();
        Boolean alert = duration > 4 ? true : false;
        DatabaseEntry dbEntry = new DatabaseEntry(row.getId(), duration, row.getType(), row.getHost(), alert);
        System.out.println(dbEntry.toString());
    }
}
