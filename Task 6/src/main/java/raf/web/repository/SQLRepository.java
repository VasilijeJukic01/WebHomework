package raf.web.repository;

import raf.web.logger.LogHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SQLRepository {

    @FunctionalInterface
    protected interface ResultSetMapper<T> {
        T map(ResultSet resultSet) throws SQLException;
    }

    public SQLRepository(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected Connection newConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://" + this.getHost() + ":" + this.getPort() + "/" + this.getDbName(), this.getUsername(), this.getPassword()
        );
    }

    protected String getHost() {
        return "localhost";
    }

    protected int getPort() {
        return 3306;
    }

    protected String getDbName() {
        return "db";
    }

    protected String getUsername() {
        return "root";
    }

    protected String getPassword() {
        return "";
    }

    protected <T> List<T> executeQuery(String query, ResultSetMapper<T> mapper, Object... params) {
        List<T> results = new ArrayList<>();
        try (Connection connection = this.newConnection();
             PreparedStatement preparedStatement = createPreparedStatement(connection, query, params);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                results.add(mapper.map(resultSet));
            }
        } catch (SQLException e) {
            LogHelper.getInstance().logInfo(getClass(), e.getMessage());
        }
        return results;
    }

    protected void executeUpdate(String query, Object... params) {
        try (Connection connection = this.newConnection();
            PreparedStatement preparedStatement = createPreparedStatement(connection, query, params)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LogHelper.getInstance().logInfo(getClass(), e.getMessage());
        }
    }

    protected PreparedStatement createPreparedStatement(Connection connection, String query, Object... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement;
    }

}
