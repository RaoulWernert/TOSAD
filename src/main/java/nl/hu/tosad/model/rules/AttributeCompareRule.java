package nl.hu.tosad.model.rules;

import nl.hu.tosad.model.ComparisonOperator;
import nl.hu.tosad.model.Implementation;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeCompareRule extends AttributeRule{
    private ComparisonOperator comparisonOperator;
    private String value;

    public AttributeCompareRule(String name, String table, Implementation implementation, String attribute, ComparisonOperator cOperator, String value) {
        super(name, table, implementation, attribute);
        this.comparisonOperator = cOperator;
        this.value = value;
    }

    public ComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String accept(IGenerator generator) {
        return generator.generateSQL(this);
    }
}
