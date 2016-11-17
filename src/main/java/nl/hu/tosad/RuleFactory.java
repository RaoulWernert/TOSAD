package nl.hu.tosad;

import nl.hu.tosad.model.BusinessRuleData;
import nl.hu.tosad.model.Operator;
import nl.hu.tosad.model.RuleType;
import nl.hu.tosad.rules.*;

/**
 * Created by Raoul on 11/17/2016.
 */
public class RuleFactory{
    public RuleFactory(){

    }
    public AttributeRule createRule(BusinessRuleData data) {
        RuleType.Type ruleType = RuleType.getTypeFromStr(data.code);
        if (ruleType == null){
            return null;//TODO Error message
        }
        switch (ruleType){
            case AttributeRangeRule:
                return new AttributeRangeRule(data.table, data.attribute, data.min, data.max);
            case AttributeCompareRule:
                return new AttributeCompareRule(data.table, data.attribute, new Operator(Operator.Type.valueOf(data.operator)), data.value);//TODO misschien moet operator anders
            case AttributeListRule:
                return new AttributeListRule(data.table, data.attribute, new Operator(Operator.Type.valueOf(data.operator)));//TODO misschien moet operator anders
            case AttributeOtherRule:
                return new AttributeOtherRule(data.table, data.attribute, data.statement);
        }
        return null;//TODO Error message
    }
}
