package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.model.*;

/**
 * Created by Raoul on 11/17/2016.
 */
public class RuleFactory {
    public RuleFactory() {

    }

    public BusinessRule createRule(BusinessRuleData data) {
        RuleType ruleType = getRuleType(data.ruletype_code);

        if(ruleType == null) {
            return null;
        }

        switch (ruleType){
            case AttributeRangeRule:
                return new AttributeRangeRule(data);
            case AttributeCompareRule:
                return new AttributeCompareRule(data);
            case AttributeListRule:
                return new AttributeListRule(data);
            case AttributeOtherRule:
                return new AttributeOtherRule(data);
            default:
                return null;
        }
    }

    private RuleType getRuleType(String code) {
        for (RuleType ruleType : RuleType.values()) {
            if(ruleType.getCode().equals(code))
                return ruleType;
        }
        return null;
    }
}