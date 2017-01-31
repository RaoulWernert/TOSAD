package nl.hu.tosad.businessruleservice.generator.triggerbuilder;

import nl.hu.tosad.businessruleservice.model.RuleType;
import nl.hu.tosad.businessruleservice.model.rules.ComparisonOperator;
import nl.hu.tosad.businessruleservice.model.rules.LogicalOperator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TriggerBuilder {
    private final String R_NAME = "#name#";
    private final String R_EVENTS = "#events#";
    private final String R_COLUMNS = "#columns#";
    private final String R_TABLE= "#table#";
    private final String R_STATEMENT = "#statement#";
    private final String R_ERROR = "#error#";
    private final String R_VARIABLES = "#variables#";

    private final String R_GVAR = "#gvar#";
    private final String R_BSTATEMENT = "#bstatement#";
    private final String R_FILLCHANGETAB = "#changetab#";
    private final String R_FILLOLDTAB = "#oldtab#";

    private final String BETWEEN = "%s BETWEEN '%s' AND '%s'";
    private final String COMPARISON = ":NEW.%s %s %s";
    private final String ANY_ALL_BLOCK = "    DECLARE v_test varchar2(6);\n" +
            "    BEGIN SELECT 'passed' INTO v_test FROM dual WHERE %s %s %s (%s); l_passed := TRUE;\n" +
            "    EXCEPTION WHEN NO_DATA_FOUND THEN l_passed := FALSE; END";

    private String trigger =
            "CREATE OR REPLACE TRIGGER #name#\n" +
            "    BEFORE #events#\n" +
            "    #columns# ON #table#\n" +
            "    FOR EACH ROW \n" +
            "DECLARE\n" +
            "    l_passed boolean := true;\n" +
            "#variables#\n"+
            "BEGIN\n" +
            "#statement#\n" +
            "    IF NOT l_passed THEN\n" +
            "        raise_application_error(-20800, '#error#');\n" +
            "    END IF;\n" +
            "END #name#;";

    private String compoundtrigger =
            "CREATE OR REPLACE TRIGGER #name#\n" +
            "    FOR #events# \n" +
            "    #columns# ON #table#\n" +
            "      COMPOUND TRIGGER\n" +
            "      \n" +
            "  TYPE t_rcrd IS RECORD (\n" +
            "    l_rowid ROWID,\n" +
            "    l_row #table#%ROWTYPE);\n" +
            "\n" +
            "  TYPE t_change_tab IS TABLE OF t_rcrd;\n" +
            "  g_change_tab t_change_tab := t_change_tab();\n" +
            "  \n" +
            "  TYPE t_old_tab IS TABLE OF t_rcrd;\n" +
            "  g_old_tab t_old_tab := t_old_tab();\n" +
            "\n" +
            "#gvar#\n" +
            "\n" +
            "  BEFORE STATEMENT IS\n" +
            "  BEGIN\n" +
            "#bstatement#\n" +
            "  END BEFORE STATEMENT;\n" +
            "\n" +
            "  AFTER EACH ROW IS\n" +
            "  BEGIN\n" +
            "    g_change_tab.extend;\n" +
            "    g_change_tab(g_change_tab.last).l_rowid := :NEW.ROWID;\n" +
            "#changetab#\n" +
            "    IF UPDATING THEN\n" +
            "      g_old_tab.extend;\n" +
            "      g_old_tab(g_old_tab.last).l_rowid := :OLD.ROWID;\n" +
            "#oldtab#\n" +
            "    END IF;\n" +
            "  END AFTER EACH ROW;\n" +
            "  \n" +
            "  AFTER STATEMENT IS\n" +
            "  BEGIN\n" +
            "    DECLARE\n" +
            "      l_passed boolean := TRUE;\n" +
            "    BEGIN    \n" +
            "#statement#\n" +
            "      IF NOT l_passed THEN\n" +
            "        raise_application_error(-20800, '#error#');\n" +
            "      END IF;\n" +
            "    END;\n" +
            "  END AFTER STATEMENT;\n" +
            "END #name#;";

    private boolean useCompoundTrigger = false;
    private String statement = "    l_passed := ";

    /**
     * Starts a new trigger and adds the name to the trigger
     * @param name The name of the trigger
     */
    public TriggerBuilder(String name) {
        trigger = trigger.replace(R_NAME, name);
        compoundtrigger = compoundtrigger.replace(R_NAME, name);
    }

    /**
     * Sets the table of the trigger
     * @param table The name of the table
     * @return The triggerbuilder, Call setEvents next
     */
    public TriggerBuilder setTable(String table){
        trigger = trigger.replace(R_TABLE, table);
        compoundtrigger = compoundtrigger.replace(R_TABLE, table);
        return this;
    }

    /**
     * Sets the insert, update and/or delete events
     * @param type The ruletype containing the event information
     * @return The triggerbuilder
     */
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
        compoundtrigger = compoundtrigger.replace(R_EVENTS, events);
        return this;
    }

    /**
     * Sets the columns of the trigger
     * @param cols The column names
     * @return The triggerbuilder
     */
    public TriggerBuilder setColumns(String... cols){
        List<String> columns = new ArrayList<>(Arrays.asList(cols)).stream()
                .filter(Objects::nonNull)
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toCollection(ArrayList::new));

        String value = "OF ";
        for (int i = 0; i < columns.size(); i++) {
            value += columns.get(i) + (i + 1 < columns.size() ? "," : "");
        }
        trigger = trigger.replace(R_COLUMNS, value);
        compoundtrigger = compoundtrigger.replace(R_COLUMNS, value);
        return this;
    }

    /**
     * Sets the error message of the trigger
     * @param error The error message
     * @return The triggerbuilder
     */
    public TriggerBuilder setError(String error){
        if(error == null) error = "ERROR";
        trigger = trigger.replace(R_ERROR, error);
        compoundtrigger = compoundtrigger.replace(R_ERROR, error);
        return this;
    }

    /**
     * Sets the global variables of the trigger
     * @param gvariables The global variables to be defined in the trigger.
     * @return The triggerbuilder, call setBeforeStatement next.
     */
    public TriggerBuilder setGlobalVariables(String gvariables) {
        useCompoundTrigger = true;
        if(gvariables == null) gvariables = "";
        List<String> strings = new ArrayList<>(Arrays.asList(gvariables.trim().split("\\r\\n")))
                .stream()
                .map(str -> "  " + str)
                .collect(Collectors.toCollection(ArrayList::new));
        gvariables = String.join("\r\n", strings);
        compoundtrigger = compoundtrigger.replace(R_GVAR, gvariables);
        return this;
    }

    /**
     * Sets the SQL code to be executed in the 'Before Statement' part of the trigger.
     * @param beforeStatement The statement to place in the trigger
     * @return The triggerbuilder, call setAllTableColumns next.
     */
    public TriggerBuilder setBeforeStatement(String beforeStatement) {
        useCompoundTrigger = true;
        if(beforeStatement == null || beforeStatement.isEmpty())
            beforeStatement = "null;";
        List<String> strings = new ArrayList<>(Arrays.asList(beforeStatement.trim().split("\\r\\n")))
                .stream()
                .map(str -> "    " + str)
                .collect(Collectors.toCollection(ArrayList::new));
        beforeStatement = String.join("\r\n", strings);
        compoundtrigger = compoundtrigger.replace(R_BSTATEMENT, beforeStatement);
        return this;
    }

    /**
     * Sets the variables which are used to track changed rows.
     * @param allColumns All columns of the table where the trigger created on.
     * @return The triggerbuilder, call setAfterStatement next.
     */
    public TriggerBuilder setAllTableColumns(List<String> allColumns) {
        useCompoundTrigger = true;
        final String format = "g_%s_tab(g_%s_tab.last).l_row.%s := %s.%s;";

        List<String> change = new ArrayList<>(allColumns).stream()
                .map(col -> "    " + String.format(format, "change", "change", col, ":NEW", col))
                .collect(Collectors.toCollection(ArrayList::new));

        List<String> old = new ArrayList<>(allColumns).stream()
                .map(col -> "      " + String.format(format, "old", "old", col, ":OLD", col))
                .collect(Collectors.toCollection(ArrayList::new));

        compoundtrigger = compoundtrigger
                .replace(R_FILLCHANGETAB, String.join("\r\n", change))
                .replace(R_FILLOLDTAB, String.join("\r\n", old));
        return this;
    }

    /**
     * Sets the SQL code to be executed in the 'After Statement' part of the trigger.
     * @param afterStatement The statement to place in the trigger
     * @return The triggerbuilder, call setError next.
     */
    public TriggerBuilder setAfterStatement(String afterStatement) {
        useCompoundTrigger = true;
        List<String> strings = new ArrayList<>(Arrays.asList(afterStatement.trim().split("\\r\\n")))
                .stream()
                .map(str -> "      " + str)
                .collect(Collectors.toCollection(ArrayList::new));
        afterStatement = String.join("\r\n", strings);
        if(afterStatement.charAt(afterStatement.length() - 1) == ';') {
            afterStatement = afterStatement.substring(0, afterStatement.length() - 1);
        }

        this.statement = afterStatement;
        return this;
    }

    /**
     * Adds aw statement to the trigger
     * @param statement The user written code
     * @return The triggerbuilder
     */
    public TriggerBuilder addStatement(String statement) {
        this.statement += statement;
        return this;
    }

    /**
     * Adds a between condition to the trigger
     * @param column The column the condition needs to check
     * @param min The lowest value of the between condition
     * @param max The highest value of the between condition
     * @return The triggerbuilder
     */
    public TriggerBuilder addBetween(String column, String min, String max) {
        statement += String.format(BETWEEN, ":NEW." + column, min, max);
        return this;
    }

    /**
     * Adds a comparison operator to the trigger
     * @param column The column the condition needs to check
     * @param operator The comparison operator of the condition
     * @param value The value the condition checks the column against
     * @return The triggerbuilder
     */
    public TriggerBuilder addComparison(String column, ComparisonOperator operator, String value) {
        statement += String.format(COMPARISON, column, operator.getCode(), "'"+value+"'");
        return this;
    }

    /**
     * Adds a comparison operator to the trigger
     * @param pre The column the condition needs to check
     * @param operator The comparison operator of the condition
     * @param post The column the condition checks the column against
     * @return The triggerbuilder
     */
    public TriggerBuilder addComparisonColumns(String pre, ComparisonOperator operator, String post) {
        statement += String.format(COMPARISON, pre, operator.getCode(), ":NEW." + post);
        return this;
    }

    /**
     * Adds a logical operator or a comparison and a logical operator to the trigger
     * @param pre The column the condition needs to check
     * @param lOperator The logical operator of the condition
     * @param cOperator The comparison operator of the condition
     * @param post The list of values the condition checks the column against
     * @return The triggerbuilder
     */
    public TriggerBuilder addComparisons(String pre, LogicalOperator lOperator, ComparisonOperator cOperator, String post) {
        if(lOperator == LogicalOperator.Any || lOperator == LogicalOperator.All) {
            statement = String.format(ANY_ALL_BLOCK, pre, cOperator.getCode(), lOperator.getCode(), post);
        } else {
            statement += String.format(COMPARISON, pre, lOperator.getCode(), "("+post+")");
        }
        return this;
    }

    /**
     * Adds a table comparison to the trigger
     * @param table The table the trigger will be implemented
     * @param key The key of the secondary table
     * @param key2 The key of the primary table
     * @param column The column the condition checks the column against
     * @param column2 The column the condition needs to check
     * @param opr The comparison operator of the condition
     * @param isPrimary Check if the table is the primary table
     * @return The triggerbuilder
     */
    public TriggerBuilder addTableComp(String table, String key, String key2, String column, String column2, ComparisonOperator opr, boolean isPrimary) {
        return addTableComp(table, key, key2, column, column2, null, opr, isPrimary);
    }

    /**
     * Adds a table comparison to the trigger
     * @param primaryTable The table which holds the primary key.
     * @param key The primary key column.
     * @param key2 The foreign key column.
     * @param column The column to check against.
     * @param opr The operator to check with.
     * @param operand The value to check against.
     * @return The triggerbuilder
     */
    public TriggerBuilder addTableComp(String primaryTable, String key, String key2, String column, ComparisonOperator opr, String operand) {
        return addTableComp(primaryTable, key, key2, column, null, operand, opr, true);
    }

    private TriggerBuilder addTableComp(String table, String key, String key2, String column, String column2, String operand2, ComparisonOperator opr, boolean isPrimary) {
        String trigColumn = column;
        if(!isPrimary){
            opr = getOppositOpr(opr);
            if(opr == ComparisonOperator.LessOrEqual || opr == ComparisonOperator.Less){
                trigColumn = "MIN("+column+")";
            }else if(opr == ComparisonOperator.GreaterOrEqual || opr == ComparisonOperator.Greater){
                trigColumn = "MAX("+column+")";
            }
        }
        String vars = "    cursor v_cursor is\n" +
                    "    select "+trigColumn+"\n" +
                    "    from "+table+"\n" +
                    "    where "+key+" = :NEW."+key2+";\n" +
                    "    v_value "+table+"."+column+"%type;\n";
        trigger = trigger.replace(R_VARIABLES, vars);
        statement = "    open v_cursor;\n" +
                "    fetch v_cursor into v_value;\n" +
                "    close v_cursor;\n";

        if(column2 != null) {
            statement += "    l_passed := :NEW."+column2+" "+opr.getCode()+" v_value";
        } else {
            statement += "    l_passed := v_value " + opr.getCode() + " '" + operand2 + "'";
        }

        if(!isPrimary && (opr == ComparisonOperator.Equal || opr == ComparisonOperator.NotEqual)) {
            statement =
                    "    FOR rec IN v_cursor\n" +
                    "    LOOP\n" +
                    "      l_passed := :NEW."+column2+" "+opr.getCode()+" rec." + column + ";\n" +
                    "    EXIT WHEN NOT l_passed;\n" +
                    "    END LOOP";
        }
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

    /**
     * Removes al unused keys from the trigger and builds the values into a string
     * @return The query string
     */
    public String build() {
        for (String tag : Arrays.asList(R_NAME, R_EVENTS, R_COLUMNS, R_TABLE, R_ERROR, R_GVAR, R_BSTATEMENT, R_FILLCHANGETAB, R_FILLOLDTAB, R_VARIABLES)) {
            trigger = trigger.replace(tag, "");
            compoundtrigger = compoundtrigger.replace(tag, "");
        }

        trigger = trigger.replace(R_STATEMENT, statement + ";");
        compoundtrigger = compoundtrigger.replace(R_STATEMENT, statement + ";");

        return useCompoundTrigger ? compoundtrigger : trigger;
    }
}
