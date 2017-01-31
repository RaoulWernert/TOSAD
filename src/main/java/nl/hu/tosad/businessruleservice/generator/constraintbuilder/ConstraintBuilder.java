package nl.hu.tosad.businessruleservice.generator.constraintbuilder;

import nl.hu.tosad.businessruleservice.model.rules.ComparisonOperator;
import nl.hu.tosad.businessruleservice.model.rules.LogicalOperator;

import java.util.List;

/**
 * Created by Raoul on 1/22/2017.
 */
public class ConstraintBuilder implements OnTable, OnColumnOrAddStatement, AddAttributes, AddValue, AddValues, AddOperator, Build {
    /**
     * Starts a new contraint
     * @param name The name of the new constraint
     * @return The ability to call onTable
     */
    public static OnTable newConstraint(String name) {
        return new ConstraintBuilder(name);
    }

    String query;
    String constraint;

    /**
     * Adds the name the constraint
     * @param name The name of the constraint
     */
    ConstraintBuilder(String name) {
        this.query = "ALTER TABLE %s ADD CONSTRAINT " + name + " CHECK (%s)";
    }

    /**
     * Adds the name of the table to the constraint
     * @param table The name of the table
     * @return The ability to call onColumn, onColumns and addStatement
     */
    @Override
    public OnColumnOrAddStatement onTable(String table) {
        this.query = String.format(query, table, "%s");
        return this;
    }

    /**
     * Adds the column to the constraint
     * @param column The name of the column
     * @return The ability to call addBetween, addComparisonOperator, addOperators and addStatement
     */
    @Override
    public AddAttributes onColumn(String column) {
        this.constraint = column;
        return this;
    }

    /**
     * Adds two columns to the constraint
     * @param column1 The name of the first column
     * @param column2 The name of the second column
     * @return The ability to call addOperator
     */
    @Override
    public AddOperator onColumns(String column1, String column2) {
        this.constraint = column1 + " %s " + column2;
        return this;
    }

    /**
     * Adds the between condition to the constraint
     * @param min The lowest value of the between condition
     * @param max The highest value of the between condtion
     * @return The ability to call build
     */
    @Override
    public Build addBetween(String min, String max) {
        this.constraint = String.format(constraint + " BETWEEN '%s' AND '%s'", min, max);
        return this;
    }

    /**
     * Adds a comparison operator to the constraint
     * @param comparisonOperator The comparion operator to add to the constraint
     * @return The ability to call addValue
     */
    @Override
    public AddValue addComparisonOperator(ComparisonOperator comparisonOperator) {
        this.constraint = String.format(constraint + " %s '%s'", comparisonOperator.getCode(), "%s");
        return this;
    }

    /**
     * Adds a value to the constraint
     * @param value The value to add to the constraint
     * @return The ability to call build
     */
    @Override
    public Build addValue(String value) {
        this.constraint = String.format(constraint, value);
        return this;
    }

    /**
     * Adds a logical operator or a comparison and a logical operator to the constraint
     * @param logicalOperator The logical operator to add to the constraint
     * @param comparisonOperator The comparison operator to add to the constraint
     * @return The ability to call addValues
     */
    @Override
    public AddValues addOperators(LogicalOperator logicalOperator, ComparisonOperator comparisonOperator) {
        if(logicalOperator == LogicalOperator.Any || logicalOperator == LogicalOperator.All) {
            this.constraint = String.format(constraint + " %s %s (%s)", comparisonOperator.getCode(), logicalOperator.getCode(), "%s");
        } else {
            this.constraint = String.format(constraint + " %s (%s)", logicalOperator.getCode(), "%s");
        }

        return this;
    }

    /**
     * Adds multiple values to the constraint
     * @param values A list of values to add to the constraint
     * @return The ability to call build
     */
    @Override
    public Build addValues(List<String> values) {
        this.constraint = String.format(constraint, getValuesFromList(values));
        return this;
    }

    /**
     * Adds a statement to the constraint
     * @param statement The code written by the user
     * @return The ability to call build
     */
    @Override
    public Build addStatement(String statement) {
        this.constraint = statement;
        return this;
    }

    /**
     * Adds a comparison operator to the constraint
     * @param comparisonOperator The comparison operator to add to the constriant
     * @return The ability to call build
     */
    @Override
    public Build addOperator(ComparisonOperator comparisonOperator) {
        this.constraint = String.format(constraint, comparisonOperator.getCode());
        return this;
    }

    /**
     * Builds the given values into a string
     * @return The constraint string
     */
    @Override
    public String build() {
        return String.format(query, constraint);
    }

    private String getValuesFromList(List<String> list){
        String values = "";
        for (String str : list) {
            if(str.length() > 0) {
                values += "'" + str + "',";
            }
        }
        return values.substring(0, values.length() - 1);
    }
}
