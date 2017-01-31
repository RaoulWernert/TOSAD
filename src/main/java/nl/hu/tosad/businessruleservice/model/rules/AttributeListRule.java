package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

import java.util.*;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeListRule extends AttributeRule {
    private ComparisonOperator comparisonOperator;
    private LogicalOperator logicalOperator;
    private List<String> values;

    public AttributeListRule(BusinessRuleData data) {
        super(data);
        try {
            comparisonOperator = ComparisonOperator.valueOf(data.getC_operator());
            logicalOperator = LogicalOperator.valueOf(data.getL_operator());
            String valuesStr = Objects.requireNonNull(data.getValue(), "AttributeListRule Values cannot be null.");
            values = new ArrayList<>(Arrays.asList(valuesStr.split("\\r\\n")));
        } catch(NullPointerException | IllegalArgumentException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    public ComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    public LogicalOperator getLogicalOperator() {
        return logicalOperator;
    }

    public List<String> getValues() {
        return Collections.unmodifiableList(values);
    }

    @Override
    public String accept(IGenerator generator) {
        return generator.generateDDL(this);
    }
}
