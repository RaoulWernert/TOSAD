package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

/**
 * Created by Raoul on 11/17/2016.
 */
public abstract class BusinessRule {
    private String name;
    private String table;
    private Implementation implementation;

    public BusinessRule(BusinessRuleData data) {
        name = data.name;
        table = data.table;
        implementation = Implementation.valueOf(data.implementation);
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
