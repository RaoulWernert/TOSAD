package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

import java.util.Objects;

/**
 * Created by Raoul on 11/17/2016.
 */
public abstract class AttributeRule extends BusinessRule{
    private String attribute;

    public AttributeRule(BusinessRuleData data) {
        super(data);
        try {
            attribute = Objects.requireNonNull(data.getTargetcolumn(), "AttributeRule Attribute cannot be null.");
        } catch(NullPointerException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    public String getAttribute() {
        return attribute;
    }
}