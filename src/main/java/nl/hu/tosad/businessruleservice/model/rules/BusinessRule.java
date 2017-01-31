package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.BusinessRuleData;
import nl.hu.tosad.businessruleservice.model.RuleType;
import nl.hu.tosad.businessruleservice.model.TargetDatabase;

import java.util.Objects;

/**
 * Created by Raoul on 11/17/2016.
 */
public abstract class BusinessRule {
    private int id;
    private String name;
    private RuleType ruleType;
    private TargetDatabase target;
    private String table;
    private Implementation implementation;
    private boolean implemented;
    private String errormsg;

    public BusinessRule(BusinessRuleData data) {
        try {
            id = Objects.requireNonNull(data.getId(), "BusinessRule ID cannot be null.");
            ruleType = Objects.requireNonNull(data.getRuleType(), "BusinessRule Type cannot be null.");
            target = Objects.requireNonNull(data.getTarget(), "BusinessRule Target cannot be null.");
            table = Objects.requireNonNull(data.getTargettable(), "BusinessRule Table cannot be null.");
            implemented = data.isImplemented();
            errormsg = data.getErrormsg();
            implementation = Implementation.valueOf(data.getImplementation());

            String impl = implementation == Implementation.CONSTRAINT ? "CNS" : "TRG";
            name = String.format("BRG_%s_%s_%s_%s", target.getName(), impl, data.getRuleType().getCode(), id);
        } catch(NullPointerException | IllegalArgumentException e) {
            throw new BusinessRuleServiceException(e);
        }
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

    public String getErrormsg() {
        return errormsg;
    }

    public abstract String accept(IGenerator generator);
}
