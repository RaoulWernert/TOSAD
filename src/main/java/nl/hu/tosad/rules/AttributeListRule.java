package nl.hu.tosad.rules;

import nl.hu.tosad.model.Operator;

import java.util.Collections;
import java.util.List;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeListRule extends AttributeRule {
    private Operator operator;
    private List<String> values;

    public AttributeListRule(String tab, String atr, Operator ope) {
        super(tab, atr);
        operator = ope;
    }

    public Operator.Type getOperator() {
        return operator.getType();
    }

    public List<String> getValues() {
        return Collections.unmodifiableList(values);
    }
}
