package nl.hu.tosad.businessruleservice.persistance.repo;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.BusinessRuleData;
import nl.hu.tosad.businessruleservice.model.rules.BusinessRule;
import nl.hu.tosad.businessruleservice.model.rules.RuleFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusinessRuleDAO extends BaseDAO {
    private final String TABLE_NAME = "BUSINESSRULES";
    private final String SELECT = "SELECT * FROM " + TABLE_NAME;
    private final String UPDATENAME = "UPDATE " + TABLE_NAME + " SET NAME = ? WHERE ID = ?";
    private final String UPDATEIMPLEMENTED = "UPDATE " + TABLE_NAME + " SET IMPLEMENTED = ? WHERE ID = ?";

    private RuleFactory factory;
    private TargetsDAO targetsDAO;
    private RuleTypeDAO ruleTypeDAO;

    public BusinessRuleDAO() {
        factory = new RuleFactory();
        targetsDAO = new TargetsDAO();
        ruleTypeDAO = new RuleTypeDAO();
    }

    public BusinessRule findById(int id) {
        try (Connection conn = super.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT + " WHERE ID = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            List<BusinessRule> bs = selectBusinessRule(rs);
            if (bs.size() < 1) {
                return null;
            }
            statement.close();
            return bs.get(0);
        } catch (SQLException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    public void updateName(BusinessRule rule) {
        try (Connection conn = super.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATENAME);
            statement.setString(1, rule.getName());
            statement.setInt(2, rule.getId());
            statement.executeUpdate();
            conn.commit();
            statement.close();
        } catch (SQLException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    public void setImplemented(BusinessRule rule, boolean implemented) {
        try (Connection conn = super.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATEIMPLEMENTED);
            statement.setInt(1, implemented ? 1 : 0);
            statement.setInt(2, rule.getId());
            statement.executeUpdate();
            conn.commit();
            statement.close();
        } catch (SQLException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    private List<BusinessRule> selectBusinessRule(ResultSet rs) throws SQLException {
        List<BusinessRule> rules = new ArrayList<>();
        while (rs.next()) {
            BusinessRuleData data = new BusinessRuleData(
                    rs.getInt("ID"),
                    rs.getString("NAME"),
                    targetsDAO.findById(rs.getInt("TARGET")),
                    ruleTypeDAO.findById(rs.getString("RULETYPES_CODE")),
                    rs.getInt("CREATOR"),
                    rs.getString("IMPLEMENTATION"),
                    rs.getInt("IMPLEMENTED") != 0,
                    rs.getString("TARGETTABLE"),
                    rs.getString("TARGETTABLE2"),
                    rs.getString("TARGETCOLUMN"),
                    rs.getString("TARGETCOLUMN2"),
                    rs.getString("MIN"),
                    rs.getString("MAX"),
                    rs.getString("C_OPERATOR"),
                    rs.getString("VALUE"),
                    rs.getString("L_OPERATOR"),
                    rs.getString("STATEMENT"),
                    rs.getString("ERRORMSG"),
                    rs.getString("GVARIABLES"),
                    rs.getString("BEFORESTATEMENT")
            );
            BusinessRule rule = factory.createRule(data);
            if(rule != null){
                rules.add(rule);
            }
        }
        rs.close();
        return rules;
    }
}
