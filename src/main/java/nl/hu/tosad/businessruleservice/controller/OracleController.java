package nl.hu.tosad.businessruleservice.controller;

import nl.hu.tosad.businessruleservice.model.TargetDatabase;
import nl.hu.tosad.businessruleservice.model.rules.BusinessRule;
import nl.hu.tosad.businessruleservice.model.rules.Implementation;
import nl.hu.tosad.businessruleservice.model.rules.RuleTypes;
import nl.hu.tosad.businessruleservice.persistance.target.OracleTargetDAO;

import java.util.List;

public class OracleController implements IController {
    private OracleTargetDAO targetDAO;

    public OracleController() {
        targetDAO = new OracleTargetDAO();
    }

    @Override
    public List<String> getTables(TargetDatabase target) {
        return targetDAO.getTables(target);
    }

    @Override
    public List<String> getColumns(TargetDatabase target, String tablename) {
        return targetDAO.getColumns(target, tablename);
    }

    @Override
    public String getPrimaryKey(TargetDatabase target, String tablename) {
        return targetDAO.getPrimaryKey(target, tablename);
    }

    @Override
    public String getForeignKey(TargetDatabase target, String targetTable, String referencingTable) {
        return targetDAO.getForeignKey(target, targetTable, referencingTable);
    }

    @Override
    public void implement(String query, BusinessRule rule) {
        if(rule.getImplementation() == Implementation.TRIGGER) {
            if(rule.getRuleType().getCode().equals(RuleTypes.InterEntityCompareRule.getCode())) {
                String[] queries = query.split("#NEWTRG#");
                for(int i = 0; i < queries.length; i++) {
                    targetDAO.implementTrigger(queries[i], rule.getTarget(), rule.getName() + "_" + i+1);
                }
            }
            targetDAO.implementTrigger(query, rule.getTarget(), rule.getName());
        } else {
            targetDAO.implement(query, rule.getTarget());
        }
    }

    @Override
    public void delete(BusinessRule rule) {
        if(rule.getImplementation() == Implementation.TRIGGER) {
            targetDAO.dropTrigger(rule.getName(), rule.getTarget());
        } else {
            targetDAO.dropConstraint(rule.getName(), rule.getTable(), rule.getTarget());
        }
    }
}
