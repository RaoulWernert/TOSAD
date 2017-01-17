package nl.hu.tosad.businessruleservice;

import nl.hu.tosad.businessruleservice.controller.IController;
import nl.hu.tosad.businessruleservice.controller.OracleController;
import nl.hu.tosad.businessruleservice.persistance.RepoDAO;

public class BusinessRuleService {
    private static BusinessRuleService instance;

    public static BusinessRuleService getInstance() {
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

    public boolean generate(int ruleid) {
        return oracleController.generate(repoDAO.getBusinessRule(ruleid));
    }
}
