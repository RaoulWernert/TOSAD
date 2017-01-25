package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.model.BusinessRuleData;
import nl.hu.tosad.businessruleservice.model.RuleType;
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
    private RuleType ruleType;
    private boolean implemented;

    public BusinessRule(BusinessRuleData data) {
        table = data.getTargettable();
        implementation = Implementation.valueOf(data.getImplementation());
        target = data.getTarget();
        id = data.getId();
        ruleType = data.getRuleType();
        implemented = data.isImplemented();

        String impl = implementation == Implementation.CONSTRAINT ? "CNS" : "TRG";
        name = String.format("BRG_%s_%s_%s_%s", target.getName(), impl, data.getRuleType().getCode(), id);
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

    public TargetDatabase getTarget() {
        return target;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public boolean isImplemented() {
        return implemented;
    }

    public abstract String accept(IGenerator generator);
}
