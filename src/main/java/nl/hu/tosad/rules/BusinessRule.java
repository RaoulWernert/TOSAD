package nl.hu.tosad.rules;

/**
 * Created by Raoul on 11/17/2016.
 */
public abstract class BusinessRule {
    protected String table;

    public BusinessRule(String tab){
        table = tab;
    }

    public String getTable(){
        return table;
    }
}
