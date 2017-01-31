package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

/**
 * Created by Raoul on 1/26/2017.
 */
public class TupleCompareRule extends TupleRule {
    private ComparisonOperator operator;

    public TupleCompareRule(BusinessRuleData data) {
        super(data);
        try {
            operator = ComparisonOperator.valueOf(data.getC_operator());
        } catch(IllegalArgumentException e) {
            throw new BusinessRuleServiceException(e);
        }

    }

    public ComparisonOperator getOperator() {
        return operator;
    }

    @Override
    public String accept(IGenerator generator) {
        return generator.generateDDL(this);
    }
}
