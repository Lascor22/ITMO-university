package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.BaseRepositoryImpl;
import ru.itmo.wp.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public void save(User user, String passwordSha) {
        save("INSERT INTO `User` (`login`, `email`, `passwordSha`, `creationTime`) VALUES (?, ?, ?, NOW())",
                user, (PreparedStatement statement, User item) -> {
                    statement.setString(1, item.getLogin());
                    statement.setString(2, item.getEmail());
                    statement.setString(3, passwordSha);
                });
    }

    @Override
    public User find(long id) {
        return find("SELECT * FROM User WHERE id=?", Long.toString(id), (PreparedStatement statement, String item) -> {
            statement.setString(1, item);
        });
    }

    @Override
    protected void setFields(ResultSet resultSet, String field, User item, int i) throws SQLException {
        switch (field) {
            case "id":
                item.setId(resultSet.getLong(i));
                break;
            case "login":
                item.setLogin(resultSet.getString(i));
                break;
            case "email":
                item.setEmail(resultSet.getString(i));
                break;
            case "creationTime":
                item.setCreationTime(resultSet.getTimestamp(i));
                break;
            default:
                // No operations.
        }
    }

    @Override
    protected User getNewItem() {
        return new User();
    }

    @Override
    public User findByLogin(String login) {
        return find("SELECT * FROM User WHERE login=?", login, (PreparedStatement statement, String item) -> {
            statement.setString(1, item);
        });
    }

    @Override
    public User findByEmail(String email) {
        return find("SELECT * FROM User WHERE email=?", email, (PreparedStatement statement, String item) -> {
            statement.setString(1, item);
        });
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        return find("SELECT * FROM User WHERE login=? AND passwordSha=?", login, (PreparedStatement statement, String item) -> {
            statement.setString(1, item);
            statement.setString(2, passwordSha);
        });
    }

    @Override
    public User findByEmailAndPasswordSha(String email, String passwordSha) {
        return find("SELECT * FROM User WHERE email=? AND passwordSha=?", email, (PreparedStatement statement, String item) -> {
            statement.setString(1, item);
            statement.setString(2, passwordSha);
        });
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM User ORDER BY id DESC")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    User user;
                    while ((user = toItem(statement.getMetaData(), resultSet)) != null) {
                        users.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
        return users;
    }

}