package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.BaseRepositoryImpl;
import ru.itmo.wp.model.repository.TalkRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TalkRepositoryImpl extends BaseRepositoryImpl<Talk> implements TalkRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public void save(Talk talk) {
        save("INSERT INTO `Talks` (`sourceUserId`, `targetUserId`, `text`, `creationTime`) VALUES (?, ?, ?, NOW())",
                talk, (PreparedStatement statement, Talk item) -> {
                    statement.setLong(1, item.getSourceUserId());
                    statement.setLong(2, item.getTargetUserId());
                    statement.setString(3, item.getText());
                });
    }

    @Override
    protected void setFields(ResultSet resultSet, String field, Talk item, int i) throws SQLException {
        switch (field) {
            case "id":
                item.setId(resultSet.getLong(i));
                break;
            case "targetUserId":
                item.setTargetUserId(resultSet.getLong(i));
                break;
            case "sourceUserId":
                item.setSourceUserId(resultSet.getLong(i));
                break;
            case "text":
                item.setText(resultSet.getString(i));
                break;
            case "creationTime":
                item.setCreationTime(resultSet.getTimestamp(i));
                break;
            default:
                // No operations.
        }
    }

    @Override
    protected Talk getNewItem() {
        return new Talk();
    }

    @Override
    public Talk find(long id) {
        return find("SELECT * FROM Talks WHERE id=?", Long.toString(id), (PreparedStatement statement, String item) -> {
            statement.setString(1, item);
        });
    }

    @Override
    public List<Talk> findAllByUserId(long userId) {
        List<Talk> talks = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Talks WHERE sourceUserId=? OR targetUserId=? ORDER BY creationTime DESC")) {
                statement.setLong(1, userId);
                statement.setLong(2, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    Talk talk;
                    while ((talk = toItem(statement.getMetaData(), resultSet)) != null) {
                        talks.add(talk);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RepositoryException("Can't find Talks.", e);
        }
        return talks;

    }

}
