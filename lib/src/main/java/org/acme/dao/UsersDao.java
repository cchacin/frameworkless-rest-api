package org.acme.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsersDao {
    private final DataSource datasource;

    private enum DatabaseType {
        POSTGRES,
    }

    public UsersDao() {
        datasource = switch (DatabaseType.valueOf(System.getProperty("DB_TYPE", "POSTGRES"))) {
            case POSTGRES -> getPostgresDataSource();
        };
    }

    private static DataSource getPostgresDataSource() {
        var config = new HikariConfig();
        config.setJdbcUrl(
                System.getProperty(
                        "DB_JDBC_URL",
                        "jdbc:postgresql://127.0.0.1:5432/postgres"
                )
        );
        config.setUsername(
                System.getProperty(
                        "DB_USERNAME",
                        "postgres"
                )
        );
        config.setPassword(
                System.getProperty(
                        "DB_PASSWORD",
                        "postgres"
                )
        );
        return new HikariDataSource(config);
    }

    public void createUser(
            UserEntity entity
    ) {
        var query = """
                INSERT INTO users (id, firstName, lastName) VALUES (?, ?, ?);
                """;
        try (var cnn = datasource.getConnection();
             var statement = cnn.prepareStatement(query)) {
            statement.setObject(1, entity.userId());
            statement.setString(2, entity.firstName());
            statement.setString(3, entity.lastName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserEntity> users() {
        var users = new ArrayList<UserEntity>();
        var query = """
                SELECT id, firstName, lastName FROM users;
                """;
        try (var cnn = datasource.getConnection();
             var statement = cnn.prepareStatement(query);
             var resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(
                        new UserEntity(
                                UUID.fromString(resultSet.getString("id")),
                                resultSet.getString("firstName"),
                                resultSet.getString("lastName")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
