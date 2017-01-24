package nl.hu.tosad.businessruleservice.persistance.repo;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.TargetDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raoul on 1/22/2017.
 */
public class TargetsDAO extends BaseDAO {
    private final String TABLE_NAME = "TARGETS";
    private final String SELECT = "SELECT * FROM " + TABLE_NAME;

    public TargetDatabase findById(int id) {
        try (Connection conn = super.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT + " WHERE ID = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            List<TargetDatabase> bs = selectTarget(rs);
            if (bs.size() < 1) {
                return null;
            }
            statement.close();
            return bs.get(0);
        } catch(SQLException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    private List<TargetDatabase> selectTarget(ResultSet rs) throws SQLException {
        List<TargetDatabase> targets = new ArrayList<>();
        while (rs.next()) {
            targets.add(new TargetDatabase(rs.getInt("ID"),
                    rs.getString("TYPE"),
                    rs.getString("USERNAME"),
                    rs.getString("PASSWORD"),
                    rs.getString("URL"),
                    rs.getString("NAME"),
                    rs.getInt("OWNER")));
        }
        rs.close();
        return targets;
    }
}
