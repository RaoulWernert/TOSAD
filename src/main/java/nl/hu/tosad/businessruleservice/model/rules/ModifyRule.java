package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

/**
 * Created by Raoul on 1/26/2017.
 */
public class ModifyRule extends BusinessRule {
    public ModifyRule(BusinessRuleData data) {
        super(data);
    }

    @Override
    public String accept(IGenerator generator) {
        return generator.generateDDL(this);
    }
}
