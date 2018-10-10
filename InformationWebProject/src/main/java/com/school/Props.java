package com.school;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Props {

    private static final String PROPERTIES_FILE = "./env.properties";

    private static final Logger logger = LogManager.getLogger(Props.class);

    private static final Props instance = new Props();

    private String databaseHost;

    private String databasePort;

    private String databaseUser;

    private String databasePassword;

    private String databaseName;

    public String getDatabaseHost() {
        return databaseHost;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    private Props(){
        init();
    }

    public static Props getInstance(){
        return instance;
    }

    private void init() {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream(PROPERTIES_FILE);
            prop.load(input);
        } catch (Exception ex) {
            logger.warn("Properties file could not been found: {}", PROPERTIES_FILE);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String databaseHost = prop.getProperty("database_host");
        String databasePort = prop.getProperty("database_port");
        String databaseUser = prop.getProperty("database_username");
        String databasePassword = prop.getProperty("database_password");
        String databaseName = prop.getProperty("database_name");

        this.databaseHost = defined(databaseHost) ? databaseHost : Constants.DATABASE_HOST;
        this.databasePort = defined(databasePort) ? databasePort : Constants.DATABASE_PORT;
        this.databaseUser = defined(databaseUser) ? databaseUser : Constants.DATABASE_USERNAME;
        this.databasePassword = defined(databasePassword) ? databasePassword : Constants.DATABASE_PASSWORD;
        this.databaseName = defined(databaseName) ? databaseName : Constants.DATABASE_NAME;

    }

    private boolean defined(String s) {
        return s != null && !s.isEmpty();
    }

}
