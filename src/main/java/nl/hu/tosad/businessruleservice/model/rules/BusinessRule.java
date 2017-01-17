package nl.hu.tosad.businessruleservice.model.rules;

/**
 * Created by Raoul on 11/17/2016.
 */
public abstract class BusinessRule {
    private String name;
    private String table;
    private Implementation implementation;

    BusinessRule(String name, String table, Implementation implementation) {
        this.name = name;
        this.table = table;
        this.implementation = implementation;
    }

    public String getTable(){
        return table;
    }

    public String getName() {
        return name;
    }

    public Implementation getImplementation() {
        return implementation;
    }

    public abstract String accept(IGenerator generator);
}
