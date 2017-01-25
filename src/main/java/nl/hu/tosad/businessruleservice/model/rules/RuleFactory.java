package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.model.*;

/**
 * Created by Raoul on 11/17/2016.
 */
public class RuleFactory {
    public RuleFactory() {

    }

    public BusinessRule createRule(BusinessRuleData data) {
        RuleTypes ruleTypes = getRuleType(data.getRuleType().getCode());

        if(ruleTypes == null) {
            return null;
        }

        switch (ruleTypes){
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

    private RuleTypes getRuleType(String code) {
        for (RuleTypes ruleTypes : RuleTypes.values()) {
            if(ruleTypes.getCode().equals(code))
                return ruleTypes;
        }
        return null;
    }
}