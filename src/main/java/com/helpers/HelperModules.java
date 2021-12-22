package com.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.processor.LogProcessor;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class HelperModules {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final org.apache.log4j.Logger log = Logger.getLogger(HelperModules.class);
    public static ObjectMapper getObjectMapper(){
        return objectMapper;
    }


    public static void initDataBase(){

        // java -cp ../lib/hsqldb.jar org.hsqldb.server.Server --database.0 file.logdb --dbname0.logdb

        Connection con = null;
        try {
            //Registering the HSQLDB JDBC driver
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            //Creating the connection with HSQLDB
            con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9111/logdb", "Shivangi", "");
            if (con!= null){
                log.info("Connection created successfully");

            }else{
                log.info("Problem with creating connection");
            }

        }  catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }


        public static void writeInFile(String data, FileWriter writer) {
                try {
                    writer.write(data);
                    writer.write('\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
}

