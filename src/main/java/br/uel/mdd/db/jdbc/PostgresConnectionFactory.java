package br.uel.mdd.db.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 28/05/14.
 */
public class PostgresConnectionFactory implements ConnectionFactory{
    private String dsn = null;
    private Properties properties;

    public PostgresConnectionFactory(){
        this.dsn = "jdbc:postgresql://";
    }

    @Override
    public void readProperties() {
        properties = new Properties();
        InputStream inputStream = null;

        inputStream = this.getClass().getResourceAsStream("/database.properties");
        try {
            properties.load(inputStream);
            this.dsn += properties.getProperty("host", "localhost") + ":";
            this.dsn += properties.getProperty("port", "5432") + "/";
            this.dsn += properties.getProperty("database");

        } catch (IOException ioException) {
            System.out.println("O arquivo de configuração não foi " +
                    "encontrado, por favor crie o arquivo com " +
                    "as seguintes chaves: \n" +
                    "host;port;database;user;password;ssl");
            ioException.printStackTrace();
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Connection getConnection() {
        this.readProperties();
        if(this.properties.getProperty("user") != null &&
                this.properties.getProperty("password") != null){
            try {
                return DriverManager.getConnection(this.dsn, this.properties);
            } catch (SQLException e) {
                System.out.println("The connection could not be created, the error was: " +
                        e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }
}
