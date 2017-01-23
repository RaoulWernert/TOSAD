package nl.hu.tosad.businessruleservice;

import nl.hu.tosad.businessruleservice.controller.IController;
import nl.hu.tosad.businessruleservice.controller.OracleController;
import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.generator.BaseGenerator;
import nl.hu.tosad.businessruleservice.model.TargetDatabase;
import nl.hu.tosad.businessruleservice.model.rules.BusinessRule;
import nl.hu.tosad.businessruleservice.persistance.repo.BusinessRuleDAO;
import nl.hu.tosad.businessruleservice.persistance.repo.TargetsDAO;

import java.util.List;

public class BusinessRuleService {
    private static BusinessRuleService instance;

    public synchronized static BusinessRuleService getInstance() {
        if(instance == null)
            instance = new BusinessRuleService();

        return instance;
    }

    private IController oracleController;
    private BusinessRuleDAO businessRuleDAO;
    private TargetsDAO targetsDAO;
    private BaseGenerator generator;

    private BusinessRuleService() {
        oracleController = new OracleController();
        businessRuleDAO = new BusinessRuleDAO();
        targetsDAO = new TargetsDAO();
        generator = new BaseGenerator();
    }

    public synchronized String generate(int ruleid) {
        BusinessRule rule = businessRuleDAO.findById(ruleid);
        if (rule == null) {
            throw new BusinessRuleServiceException("Rule ID not found.");
        }
        businessRuleDAO.updateName(rule);

        String query = rule.accept(generator);
        IController controller = getController(rule.getTarget().getType());

        controller.implement(query, rule.getTarget());
        return query;
    }

    public synchronized List<String> getTables(int id) {
        TargetDatabase target = targetsDAO.findById(id);
        if(target == null) {
            throw new BusinessRuleServiceException("Target database not found.");
        }
        return getController(target.getType()).getTables(target);
    }

    public synchronized List<String> getColumns(int id, String tablename) {
        TargetDatabase target = targetsDAO.findById(id);
        if(target == null) {
            throw new BusinessRuleServiceException("Target database not found.");
        }
        return getController(target.getType()).getColumns(target, tablename);
    }

    private IController getController(String type) {
        switch (type) {
            case "ORACLE":
                return oracleController;
            case "MYSQL":
                throw new BusinessRuleServiceException("Target database type not found.");
        }
        throw new BusinessRuleServiceException("Target database type not found.");
    }
}
