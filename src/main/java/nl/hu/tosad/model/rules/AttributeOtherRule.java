package nl.hu.tosad.model.rules;

import nl.hu.tosad.model.Implementation;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeOtherRule extends AttributeRule{
    private String statement;

    public AttributeOtherRule(String name, String table, Implementation implementation, String attribute, String statement) {
        super(name, table, implementation, attribute);
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
