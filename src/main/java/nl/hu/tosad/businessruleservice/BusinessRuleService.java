package nl.hu.tosad.businessruleservice;

import nl.hu.tosad.businessruleservice.controller.IController;
import nl.hu.tosad.businessruleservice.controller.OracleController;
import nl.hu.tosad.businessruleservice.model.rules.BusinessRule;
import nl.hu.tosad.businessruleservice.persistance.RepoDAO;

public class BusinessRuleService {
    private static BusinessRuleService instance;

    public synchronized static BusinessRuleService getInstance() {
        if(instance == null)
            instance = new BusinessRuleService();

        return instance;
    }

    private IController oracleController;
    private RepoDAO repoDAO;

    private BusinessRuleService() {
        oracleController = new OracleController();
        repoDAO = new RepoDAO();
    }

    public synchronized boolean generate(int ruleid) {
        BusinessRule rule = repoDAO.getBusinessRule(ruleid);
        return oracleController.generate(rule);
    }
}
