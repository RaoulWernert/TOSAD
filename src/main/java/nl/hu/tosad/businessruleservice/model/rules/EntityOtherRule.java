package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

import java.util.Objects;

/**
 * Created by Raoul on 1/26/2017.
 */
public class EntityOtherRule extends BusinessRule {
    public String column;
    public String column2;
    private String gvariables;
    private String beforestatement;
    private String statement;

    public EntityOtherRule(BusinessRuleData data) {
        super(data);
        try {
            column = Objects.requireNonNull(data.getTargetcolumn(), "EntityOtherRule Column cannot be null.");
            column2 = Objects.requireNonNull(data.getTargetcolumn2(), "EntityOtherRule Column2 cannot be null.");
            gvariables = Objects.requireNonNull(data.getGvariables(), "EntityOtherRule Gvariables cannot be null.");
            beforestatement = Objects.requireNonNull(data.getBeforestatement(), "EntityOtherRule Beforestatement cannot be null.");
            statement = Objects.requireNonNull(data.getStatement(), "EntityOtherRule Statement cannot be null.");
        } catch(NullPointerException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    @Override
    public String accept(IGenerator generator) {
        return generator.generateDDL(this);
    }

    public String getColumn() {
        return column;
    }

    public String getColumn2() {
        return column2;
    }

    public String getStatement() {
        return statement;
    }

    public String getGvariables() {
        return gvariables;
    }

    public String getBeforestatement() {
        return beforestatement;
    }
}
