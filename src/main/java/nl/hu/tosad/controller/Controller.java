package nl.hu.tosad.controller;

import nl.hu.tosad.RuleFactory;
import nl.hu.tosad.model.BusinessRuleData;
import nl.hu.tosad.model.rules.BusinessRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {
    private RuleFactory factory;
    private OracleController oracleController;

    private List<BusinessRuleData> businessRuleData;

    public Controller() {
        factory = new RuleFactory();
        oracleController = new OracleController();
        businessRuleData = new ArrayList<>(Arrays.asList(
                new BusinessRuleData(1, "ARNG1", "MEDEWERKERS", "SALARIS", "100", "1000", "", "", "", "", "ARNG", "SQL"),
                new BusinessRuleData(2, "ACMP1", "MEDEWERKERS", "SALARIS", "", "", ">", "", "10", "", "ACMP", "SQL"),
                new BusinessRuleData(3, "ALIS1", "MEDEWERKERS", "NAAM", "", "", "IN", "", "JANTJE\r\nKLAAS", "", "ALIS", "SQL"),
                new BusinessRuleData(4, "AOTH1", "MEDEWERKERS", "NAAM", "", "", "", "", "", "substr(naam,1,1) between ('1' and '9')", "AOTH", "SQL")));
    }

    public boolean generate(int ruleid) {
        BusinessRule br = factory.createRule(businessRuleData.get(ruleid - 1));
        return oracleController.generate(br);
    }
}
