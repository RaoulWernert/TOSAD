package nl.hu.tosad.businessruleservice.persistance;

import nl.hu.tosad.businessruleservice.model.BusinessRuleData;
import nl.hu.tosad.businessruleservice.model.rules.BusinessRule;
import nl.hu.tosad.businessruleservice.model.rules.RuleFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RepoDAO {
    private List<BusinessRuleData> businessRuleData; // TODO: Wordt later vervangen door database calls.
    private RuleFactory factory;

    public RepoDAO() {
        factory = new RuleFactory();
        businessRuleData = new ArrayList<>(Arrays.asList(
                new BusinessRuleData(1, "ARNG1", "MEDEWERKERS", "SALARIS", "100", "1000", "", "", "", "", "ARNG", "SQL", "CONDITION"),
                new BusinessRuleData(2, "ACMP1", "MEDEWERKERS", "SALARIS", "", "", ">", "", "10", "", "ACMP", "SQL", "CONDITION"),
                new BusinessRuleData(3, "ALIS1", "MEDEWERKERS", "NAAM", "", "", "IN", "", "JANTJE\r\nKLAAS", "", "ALIS", "SQL", "CONDITION"),
                new BusinessRuleData(4, "AOTH1", "MEDEWERKERS", "NAAM", "", "", "", "", "", "substr(naam,1,1) between ('1' and '9')", "AOTH", "SQL", "CONDITION")));
    }

    public BusinessRule getBusinessRule(int ruleid) {
        return factory.createRule(businessRuleData.get(ruleid - 1));
    }
}
