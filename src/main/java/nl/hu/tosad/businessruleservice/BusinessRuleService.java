package nl.hu.tosad.businessruleservice;

import nl.hu.tosad.businessruleservice.controller.IController;
import nl.hu.tosad.businessruleservice.controller.OracleController;
import nl.hu.tosad.businessruleservice.model.rules.BusinessRule;
import nl.hu.tosad.businessruleservice.persistance.repo.BusinessRuleDAO;

public class BusinessRuleService {
    private static BusinessRuleService instance;

    public synchronized static BusinessRuleService getInstance() {
        if(instance == null)
            instance = new BusinessRuleService();

        return instance;
    }

    private IController oracleController;
    private BusinessRuleDAO businessRuleDAO;

    private BusinessRuleService() {
        oracleController = new OracleController();
        businessRuleDAO = new BusinessRuleDAO();
    }

    public synchronized boolean generate(int ruleid) {
        BusinessRule rule = businessRuleDAO.findById(ruleid);

        if(rule == null)
            return false;

        return oracleController.generate(businessRuleDAO.findById(ruleid));
    }
}
