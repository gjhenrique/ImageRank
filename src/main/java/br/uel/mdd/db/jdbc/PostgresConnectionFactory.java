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
public class PostgresConnectionFactory implements ConnectionFactory {
    private String dsn = null;
    private Properties properties;

    public PostgresConnectionFactory() {
        this.dsn = System.getenv("JDBC_URL");
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(this.dsn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getUrl() {
        return this.dsn;
    }
}
