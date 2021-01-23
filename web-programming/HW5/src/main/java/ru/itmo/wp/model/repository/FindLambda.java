package ru.itmo.wp.model.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface FindLambda {
    void method(PreparedStatement statement, String item) throws SQLException;
}
