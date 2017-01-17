package nl.hu.tosad.businessruleservice.model.rules;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeOtherRule extends AttributeRule{
    private String statement;

    AttributeOtherRule(String name, String table, Implementation implementation, String attribute, String statement) {
        super(name, table, implementation, attribute);
        this.statement = statement;
    }

    public String getStatement() {
        return statement;
    }

    @Override
    public String accept(IGenerator generator) {
        return generator.generateDDL(this);
    }
}
