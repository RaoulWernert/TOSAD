package nl.hu.tosad.businessruleservice.generator.triggerbuilder;

import nl.hu.tosad.businessruleservice.model.RuleType;
import nl.hu.tosad.businessruleservice.model.rules.ComparisonOperator;
import nl.hu.tosad.businessruleservice.model.rules.LogicalOperator;

import java.util.List;

public class TriggerBuilder implements OnRuleType, AddEvent, OnColumn, AddAttributes, AddValue, AddValues, Build {
    public static OnRuleType newTrigger(String name) {
        return new TriggerBuilder(name);
    }

    private String trigger =
            "CREATE OR REPLACE TRIGGER %s\n" +
            "    BEFORE %s %s\n" +
            "    FOR EACH ROW \n" +
            "DECLARE\n" +
            "    l_passed boolean := true;\n" +
            "BEGIN\n" +
            "    l_passed := %s;\n" +
            "    IF NOT l_passed THEN\n" +
            "        raise_application_error(-20800, '%s');\n" +
            "    END IF;\n" +
            "END %s;";
    private String condition = "";

    private TriggerBuilder(String name) {
        trigger = String.format(trigger, name, "%s", "%s", "%s", "%s", name);
    }

    @Override
    public AddEvent onRuleType(RuleType ruleType) {
        String event = "";

        if(ruleType.isInsert()) {
            event += "INSERT";
        }
        if(ruleType.isUpdate()) {
            event += (!event.isEmpty() ? " OR " : "") + "UPDATE";
        }
        if(ruleType.isDelete()) {
            event += (!event.isEmpty() ? " OR " : "") + "DELETE";
        }

        trigger = String.format(trigger, event, "%s", "%s", "%s");
        return this;
    }

    @Override
    public OnColumn addEvent(String table, String... columns) {
        String event;

        if(columns == null || columns.length == 0) {
            event = "ON " + table;
        } else {
            String cols = "";
            for(int i = 0; i < columns.length; i++) {
                cols += columns[i] + (i + 1 < columns.length ? "," : "");
            }
            event = String.format("OF %s ON %s", cols, table);
        }

        trigger = String.format(trigger, event, "%s", "%s");
        return this;
    }

    @Override
    public AddAttributes onColumn(String column) {
        this.condition = ":NEW." + column;
        return this;
    }

    @Override
    public Build addBetween(String min, String max) {
        this.condition = String.format(condition + " BETWEEN '%s' AND '%s'", min, max);
        return this;
    }

    @Override
    public AddValue addComparisonOperator(ComparisonOperator comparisonOperator) {
        this.condition = String.format(condition + " %s '%s'", comparisonOperator.getCode(), "%s");
        return this;
    }

    @Override
    public Build addValue(String value) {
        this.condition = String.format(condition, value);
        return this;
    }

    @Override
    public AddValues addOperators(LogicalOperator logicalOperator, ComparisonOperator comparisonOperator) {
        if(logicalOperator == LogicalOperator.Any || logicalOperator == LogicalOperator.All) {
            this.condition = String.format(condition + " %s %s (%s)", comparisonOperator.getCode(), logicalOperator.getCode(), "%s");
        } else {
            this.condition = String.format(condition + " %s (%s)", logicalOperator.getCode(), "%s");
        }
        return this;
    }

    @Override
    public Build addValues(List<String> values) {
        this.condition = String.format(condition, getValuesFromList(values));
        return this;
    }


    @Override
    public String build() {
        return String.format(trigger, condition, "ERRORMSG PLACEHOLDER");
    }

    String getValuesFromList(List<String> list){
        String values = "";
        for (String str : list) {
            values += "'" + str + "',";
        }
        return values.substring(0, values.length() - 1);
    }
}
