package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    private static Logger logger = Logger.getLogger(Util.class.getName());
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;

    static {
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(System.getenv("DB_URL"),
                    System.getenv("DB_USERNAME"),
                    System.getenv("DB_PASSWORD"));
        } catch ( ClassNotFoundException
                  | SQLException e) {
            logger.log(Level.WARNING, e.toString());
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
