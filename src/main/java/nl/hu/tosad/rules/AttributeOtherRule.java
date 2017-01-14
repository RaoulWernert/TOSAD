package nl.hu.tosad.rules;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeOtherRule extends AttributeRule{
    private String statement;

    public AttributeOtherRule(String name, String table, String category, String attribute, String statement) {
        super(name, table, category, attribute);
        this.statement = statement;
    }

    public String getStatement() {
        return statement;
    }
}
