package nl.hu.tosad.businessruleservice.generator.constraintbuilder;

import nl.hu.tosad.businessruleservice.model.rules.ComparisonOperator;
import nl.hu.tosad.businessruleservice.model.rules.LogicalOperator;

public interface AddAttributes {
        Build addBetween(String min, String max);
        AddValue addComparisonOperator(ComparisonOperator comparisonOperator);
        AddValues addOperators(LogicalOperator logicalOperator, ComparisonOperator comparisonOperator);
        Build addStatement(String statement);
}
