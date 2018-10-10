package com.school.db;

import com.school.Props;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataBase {

    private static BasicDataSource connectionPool;

    private static synchronized void connect() {
        Props prop = Props.getInstance();
        String dbConnectUrl = "jdbc:postgresql://" + prop.getDatabaseHost() + ":" + prop.getDatabasePort() + "/" + prop.getDatabaseName();
        String dbDriver = "org.postgresql.Driver";
        System.out.printf("%s %s (DB.getConnection)\n", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()), dbConnectUrl);

        try {
            connectionPool = new BasicDataSource();
            connectionPool.setUsername(prop.getDatabaseUser());
            connectionPool.setPassword(prop.getDatabasePassword());
            connectionPool.setDriverClassName(dbDriver);
            connectionPool.setUrl(dbConnectUrl);
            connectionPool.setInitialSize(3);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized Connection getConnection(boolean isFirstTime) throws Exception {
        if (connectionPool == null || connectionPool.isClosed())
            connect();

        try {
            return connectionPool.getConnection();
        } catch (Exception e) {
            System.out.printf("%s ! %s (DB.getConnection)\n", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()), e.toString());
            if (!isFirstTime){
                Thread.sleep(30000);
            }
            connect();
            return getConnection(false);
        }

    }
}
