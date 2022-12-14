package jm.task.core.jdbc.dao;

import static jm.task.core.jdbc.util.Util.getConnection;
import jm.task.core.jdbc.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class UserDaoJDBCImpl implements UserDao {
    private static Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = getConnection().createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Users (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20), lastName VARCHAR(20), age TINYINT)");
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.toString());
        }
    }

    public void dropUsersTable() {
        try (Statement statement = getConnection().createStatement()) {
            statement.execute("DROP TABLE IF EXISTS Users");
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.toString());
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO Users (name, lastName, age) VALUES(?, ?, ?)")) {
            connection.setAutoCommit(false);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.toString());
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void removeUserById(long id) throws SQLException {
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Users WHERE id = ?")) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.toString());
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                result.add(user);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.toString());
        }
        return result;
    }

    public void cleanUsersTable() throws SQLException {
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute("TRUNCATE TABLE Users");
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            logger.log(Level.WARNING, e.toString());
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
