package nl.hu.tosad.rules;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeOtherRule extends AttributeRule{
    private String statement;

    public AttributeOtherRule(String tab, String atr, String stat) {
        super(tab, atr);
        statement = stat;
    }

    public String getStatement() {
        return statement;
    }
}
