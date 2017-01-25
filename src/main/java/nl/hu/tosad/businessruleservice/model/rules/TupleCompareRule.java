package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

/**
 * Created by Raoul on 1/26/2017.
 */
public class TupleCompareRule extends TupleRule {
    private ComparisonOperator operator;

    public TupleCompareRule(BusinessRuleData data) {
        super(data);
        operator = ComparisonOperator.valueOf(data.cOperator);
    }
}
