package com.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.DriverManager;

public class HelperModules {
    private static final ObjectMapper objectMapper = new ObjectMapper();

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
            con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/logdb", "Shivangi", "");
            if (con!= null){
                System.out.println("Connection created successfully");

            }else{
                System.out.println("Problem with creating connection");
            }

        }  catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
