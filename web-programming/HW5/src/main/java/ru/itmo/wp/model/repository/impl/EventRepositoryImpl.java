package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.EventTypes;
import ru.itmo.wp.model.repository.BaseRepositoryImpl;
import ru.itmo.wp.model.repository.EventRepository;

import java.sql.*;

public class EventRepositoryImpl extends BaseRepositoryImpl<Event> implements EventRepository {

    @Override
    public void save(Event event) {
        save("INSERT INTO `Event` (`userId`, `type`, `creationTime`) VALUES (?, ?, NOW())",
                event, (PreparedStatement statement, Event item) -> {
                    statement.setLong(1, item.getUserId());
                    statement.setString(2, item.getType().name());
                });
    }

    @Override
    protected Event getNewItem() {
        return new Event();
    }

    @Override
    protected void setFields(ResultSet resultSet, String field, Event item, int i) throws SQLException {
        switch (field) {
            case "id":
                item.setId(resultSet.getLong(i));
                break;
            case "userId":
                item.setUserId(resultSet.getLong(i));
                break;
            case "type":
                item.setType(EventTypes.valueOf(resultSet.getString(i)));
                break;
            case "creationTime":
                item.setCreationTime(resultSet.getTimestamp(i));
                break;
            default:
                // No operations.
        }
    }

    @Override
    public Event find(long id) {
        return find("SELECT * FROM Event WHERE id=?", Long.toString(id), (PreparedStatement statement, String item) -> {
            statement.setString(1, item);
        });
    }
}
