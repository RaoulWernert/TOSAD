package nl.hu.tosad.businessruleservice.persistance.target;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.TargetDatabase;
import nl.hu.tosad.logging.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Raoul on 1/21/2017.
 */
@SuppressWarnings("Duplicates")
public class OracleTargetDAO {
    private final String GETTABLES = "select table_name from all_tables where lower(owner) = lower(?)";
    private final String GETCOLUMNS = "select column_name from cols where lower(table_name) = lower(?)";
    private final String GETERRORS = "SELECT * FROM SYS.USER_ERRORS where upper(NAME) = upper(?) and TYPE = 'TRIGGER'";
    private final String GETPRIMARYKEYCOLUMN =
            "SELECT cols.column_name" +
            " FROM user_constraints cons, user_cons_columns cols" +
            " WHERE cols.table_name = ?" +
            " AND cons.constraint_type = 'P'" +
            " AND cons.constraint_name = cols.constraint_name" +
            " AND cons.owner = cols.owner";
    private final String GETFOREIGNKEYCOLUMN =
            "SELECT a.column_name FROM user_cons_columns a" +
            "  JOIN user_constraints c ON a.owner = c.owner AND a.constraint_name = c.constraint_name" +
            "  JOIN user_constraints c_pk ON c.r_owner = c_pk.owner AND c.r_constraint_name = c_pk.constraint_name" +
            "  where c.owner = ? and c.table_name = ? and c_pk.table_name = ?";

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
                Logger.getInstance().Log("Error:"+errormsg);
                errors.add(String.format("Error(%d,%d): %s", line, position, errormsg));
            }
            statement.close();
            if(errors.size() > 0) {
                errors.add(0, "Compilation error");
//                dropTrigger(triggerName, target);
                throw new BusinessRuleServiceException(String.join("\r\n", errors));
            }else{
                Logger.getInstance().Log("Implemented"+triggerName);
            }
        } catch (SQLException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    public void dropTrigger(String triggerName, TargetDatabase target) {
        try (Connection connection = getConnection(target)) {
            Statement statement = connection.createStatement();
            statement.execute("DROP TRIGGER " + triggerName.toUpperCase());
            Logger.getInstance().Log("Dropped"+triggerName);
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    public void implement(String query, TargetDatabase target) {
        try (Connection connection = getConnection(target)) {
            Statement statement = connection.createStatement();
            Logger.getInstance().Log("Executed query: "+query);
            statement.execute(query);
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    public void dropConstraint(String constraintName, String table, TargetDatabase target) {
        String query = String.format("ALTER TABLE %s DROP CONSTRAINT %s", table, constraintName);
        try (Connection connection = getConnection(target)) {
            Statement statement = connection.createStatement();
            Logger.getInstance().Log("Dropped"+constraintName);
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

    public String getPrimaryKey(TargetDatabase target, String targetTable) {
        try (Connection connection = getConnection(target)) {
            PreparedStatement statement = connection.prepareStatement(GETPRIMARYKEYCOLUMN);
            statement.setString(1, targetTable.toUpperCase());
            ResultSet result = statement.executeQuery();

            List<String> columns = new ArrayList<>();
            while (result.next()) {
                columns.add(result.getString("COLUMN_NAME"));
            }
            return columns.size() > 0 ? columns.get(0) : null;
        } catch(SQLException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    public String getForeignKey(TargetDatabase target, String targetTable, String referencingTable) {
        try (Connection connection = getConnection(target)) {
            PreparedStatement statement = connection.prepareStatement(GETFOREIGNKEYCOLUMN);
            statement.setString(1, target.getUsername().toUpperCase());
            statement.setString(2, targetTable.toUpperCase());
            statement.setString(3, referencingTable.toUpperCase());
            ResultSet result = statement.executeQuery();
            List<String> columns = new ArrayList<>();
            while (result.next()) {
                columns.add(result.getString("COLUMN_NAME"));
            }
            return columns.size() > 0 ? columns.get(0) : null;
        } catch(SQLException e) {
            throw new BusinessRuleServiceException(e);
        }
    }
}
