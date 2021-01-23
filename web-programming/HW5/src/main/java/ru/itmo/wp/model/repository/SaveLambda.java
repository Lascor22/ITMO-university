package ru.itmo.wp.model.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SaveLambda<T> {
    void method(PreparedStatement statement, T item) throws SQLException;
}