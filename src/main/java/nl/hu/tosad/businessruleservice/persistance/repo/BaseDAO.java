package nl.hu.tosad.businessruleservice.persistance.repo;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

abstract class BaseDAO {
    Connection getConnection() {
        try {
            Connection conn = ((DataSource) new InitialContext().lookup("java:comp/env/jdbc/RepoDB")).getConnection();
            conn.setAutoCommit(false);
            return conn;
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
            throw new BusinessRuleServiceException("Could not connect to repo database.");
        }
    }
}
