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
    private final String GETERRORS = "SELECT * FROM SYS.USER_ERRORS where upper(NAME) = upper(?) and TYPE = 'TRIGGER'";

    private Connection getConnection(TargetDatabase target) {
        try {
            Connection conn = DriverManager.getConnection(target.getUrl(), target.getUsername(), target.getPassword());
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException e) {
            throw new BusinessRuleServiceException("Could not connect to target database.");
        }
    }

    public void implementTrigger(String query, TargetDatabase target, String triggerName) {
        implement(query, target);

        try (Connection connection = getConnection(target)) {
            PreparedStatement statement = connection.prepareStatement(GETERRORS);
            statement.setString(1, triggerName);
            ResultSet rs = statement.executeQuery();
            List<String> errors = new ArrayList<>();
            while (rs.next()) {
                int line = rs.getInt("LINE") - 45;
                int position = rs.getInt("POSITION");
                String errormsg = rs.getString("TEXT").replace("\r", " ").replace("\n", " ");

                errors.add(String.format("Error(%d,%d): %s", line, position, errormsg));
            }
            statement.close();
            if(errors.size() > 0) {
                errors.add(0, "Compilation error");
                dropTrigger(triggerName, target);
                throw new BusinessRuleServiceException(String.join("\r\n", errors));
            }
        } catch (SQLException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    public void dropTrigger(String triggerName, TargetDatabase target) {
        try (Connection connection = getConnection(target)) {
            Statement statement = connection.createStatement();
            statement.execute("DROP TRIGGER " + triggerName.toUpperCase());
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    public void implement(String query, TargetDatabase target) {
        try (Connection connection = getConnection(target)) {
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.commit();
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
            statement.close();
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
            statement.close();
            return Collections.unmodifiableList(columns);
        } catch (SQLException e) {
            throw new BusinessRuleServiceException(e);
        }

    }
}
