package nl.hu.tosad.businessruleservice.generator;

import nl.hu.tosad.businessruleservice.model.rules.*;

public class OracleGenerator implements IGenerator {
    private final String CONSTRAINT = "ALTER TABLE %s ADD CONSTRAINT %s CHECK (%s)";

    @Override
    public String generateDDL(AttributeRangeRule rule) {
        String constraint = String.format("%s BETWEEN %s AND %s", rule.getAttribute(), rule.getMin(), rule.getMax());
        return String.format(CONSTRAINT, rule.getTable(), rule.getName(), constraint);
    }

    @Override
    public String generateDDL(AttributeCompareRule rule) {
        String constraint = String.format("%s %s %s", rule.getAttribute(), rule.getComparisonOperator().getCode(), rule.getValue());
        return String.format(CONSTRAINT, rule.getTable(), rule.getName(), constraint);
    }

    @Override
    public String generateDDL(AttributeListRule rule) {
        String constraint = "STATUS IN (";
        for (String str : rule.getValues()) {
            constraint += "'" + str + "',";
        }
        constraint = constraint.substring(0, constraint.length() - 1) + ")";

        return String.format(CONSTRAINT, rule.getTable(), rule.getName(), constraint);
    }

    @Override
    public String generateDDL(AttributeOtherRule rule) {
        return String.format(CONSTRAINT, rule.getTable(), rule.getName(), rule.getStatement());
    }
}
