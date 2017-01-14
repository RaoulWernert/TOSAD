package nl.hu.tosad;

import nl.hu.tosad.model.BusinessRuleData;
import nl.hu.tosad.model.ComparisonOperator;
import nl.hu.tosad.model.LogicalOperator;
import nl.hu.tosad.model.RuleType;
import nl.hu.tosad.rules.*;

/**
 * Created by Raoul on 11/17/2016.
 */
public class RuleFactory {
    public RuleFactory() {

    }

    public AttributeRule createRule(BusinessRuleData data) {
        RuleType ruleType = getRuleType(data.code);

        if(ruleType == null) {
            return null;
        }

        switch (ruleType){
            case AttributeRangeRule:
                return new AttributeRangeRule(data.name, data.table, data.attribute, data.min, data.max);
            case AttributeCompareRule:
                return new AttributeCompareRule(data.name, data.table, data.attribute, getComparisonOperator(data.cOperator), data.value);
            case AttributeListRule:
                return new AttributeListRule(data.name, data.table, data.attribute, getComparisonOperator(data.cOperator), getLogicalOperator(data.lOperator), data.values.split("\\r\\n"));
            case AttributeOtherRule:
                return new AttributeOtherRule(data.name, data.table, data.attribute, data.statement);
            default:
                return null; //TODO Error message
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
}
