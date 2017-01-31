package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

import java.util.Objects;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeOtherRule extends AttributeRule{
    private String statement;

    public AttributeOtherRule(BusinessRuleData data) {
        super(data);
        try {
            statement = Objects.requireNonNull(data.getStatement(), "AttributeOtherRule Statement cannot be null.");
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
