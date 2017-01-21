package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeCompareRule extends AttributeRule{
    private ComparisonOperator comparisonOperator;
    private String value;

    public AttributeCompareRule(BusinessRuleData data) {
        super(data);
        comparisonOperator = ComparisonOperator.valueOf(data.cOperator);
        value = data.value;
    }

    public ComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String accept(IGenerator generator) {
        return generator.generateDDL(this);
    }
}
