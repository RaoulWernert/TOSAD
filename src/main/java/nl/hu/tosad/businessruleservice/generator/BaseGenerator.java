package nl.hu.tosad.businessruleservice.generator;

import nl.hu.tosad.businessruleservice.generator.constraintbuilder.ConstraintBuilder;
import nl.hu.tosad.businessruleservice.model.rules.*;

/**
 * Created by Raoul on 1/21/2017.
 */
public class BaseGenerator implements IGenerator {
    @Override
    public String generateDDL(AttributeRangeRule rule) {
        switch(rule.getImplementation()) {
            case CONSTRAINT:
                return new ConstraintBuilder().newConstraint(rule.getName())
                        .onTable(rule.getTable())
                        .onColumn(rule.getAttribute())
                        .addBetween(rule.getMin(), rule.getMax())
                        .build();
            case TRIGGER:
                return "";
            default:
                return null;
        }
    }

    @Override
    public String generateDDL(AttributeCompareRule rule) {
        switch(rule.getImplementation()) {
            case CONSTRAINT:
                return new ConstraintBuilder().newConstraint(rule.getName())
                        .onTable(rule.getTable())
                        .onColumn(rule.getAttribute())
                        .addComparisonOperator(rule.getComparisonOperator())
                        .addValue(rule.getValue())
                        .build();
            case TRIGGER:
                return "";
            default:
                return null;
        }
    }

    @Override
    public String generateDDL(AttributeListRule rule) {
        switch(rule.getImplementation()) {
            case CONSTRAINT:
                return new ConstraintBuilder().newConstraint(rule.getName())
                        .onTable(rule.getTable())
                        .onColumn(rule.getAttribute())
                        .addOperators(rule.getLogicalOperator(), rule.getComparisonOperator())
                        .addValues(rule.getValues())
                        .build();
            case TRIGGER:
                return "";
            default:
                return null;
        }
    }

    @Override
    public String generateDDL(AttributeOtherRule rule) {
        switch(rule.getImplementation()) {
            case CONSTRAINT:
                return new ConstraintBuilder().newConstraint(rule.getName())
                        .onTable(rule.getTable())
                        .onColumn(rule.getAttribute())
                        .addStatement(rule.getStatement())
                        .build();
            case TRIGGER:
                return "";
            default:
                return null;
        }
    }
}
