package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.AbstractModel;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.*;

public abstract class BaseRepositoryImpl<T extends AbstractModel> {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    protected void save(String request, T item, SaveLambda<T> statementLambda) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request,
                    Statement.RETURN_GENERATED_KEYS)) {
                statementLambda.method(statement, item);
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save " + getNewItem().getClass().getName() + ".");
                } else {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        item.setId(generatedKeys.getLong(1));
                        item.setCreationTime(find(item.getId()).getCreationTime());
                    } else {
                        throw new RepositoryException("Can't save " + getNewItem().getClass().getName() +
                                " [no autogenerated fields].");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save " + getNewItem().getClass().getName(), e);
        }
    }

    protected T find(String request, String item, FindLambda lambda) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request)) {
                lambda.method(statement, item);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toItem(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + getNewItem().getClass().getName(), e);
        }
    }

    protected T toItem(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }
        T item = getNewItem();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            setFields(resultSet, metaData.getColumnName(i), item, i);
        }
        return item;
    }

    public abstract T find(long id);

    protected abstract void setFields(ResultSet resultSet, String field, T item, int i) throws SQLException;

    protected abstract T getNewItem();

}
