package nl.hu.tosad.generator;

import nl.hu.tosad.model.rules.*;

public class OracleSQLGenerator implements ISQLGenerator {
    private final String CONSTRAINT = "ALTER TABLE %s ADD CONSTRAINT %s CHECK (%s)";

    @Override
    public String generateSQL(AttributeRangeRule rule) {
        String constraint = String.format("%s BETWEEN %s AND %s", rule.getAttribute(), rule.getMin(), rule.getMax());
        return String.format(CONSTRAINT, rule.getTable(), rule.getName(), constraint);
    }

    @Override
    public String generateSQL(AttributeCompareRule rule) {
        String constraint = String.format("%s %s %s", rule.getAttribute(), rule.getComparisonOperator().getCode(), rule.getValue());
        return String.format(CONSTRAINT, rule.getTable(), rule.getName(), constraint);
    }

    @Override
    public String generateSQL(AttributeListRule rule) {
        String constraint = "STATUS IN (";
        for (String str : rule.getValues()) {
            constraint += "'" + str + "',";
        }
        constraint = constraint.substring(0, constraint.length() - 1) + ")";

        return String.format(CONSTRAINT, rule.getTable(), rule.getName(), constraint);
    }

    @Override
    public String generateSQL(AttributeOtherRule rule) {
        return String.format(CONSTRAINT, rule.getTable(), rule.getName(), rule.getStatement());
    }
}
