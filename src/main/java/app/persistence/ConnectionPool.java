package app.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionPool {
    public static ConnectionPool instance = null;
    public static HikariDataSource ds = null;

    private ConnectionPool() {}

    public static ConnectionPool getInstance(String user, String password, String url, String db) {
        if (instance == null) {
            ds = createHikariConnectionPool(user, password, url, db);
            instance = new ConnectionPool();
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public synchronized void close() {
        Logger.getLogger("web").log(Level.INFO, "Shutting down connection pool");
        ds.close();
    }

    private static HikariDataSource createHikariConnectionPool(String user, String password, String url, String db) {
        Logger.getLogger("web").log(Level.INFO,
                String.format("Creating connection pool for: (%s, %s, %s, %s)", user, password, url, db));
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(String.format(url, db));
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(3);
        config.setPoolName("Postgresql Pool");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }
}
