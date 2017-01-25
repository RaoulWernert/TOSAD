package nl.hu.tosad.businessruleservice.generator.triggerbuilder;

import nl.hu.tosad.businessruleservice.model.RuleType;

public class TriggerBuilder {
    private String trigger =
            "CREATE OR REPLACE TRIGGER %s\n" +
            "    BEFORE %s %s\n" +
            "    FOR EACH ROW \n" +
            "BEGIN\n" +
            "    null;\n" +
            "END";

    public TriggerBuilder newTrigger(String name) {
        trigger = String.format(trigger, name, "%s", "%s");
        return this;
    }

    public TriggerBuilder onRuleType(RuleType ruleType) {
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

        trigger = String.format(trigger, event, "%s");
        return this;
    }

    public TriggerBuilder addEvent(String table, String... columns) {
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

        trigger = String.format(trigger, event);
        return this;
    }

    public String build() {
        return trigger;
    }
}
