package nl.hu.tosad.model.rules;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeOtherRule extends AttributeRule{
    private String statement;

    public AttributeOtherRule(String name, String table, String attribute, String statement) {
        super(name, table, attribute);
        this.statement = statement;
    }

    public String getStatement() {
        return statement;
    }

    @Override
    public String accept(ISQLGenerator generator) {
        return generator.generateSQL(this);
    }
}
