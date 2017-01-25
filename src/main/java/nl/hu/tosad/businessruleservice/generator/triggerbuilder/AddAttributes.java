package nl.hu.tosad.businessruleservice.generator.triggerbuilder;

import nl.hu.tosad.businessruleservice.model.rules.ComparisonOperator;

public interface AddAttributes {
    Build addBetween(String min, String max);
    AddValue addComparisonOperator(ComparisonOperator comparisonOperator);
}
