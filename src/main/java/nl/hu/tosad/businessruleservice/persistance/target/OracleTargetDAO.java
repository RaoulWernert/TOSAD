package nl.hu.tosad.businessruleservice.persistance.target;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.TargetDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Raoul on 1/21/2017.
 */
public class OracleTargetDAO {
    private final String GETTABLES = "select table_name from all_tables where lower(owner) = lower(?)";
    private final String GETCOLUMNS = "select column_name from cols where lower(table_name) = lower(?)";

    private Connection getConnection(TargetDatabase target) {
        try {
            Connection conn = DriverManager.getConnection(target.getUrl(), target.getUsername(), target.getPassword());
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException e) {
            throw new BusinessRuleServiceException("Could not connect to target database.");
        }
    }

    public void implement(String query, TargetDatabase target) {
        try (Connection connection = getConnection(target)) {
            connection.createStatement().execute(query);
            connection.close();
        } catch (SQLException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    public List<String> getTables(TargetDatabase target) {
        try (Connection connection = getConnection(target)) {
            PreparedStatement statement = connection.prepareStatement(GETTABLES);
            statement.setString(1, target.getUsername());
            ResultSet result = statement.executeQuery();
            List<String> tables = new ArrayList<>();
            while (result.next()) {
                tables.add(result.getString("TABLE_NAME"));
            }
            connection.close();
            return Collections.unmodifiableList(tables);
        } catch (SQLException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    public List<String> getColumns(TargetDatabase target, String tablename) {
        try (Connection connection = getConnection(target)) {
            PreparedStatement statement = connection.prepareStatement(GETCOLUMNS);
            statement.setString(1, tablename);
            ResultSet result = statement.executeQuery();
            List<String> columns = new ArrayList<>();
            while (result.next()) {
                columns.add(result.getString("COLUMN_NAME"));
            }
            connection.close();
            return Collections.unmodifiableList(columns);
        } catch (SQLException e) {
            throw new BusinessRuleServiceException(e);
        }

    }
}
