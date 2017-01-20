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
                return new AttributeRangeRule(data.name, data.table, getImplementation(data.implementation), data.attribute, data.min, data.max);
            case AttributeCompareRule:
                return new AttributeCompareRule(data.name, data.table, getImplementation(data.implementation), data.attribute, getComparisonOperator(data.cOperator), data.value);
            case AttributeListRule:
                return new AttributeListRule(data.name, data.table, getImplementation(data.implementation), data.attribute, getComparisonOperator(data.cOperator), getLogicalOperator(data.lOperator), data.value.split("\\r\\n"));
            case AttributeOtherRule:
                return new AttributeOtherRule(data.name, data.table, getImplementation(data.implementation), data.attribute, data.code);
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

    private ComparisonOperator getComparisonOperator(String code) {
        for (ComparisonOperator cOper : ComparisonOperator.values()) {
            if(cOper.getCode().equals(code))
                return cOper;
        }
        return null;
    }

    private LogicalOperator getLogicalOperator(String code) {
        for (LogicalOperator lOper : LogicalOperator.values()) {
            if(lOper.getCode().equals(code))
                return lOper;
        }
        return null;
    }

    private Implementation getImplementation(String code) {
        try {
            return Implementation.valueOf(code);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
