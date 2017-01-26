package nl.hu.tosad.businessruleservice.generator.constraintbuilder;

import nl.hu.tosad.businessruleservice.model.rules.ComparisonOperator;

public interface AddOperator {
    Build addOperator(ComparisonOperator comparisonOperator);
}
