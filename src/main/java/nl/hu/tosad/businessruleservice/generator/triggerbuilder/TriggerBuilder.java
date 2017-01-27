package nl.hu.tosad.businessruleservice.generator.triggerbuilder;

import nl.hu.tosad.businessruleservice.model.RuleType;
import nl.hu.tosad.businessruleservice.model.rules.ComparisonOperator;
import nl.hu.tosad.businessruleservice.model.rules.LogicalOperator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TriggerBuilder implements OnRuleType, AddEvent, AddColumnOrStatement, AddAttributes, AddValueOrColumn, AddValues, Build {
    public static OnRuleType newTrigger(String name) {
        return new TriggerBuilder(name);
    }

    String trigger =
            "CREATE OR REPLACE TRIGGER %s\n" +
            "    BEFORE %s %s\n" +
            "    FOR EACH ROW \n" +
            "DECLARE\n" +
            "    l_passed boolean := true;\n" +
            "BEGIN\n" +
            "    %s;\n" +
            "    IF NOT l_passed THEN\n" +
            "        raise_application_error(-20800, '%s');\n" +
            "    END IF;\n" +
            "END %s;";
    String condition = "l_passed := ";
    String column1;

    TriggerBuilder(String name) {
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
    public AddColumnOrStatement addEvent(String table, String... columns) {
        String event;

        if(columns == null || columns.length == 0) {
            event = "ON " + table;
        } else {
            List<String> columnList = new ArrayList<>(Arrays.asList(columns))
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(str -> !str.trim().isEmpty())
                    .collect(Collectors.toCollection(ArrayList::new));

            String cols = "";
            for(int i = 0; i < columnList.size(); i++) {
                cols += columnList.get(i) + (i + 1 < columnList.size() ? "," : "");
            }
            event = String.format("OF %s ON %s", cols, table);
        }

        trigger = String.format(trigger, event, "%s", "%s");
        return this;
    }

    @Override
    public AddAttributes addColumn(String column) {
        this.column1 = ":NEW." + column;
        this.condition += column1;
        return this;
    }

    @Override
    public Build addStatement(String statement) {
        this.condition += statement;
        return this;
    }

    @Override
    public Build addCodeBlock(String code) {
        List<String> strings = new ArrayList<>(Arrays.asList(code.trim().split("\\r\\n")));

        if(strings.size() > 0) {
            String first = strings.get(0);
            strings.remove(first);
            strings = strings.stream().map(str -> "    " + str).collect(Collectors.toCollection(ArrayList::new));
            strings.add(0, first);
            code = String.join("\r\n", strings);
            if(code.charAt(code.length() - 1) == ';') {
                code = code.substring(0, code.length() - 1);
            }
        }

        this.condition = code;
        return this;
    }

    @Override
    public Build addBetween(String min, String max) {
        this.condition = String.format(condition + " BETWEEN '%s' AND '%s'", min, max);
        return this;
    }

    @Override
    public AddValueOrColumn addComparisonOperator(ComparisonOperator comparisonOperator) {
        this.condition = String.format(condition + " %s %s", comparisonOperator.getCode(), "%s");
        return this;
    }

    @Override
    public Build addValue(String value) {
        this.condition = String.format(condition, "'" + value + "'");
        return this;
    }

    @Override
    public Build addSecondColumn(String column) {
        this.condition = String.format(condition, ":NEW." + column);
        return this;
    }

    @Override
    public AddValues addOperators(LogicalOperator logicalOperator, ComparisonOperator comparisonOperator) {
        if(logicalOperator == LogicalOperator.Any || logicalOperator == LogicalOperator.All) {
            this.condition =
                    "    DECLARE\n" +
                    "          v_test varchar2(4000);\n" +
                    "        BEGIN\n" +
                    "          SELECT 'passed' INTO v_test FROM dual WHERE %s %s %s (%s);\n" +
                    "          l_passed := TRUE;\n" +
                    "        EXCEPTION\n" +
                    "          WHEN NO_DATA_FOUND THEN\n" +
                    "          l_passed := FALSE;\n" +
                    "        END";
            this.condition = String.format(condition, column1, comparisonOperator.getCode(), logicalOperator.getCode(), "%s");
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
            if(str.length() > 0) {
                values += "'" + str + "',";
            }
        }
        return values.substring(0, values.length() - 1);
    }
}
