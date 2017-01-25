package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeListRule extends AttributeRule {
    private ComparisonOperator comparisonOperator;
    private LogicalOperator logicalOperator;
    private List<String> values;

    public AttributeListRule(BusinessRuleData data) {
        super(data);
        comparisonOperator = ComparisonOperator.valueOf(data.getC_operator());
        logicalOperator = LogicalOperator.valueOf(data.getL_operator());
        values = new ArrayList<>(Arrays.asList(data.getValue().split("\\r\\n")));
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
