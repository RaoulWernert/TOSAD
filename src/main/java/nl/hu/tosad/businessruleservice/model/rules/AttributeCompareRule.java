package nl.hu.tosad.businessruleservice.model.rules;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeCompareRule extends AttributeRule{
    private ComparisonOperator comparisonOperator;
    private String value;

    AttributeCompareRule(String name, String table, Implementation implementation, String attribute, ComparisonOperator cOperator, String value) {
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
        return generator.generateDDL(this);
    }
}
