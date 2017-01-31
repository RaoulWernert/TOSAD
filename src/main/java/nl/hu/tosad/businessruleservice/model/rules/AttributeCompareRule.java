package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

import java.util.Objects;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeCompareRule extends AttributeRule{
    private ComparisonOperator comparisonOperator;
    private String value;

    public AttributeCompareRule(BusinessRuleData data) {
        super(data);
        try {
            comparisonOperator = ComparisonOperator.valueOf(data.getC_operator());
            value = Objects.requireNonNull(data.getValue(), "AttributeCompareRule Value cannot be null.");
        } catch(NullPointerException | IllegalArgumentException e) {
            throw new BusinessRuleServiceException(e);
        }
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
