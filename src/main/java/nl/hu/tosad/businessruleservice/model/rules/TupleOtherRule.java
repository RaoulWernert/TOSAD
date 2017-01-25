package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

/**
 * Created by Raoul on 1/26/2017.
 */
public class TupleOtherRule extends TupleRule {
    private String statement;

    public TupleOtherRule(BusinessRuleData data) {
        super(data);
        statement = data.getStatement();
    }

    @Override
    public String accept(IGenerator generator) {
        return generator.generateDDL(this);
    }
}
