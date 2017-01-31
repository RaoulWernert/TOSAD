package nl.hu.tosad.businessruleservice.controller;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.TargetDatabase;
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
    public void implement(String query, String name, TargetDatabase target, boolean isTrigger) {
        if(isTrigger) {
            String[] queries = query.split("#NEWTRG#");
            if(queries.length == 1) {
                try {
                    targetDAO.implementTrigger(query, target, name);
                } catch(BusinessRuleServiceException e) {
                    targetDAO.dropTrigger(name, target);
                    throw e;
                }
            } else {
                try {
                    for (int i = 0; i < queries.length; i++) {
                        targetDAO.implementTrigger(queries[i], target, name + "_" + (i + 1));
                    }
                } catch(BusinessRuleServiceException e) {
                    for (int i = 0; i < queries.length; i++) {
                        targetDAO.dropTrigger(name + "_" + (i + 1), target);
                    }
                    throw e;
                }
            }
        } else {
            targetDAO.implement(query, target);
        }
    }

    @Override
    public void delete(String name, String table, TargetDatabase target, boolean isTrigger) {
        if(isTrigger) {
            targetDAO.dropTrigger(name, target);
        } else {
            targetDAO.dropConstraint(name, table, target);
        }
    }
}
