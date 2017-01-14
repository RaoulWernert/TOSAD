package nl.hu.tosad.rules;

/**
 * Created by Raoul on 11/17/2016.
 */
public abstract class BusinessRule {
    private String name;
    private String table;

    BusinessRule(String name, String table) {
        this.name = name;
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public String getTable(){
        return table;
    }
}
