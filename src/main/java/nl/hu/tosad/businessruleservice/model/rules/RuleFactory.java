package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

/**
 * Created by Raoul on 11/17/2016.
 */
public class RuleFactory {
    public BusinessRule createRule(BusinessRuleData data) {
        RuleTypes ruleTypes = getRuleType(data.getRuleType().getCode());

        if(ruleTypes == null) {
            throw new BusinessRuleServiceException("Ruletype is null.");
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
            case TupleCompareRule:
                return new TupleCompareRule(data);
            case TupleOtherRule:
                return new TupleOtherRule(data);
            case InterEntityCompareRule:
                return new InterEntityCompareRule(data);
            case EntityOtherRule:
                return new EntityOtherRule(data);
            case ModifyRule:
                return new ModifyRule(data);
            default:
                throw new BusinessRuleServiceException("Ruletype not found.");
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