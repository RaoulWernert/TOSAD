package nl.hu.tosad.businessruleservice.generator.triggerbuilder;

import nl.hu.tosad.businessruleservice.model.RuleType;

public interface OnRuleType {
    AddEvent onRuleType(RuleType ruleType);
}
