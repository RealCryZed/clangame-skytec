package clangame.config;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcConnection {
    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:postgresql://surus.db.elephantsql.com/pzktcwmi");
        ds.setUsername("pzktcwmi");
        ds.setPassword("SEoRn8r8G6bSmJDI47cVxoeKUZgjenNB");
        ds.setMinIdle(1);
        ds.setMaxIdle(100);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private JdbcConnection(){ }
}