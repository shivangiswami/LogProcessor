package com.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpers.HelperModules;
import com.model.DatabaseEntry;
import com.model.Row;
import com.model.State;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.io.*;
import java.util.HashMap;

public class LogProcessor {
    private static final org.apache.log4j.Logger log = Logger.getLogger(LogProcessor.class);
    @Test
    public void analyzeLogs(){
        BasicConfigurator.configure();
        log.info("Started log processing");
        //HelperModules.initDataBase();
        LogProcessor processor = new LogProcessor();
        processor.process("src/main/resources/test.txt");
    }

    private void process(String filePath){
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br=new BufferedReader(fr);
            String line;
            HashMap<String, Row> entries = new HashMap<>();
            ObjectMapper objectMapper = HelperModules.getObjectMapper();
            int count = 0;
            FileWriter writer = new FileWriter("src/main/resources/output.txt");
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
                    store(row, writer);
                }
                count++;
            }
            log.info("Processed " + count + " rows and final data size is : " + entries.size());
            writer.close();
        } catch (FileNotFoundException e) {
            log.info("Log processing finished with some exception. " + e.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void store(Row row, FileWriter writer){
        Long duration = row.getEndTime() - row.getStartTime();
        Boolean alert = duration > 4 ? true : false;
        DatabaseEntry dbEntry = new DatabaseEntry(row.getId(), duration, row.getType(), row.getHost(), alert);
        log.info(dbEntry.toString());
        HelperModules.writeInFile(dbEntry.toFormatedString(), writer);
    }
}
