package nl.hu.tosad.businessruleservice.generator.constraintbuilder;

import nl.hu.tosad.businessruleservice.model.rules.ComparisonOperator;
import nl.hu.tosad.businessruleservice.model.rules.LogicalOperator;

import java.util.List;

/**
 * Created by Raoul on 1/22/2017.
 */
public class ConstraintBuilder implements OnTable, OnColumnOrAddStatement, AddAttributes, AddValue, AddValues, AddOperator, Build {
    public static OnTable newConstraint(String name) {
        return new ConstraintBuilder(name);
    }

    private String query;
    private String constraint;

    private ConstraintBuilder(String name) {
        this.query = "ALTER TABLE %s ADD CONSTRAINT " + name + " CHECK (%s)";
    }

    @Override
    public OnColumnOrAddStatement onTable(String table) {
        this.query = String.format(query, table, "%s");
        return this;
    }

    @Override
    public AddAttributes onColumn(String column) {
        this.constraint = column;
        return this;
    }

    @Override
    public AddOperator onColumns(String column1, String column2) {
        this.constraint = column1 + " %s " + column2;
        return this;
    }

    @Override
    public Build addBetween(String min, String max) {
        this.constraint = String.format(constraint + " BETWEEN '%s' AND '%s'", min, max);
        return this;
    }

    @Override
    public AddValue addComparisonOperator(ComparisonOperator comparisonOperator) {
        this.constraint = String.format(constraint + " %s '%s'", comparisonOperator.getCode(), "%s");
        return this;
    }

    @Override
    public Build addValue(String value) {
        this.constraint = String.format(constraint, value);
        return this;
    }

    @Override
    public AddValues addOperators(LogicalOperator logicalOperator, ComparisonOperator comparisonOperator) {
        if(logicalOperator == LogicalOperator.Any || logicalOperator == LogicalOperator.All) {
            this.constraint = String.format(constraint + " %s %s (%s)", comparisonOperator.getCode(), logicalOperator.getCode(), "%s");
        } else {
            this.constraint = String.format(constraint + " %s (%s)", logicalOperator.getCode(), "%s");
        }

        return this;
    }

    @Override
    public Build addValues(List<String> values) {
        this.constraint = String.format(constraint, getValuesFromList(values));
        return this;
    }

    @Override
    public Build addStatement(String statement) {
        this.constraint = statement;
        return this;
    }

    @Override
    public Build addOperator(ComparisonOperator comparisonOperator) {
        this.constraint = String.format(constraint, comparisonOperator.getCode());
        return this;
    }

    @Override
    public String build() {
        System.out.println(String.format(query, constraint));
        return String.format(query, constraint);
    }

    String getValuesFromList(List<String> list){
        String values = "";
        for (String str : list) {
            if(str.length() > 0) {
                values += "'" + str + "',";
            }
        }
        return values.substring(0, values.length() - 1);
    }
}
