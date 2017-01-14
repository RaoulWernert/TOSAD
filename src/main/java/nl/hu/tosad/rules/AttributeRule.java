package nl.hu.tosad.rules;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeRule extends BusinessRule{
    private String attribute;

    AttributeRule(String name, String table, String category, String attribute) {
        super(name, table, category);
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}