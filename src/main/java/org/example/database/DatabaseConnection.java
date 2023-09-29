package org.example.database;

import org.example.ConfigLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static volatile DatabaseConnection instance;
    private Connection connection;
    private String url;
    private String username;
    private String password;

    private DatabaseConnection() {
        loadConfig();
    }

    public static DatabaseConnection getInstance() {
        DatabaseConnection result = instance;
        if (result == null) {
            synchronized (DatabaseConnection.class) {
                result = instance;
                if (result == null) {
                    instance = result = new DatabaseConnection();
                }
            }
        }
        return result;
    }

    private void loadConfig() {
        Properties properties = ConfigLoader.loadConfig();
        url = properties.getProperty("db.url");
        username = properties.getProperty("db.username");
        password = properties.getProperty("db.password");
    }

    public Connection getConnection() {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DriverManager.getConnection(this.url, this.username, this.password);
            }
        } catch (SQLException var2) {
            var2.printStackTrace();
            // Add a log statement here to understand if there's an issue in connection
            System.err.println("Error in getting Database Connection: " + var2.getMessage());
        }
        // Add a null check here
        if (this.connection == null) {
            System.err.println("Database Connection is null");
        }
        return this.connection;
    }

}
