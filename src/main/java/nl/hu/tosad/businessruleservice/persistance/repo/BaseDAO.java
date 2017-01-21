package nl.hu.tosad.businessruleservice.persistance.repo;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDAO {
    public Connection getConnection() {
        try {
            Connection conn = ((DataSource) new InitialContext().lookup("java:comp/env/jdbc/RepoDB")).getConnection();
            conn.setAutoCommit(false);
            return conn;
        } catch (NamingException |SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
