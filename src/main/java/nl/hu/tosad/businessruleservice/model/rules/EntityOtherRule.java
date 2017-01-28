package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

/**
 * Created by Raoul on 1/26/2017.
 */
public class EntityOtherRule extends BusinessRule {
    public String column;
    public String column2;
    private String statement;

    public EntityOtherRule(BusinessRuleData data) {
        super(data);
        column = data.getTargetcolumn();
        column2 = data.getTargetcolumn2();
        statement = data.getStatement();
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
}
