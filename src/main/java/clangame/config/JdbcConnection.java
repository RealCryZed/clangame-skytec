package clangame.config;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcConnection {
    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:h2:mem:clangame;DB_CLOSE_DELAY=-1");
        ds.setUsername("realcryzed");
        ds.setPassword("password");
        ds.setMinIdle(1);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private JdbcConnection(){ }
}