package nl.hu.tosad.rules;

import nl.hu.tosad.model.Operator;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeCompareRule extends AttributeRule{
    private Operator operator;
    private String value;

    public AttributeCompareRule(String name, String table, String category, String attribute, Operator operator, String value) {
        super(name, table, category, attribute);
        this.operator = operator;
        this.value = value;
    }

    public Operator getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }
}
