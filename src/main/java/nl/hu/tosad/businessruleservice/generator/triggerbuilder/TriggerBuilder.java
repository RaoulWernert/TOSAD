package nl.hu.tosad.businessruleservice.generator.triggerbuilder;

import nl.hu.tosad.businessruleservice.model.RuleType;
import nl.hu.tosad.businessruleservice.model.rules.ComparisonOperator;
import nl.hu.tosad.businessruleservice.model.rules.LogicalOperator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TriggerBuilder {
    private final String R_NAME = "#name#";
    private final String R_EVENTS = "#events#";
    private final String R_COLUMNS = "#columns#";
    private final String R_TABLE= "#table#";
    private final String R_STATEMENT = "#statement#";
    private final String R_ERROR = "#error#";
    private final String BETWEEN = "%s BETWEEN '%s' AND '%s'";
    private final String COMPARISON = "'%s' %s '%s'";
    private final String ANY_ALL_BLOCK = "DECLARE v_test varchar2(6);\n" +
            "BEGIN SELECT 'passed' INTO v_test FROM dual WHERE %s %s %s (%s); l_passed := TRUE;\n" +
            "EXCEPTION WHEN NO_DATA_FOUND THEN l_passed := FALSE; END";

    private String trigger =
            "CREATE OR REPLACE TRIGGER #name#\n" +
            "    BEFORE #events#\n" +
            "    #columns# ON #table#\n" +
            "    FOR EACH ROW \n" +
            "DECLARE\n" +
            "    l_passed boolean := true;\n" +
            "BEGIN\n" +
            "    #statement#\n" +
            "    IF NOT l_passed THEN\n" +
            "        raise_application_error(-20800, '#error#');\n" +
            "    END IF;\n" +
            "END #name#;";

    private String compoundtrigger =
            "CREATE OR REPLACE TRIGGER %s\n" +
            "    FOR %s %s\n" +
            "      COMPOUND TRIGGER\n" +
            "      \n" +
            "  TYPE t_rcrd IS RECORD (\n" +
            "    l_rowid ROWID,\n" +
            "    l_row %s#PERC#ROWTYPE);\n" +
            "\n" +
            "  TYPE t_change_tab IS TABLE OF t_rcrd;\n" +
            "  g_change_tab t_change_tab := t_change_tab();\n" +
            "  \n" +
            "  TYPE t_old_tab IS TABLE OF t_rcrd;\n" +
            "  g_old_tab t_old_tab := t_old_tab();\n" +
            "\n" +
            "  AFTER EACH ROW IS\n" +
            "  BEGIN\n" +
            "    g_change_tab.extend;\n" +
            "    g_change_tab(g_change_tab.last).l_rowid := :NEW.ROWID;\n" +
            "%s\n" +
            "    IF UPDATING THEN\n" +
            "      g_old_tab.extend;\n" +
            "      g_old_tab(g_old_tab.last).l_rowid := :OLD.ROWID;\n" +
            "%s\n" +
            "    END IF;\n" +
            "  END AFTER EACH ROW;\n" +
            "  \n" +
            "  AFTER STATEMENT IS\n" +
            "  BEGIN\n" +
            "    DECLARE\n" +
            "      v_old %s#PERC#ROWTYPE;\n" +
            "      v_new %s#PERC#ROWTYPE;\n" +
            "      l_passed boolean := TRUE;\n" +
            "    BEGIN    \n" +
            "      FOR i IN g_change_tab.first .. g_change_tab.last LOOP         \n" +
            "        v_new := g_change_tab(i).l_row;\n" +
            "        IF UPDATING THEN\n" +
            "          v_old := g_old_tab(i).l_row;\n" +
            "        ELSE\n" +
            "          v_old := NULL;\n" +
            "        END IF;\n" +
            "        \n" +
            "        %s;" +
            "        \n" +
            "        EXIT WHEN NOT l_passed;\n" +
            "      END LOOP;\n" +
            "      IF NOT l_passed THEN\n" +
            "        raise_application_error(-20800, '%s');\n" +
            "      END IF;\n" +
            "    END;\n" +
            "  END AFTER STATEMENT;\n" +
            "END %s;";

    String condition = "l_passed := ";
    String column1;
    String errormsg = "ERROR";
    String statement =  "l_passed := ";


    public TriggerBuilder(String name) {
        trigger = trigger.replace(R_NAME, name);
    }

    public TriggerBuilder setTable(String table){
        trigger = trigger.replace(R_TABLE, table);
        return this;
    }

    public TriggerBuilder setColumns(String... columns){
        String value = "OF ";
        for (int i = 0; i < columns.length; i++) {
            value += columns[i] + (i + 1 < columns.length ? "," : "");
        }
        trigger = trigger.replace(R_COLUMNS, value);
        return this;
    }

    public TriggerBuilder setStatement(String statement){
        trigger = trigger.replace(R_STATEMENT, statement);
        return this;
    }

    public TriggerBuilder setError(String error){
        trigger = trigger.replace(R_ERROR, error);
        return this;
    }

    public TriggerBuilder addBetween(String value, String min, String max) {
        statement += String.format(BETWEEN, ":NEW." + value, min, max);
        return this;
    }

    public TriggerBuilder addComparison(String pre, ComparisonOperator operator, String post) {
        statement += String.format(COMPARISON, ":NEW." + pre, operator.getCode(), ":NEW." + post);
        return this;
    }

    public TriggerBuilder setEvents(RuleType type){
        String events = "";
        if(type.isInsert()) {
            events += "INSERT";
        }
        if(type.isUpdate()) {
            events += (!events.isEmpty() ? " OR " : "") + "UPDATE";
        }
        if(type.isDelete()) {
            events += (!events.isEmpty() ? " OR " : "") + "DELETE";
        }
        trigger = trigger.replace(R_EVENTS, events);
        return this;
    }

    public TriggerBuilder addComparisons(String pre, LogicalOperator lOperator, ComparisonOperator cOperator, String post) {
        if(lOperator == LogicalOperator.Any || lOperator == LogicalOperator.All) {
            statement = String.format(ANY_ALL_BLOCK, pre, cOperator.getCode(), lOperator.getCode(), post);
        } else {
            statement += String.format(pre, lOperator.getCode(), post);
        }
        return this;
    }

    public TriggerBuilder addTableComp(String table, String key, String column, ComparisonOperator opr, boolean isPrimary){
        if(opr == ComparisonOperator.LessOrEqual || opr == ComparisonOperator.Less){
            column = "MIN("+column+")";
        }else if(opr == ComparisonOperator.GreaterOrEqual || opr == ComparisonOperator.Greater){
            column = "MAX("+column+")";
        }
        if(!isPrimary){
            opr = getOppositOpr(opr);
        }
        statement = " cursor v_cursor is\n" +
                    " select "+column+"\n" +
                    " from "+table+"\n" +
                    " where "+key+" = :NEW."+key+";\n" +
                    " v_value "+table+"."+key+"%type;\n" +
                    "begin\n" +
                    " open v_cursor;\n" +
                    " fetch v_cursor into v_value;\n" +
                    " close v_cursor;\n" +
                    " l_passed := :NEW."+column+" "+opr.getCode()+" v_value;";
        return this;

    }
    private ComparisonOperator getOppositOpr(ComparisonOperator opr){
        switch (opr){
            case Greater:
                return ComparisonOperator.Less;
            case Less:
                return ComparisonOperator.Greater;
            case GreaterOrEqual:
                return ComparisonOperator.LessOrEqual;
            case LessOrEqual:
                return ComparisonOperator.GreaterOrEqual;
        }
        return opr;
    }

    public TriggerBuilder addCodeBlock(String code) {
        List<String> strings = new ArrayList<>(Arrays.asList(code.trim().split("\\r\\n")));

        if(strings.size() > 0) {
            String first = strings.get(0);
            strings.remove(first);
            strings = strings.stream().map(str -> "        " + str).collect(Collectors.toCollection(ArrayList::new));
            strings.add(0, first);
            code = String.join("\r\n", strings);
            if(code.charAt(code.length() - 1) == ';') {
                code = code.substring(0, code.length() - 1);
            }
        }

        this.condition = code;
        return this;
    }

    public TriggerBuilder addAllColumns(List<String> columns) {
        final String format = "g_%s_tab(g_%s_tab.last).l_row.%s := %s.%s;";

        List<String> change = new ArrayList<>(columns);
        List<String> old = new ArrayList<>(columns);

        change = change.stream()
                .map(str -> "    " + String.format(format, "change", "change", str, ":NEW", str))
                .collect(Collectors.toCollection(ArrayList::new));
        old = old.stream()
                .map(str -> "      " + String.format(format, "old", "old", str, ":OLD", str))
                .collect(Collectors.toCollection(ArrayList::new));

        compoundtrigger = String.format(compoundtrigger, String.join("\r\n", change), String.join("\r\n", old), "%s", "%s");
        trigger = compoundtrigger;
        return this;
    }

    public String build() {
        for (String tag : Arrays.asList(R_NAME, R_EVENTS, R_COLUMNS, R_TABLE, R_ERROR)) {
            trigger = trigger.replace(tag, "");
        }
        return trigger.replace(R_STATEMENT, statement + ";");
        //return String.format(trigger, condition, errormsg).replace("#PERC#", "%");
    }
}
