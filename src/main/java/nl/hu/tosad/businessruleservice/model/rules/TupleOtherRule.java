package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

import java.util.Objects;

/**
 * Created by Raoul on 1/26/2017.
 */
public class TupleOtherRule extends TupleRule {
    private String statement;

    public TupleOtherRule(BusinessRuleData data) {
        super(data);
        try {
            statement = Objects.requireNonNull(data.getStatement(), "TupleOtherRule Statement cannot be null.");
        } catch(NullPointerException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    public String getStatement() {
        return statement;
    }

    @Override
    public String accept(IGenerator generator) {
        return generator.generateDDL(this);
    }
}
