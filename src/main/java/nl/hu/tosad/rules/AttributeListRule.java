package nl.hu.tosad.rules;

import nl.hu.tosad.model.Operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeListRule extends AttributeRule {
    private Operator operator;
    private List<String> values;

    public AttributeListRule(String name, String table, String category, String attribute, Operator operator, String... values) {
        super(name, table, category, attribute);
        this.operator = operator;
        this.values = new ArrayList<>(Arrays.asList(values));
    }

    public Operator getOperator() {
        return operator;
    }

    public List<String> getValues() {
        return Collections.unmodifiableList(values);
    }
}
