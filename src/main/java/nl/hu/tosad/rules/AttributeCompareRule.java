package nl.hu.tosad.rules;

import nl.hu.tosad.model.ComparisonOperator;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeCompareRule extends AttributeRule{
    private ComparisonOperator comparisonOperator;
    private String value;

    public AttributeCompareRule(String name, String table, String attribute, ComparisonOperator cOperator, String value) {
        super(name, table, attribute);
        this.comparisonOperator = cOperator;
        this.value = value;
    }

    public ComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    public String getValue() {
        return value;
    }
}
