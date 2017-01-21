package nl.hu.tosad.businessruleservice.persistance.repo;

import nl.hu.tosad.businessruleservice.model.BusinessRuleData;
import nl.hu.tosad.businessruleservice.model.rules.BusinessRule;
import nl.hu.tosad.businessruleservice.model.rules.RuleFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BusinessRuleDAO extends BaseDAO {
    private final String TABLE_NAME = "BUSINESSRULES";
    private final String SELECT = "SELECT * FROM " + TABLE_NAME;

    private RuleFactory factory;

    public BusinessRuleDAO() {
        factory = new RuleFactory();
    }

    public List<BusinessRule> findAll() {
        try (Connection conn = super.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT);
            ResultSet rs = statement.executeQuery();
            return selectBusinessRule(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BusinessRule findById(int id) {
        try (Connection conn = super.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT + " WHERE ID = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            List<BusinessRule> bs = selectBusinessRule(rs);
            return bs.size() > 0 ? bs.get(0) : null;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<BusinessRule> selectBusinessRule(ResultSet rs) throws SQLException {
        List<BusinessRuleData> bsdata = new ArrayList<>();
        while (rs.next()) {
            bsdata.add(new BusinessRuleData(
                    rs.getInt("ID"),
                    rs.getString("NAME"),
                    rs.getString("TARGETTABLE"),
                    rs.getString("ATTRIBUTE"),
                    rs.getString("MIN"),
                    rs.getString("MAX"),
                    rs.getString("OPERATOR"),
                    rs.getString("LOPERATOR"),
                    rs.getString("VALUE"),
                    rs.getString("CODE"),
                    rs.getString("RULETYPES_CODE"),
                    rs.getString("TARGETDATABASE"),
                    rs.getString("IMPLEMENTATION")
            ));
        }
        rs.close();
        return bsdata.stream()
                .map(factory::createRule)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
