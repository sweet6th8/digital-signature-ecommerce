package dao;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

public class DBConnectionPool {
    private static HikariDataSource dataSource;

    static {
        try {
            Properties props = new Properties();
            props.load(DBConnectionPool.class.getClassLoader().getResourceAsStream("db.properties"));

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));

            // Các cấu hình Connection Pool
            config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            config.setMaximumPoolSize(10); // Số lượng kết nối tối đa
            config.setMinimumIdle(5); // Số lượng kết nối tối thiểu
            config.setIdleTimeout(30000); // Thời gian idle tối đa (ms)
            config.setMaxLifetime(1800000); // Thời gian tồn tại tối đa của một connection (ms)
            config.setConnectionTimeout(20000); // Thời gian chờ để lấy một connection (ms)

            // Tạo DataSource
            dataSource = new HikariDataSource(config);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing HikariCP connection pool", e);
        }
    }

    // Phương thức để lấy DataSource
    public static DataSource getDataSource() {
        return dataSource;
    }


    public static void shutdown() {
        if (dataSource != null) {
            dataSource.close();
        }


    }
    public static void main(String[] args) {
        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            System.out.println("Database connection successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
