package nl.hu.tosad.businessruleservice.persistance.repo;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.RuleType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RuleTypeDAO extends BaseDAO {
    private final String TABLE_NAME = "RULETYPES";
    private final String SELECT = "SELECT * FROM " + TABLE_NAME;

    public RuleType findById(String code) {
        try (Connection conn = super.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT + " WHERE CODE = ?");
            statement.setString(1, code);
            ResultSet rs = statement.executeQuery();
            List<RuleType> bs = selectRuleType(rs);
            if (bs.size() < 1) {
                return null;
            }
            statement.close();
            return bs.get(0);
        } catch(SQLException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    private List<RuleType> selectRuleType(ResultSet rs) throws SQLException {
        List<RuleType> targets = new ArrayList<>();
        while (rs.next()) {
            targets.add(new RuleType(
                    rs.getString("CODE"),
                    rs.getString("NAAM"),
                    rs.getString("BESCHRIJVING"),
                    rs.getInt("INS") != 0,
                    rs.getInt("UPD") != 0,
                    rs.getInt("DEL") != 0));
        }
        rs.close();
        return targets;
    }
}
