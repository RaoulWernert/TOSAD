package nl.hu.tosad.rules;

import nl.hu.tosad.model.Operator;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeCompareRule extends AttributeRule{
    private Operator operator;
    private String value;

    public AttributeCompareRule(String tab, String atr, Operator op, String val) {
        super(tab, atr);
        operator = op;
        value = val;
    }

    public Operator.Type getOperator() {
        return operator.getType();
    }

    public String getValue() {
        return value;
    }
}
