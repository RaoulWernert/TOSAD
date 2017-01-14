package nl.hu.tosad.model.rules;

import nl.hu.tosad.model.ComparisonOperator;
import nl.hu.tosad.model.LogicalOperator;

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

    public AttributeListRule(String name, String table, String attribute, ComparisonOperator cOperator, LogicalOperator lOperator, String... values) {
        super(name, table, attribute);
        this.comparisonOperator = cOperator;
        this.logicalOperator = lOperator;
        this.values = new ArrayList<>(Arrays.asList(values));
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
    public String accept(ISQLGenerator generator) {
        return generator.generateSQL(this);
    }
}
