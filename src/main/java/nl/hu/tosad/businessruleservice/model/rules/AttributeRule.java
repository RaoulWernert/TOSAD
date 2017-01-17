package nl.hu.tosad.businessruleservice.model.rules;

/**
 * Created by Raoul on 11/17/2016.
 */
public abstract class AttributeRule extends BusinessRule{
    private String attribute;

    AttributeRule(String name, String table, Implementation implementation, String attribute) {
        super(name, table, implementation);
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}