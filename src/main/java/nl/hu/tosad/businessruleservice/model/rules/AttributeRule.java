package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

/**
 * Created by Raoul on 11/17/2016.
 */
public abstract class AttributeRule extends BusinessRule{
    private String attribute;

    public AttributeRule(BusinessRuleData data) {
        super(data);
        attribute = data.getTargetcolumn();
    }

    public String getAttribute() {
        return attribute;
    }
}