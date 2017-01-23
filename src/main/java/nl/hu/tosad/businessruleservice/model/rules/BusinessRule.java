package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.model.BusinessRuleData;
import nl.hu.tosad.businessruleservice.model.TargetDatabase;

/**
 * Created by Raoul on 11/17/2016.
 */
public abstract class BusinessRule {
    private int id;
    private String name;
    private String table;
    private Implementation implementation;
    private TargetDatabase target;

    public BusinessRule(BusinessRuleData data) {
        table = data.table;
        implementation = Implementation.valueOf(data.implementation);
        target = data.target;
        id = data.id;

        String impl = implementation == Implementation.CONSTRAINT ? "CNS" : "TRG";
        name = String.format("BRG_%s_%s_%s_%s", target.getName(), impl, data.ruletype_code, id);
    }

    public int getId() {
        return id;
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

    public TargetDatabase getTarget() {
        return target;
    }
}
