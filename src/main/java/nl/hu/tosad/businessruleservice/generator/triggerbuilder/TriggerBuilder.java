package nl.hu.tosad.businessruleservice.generator.triggerbuilder;

public class TriggerBuilder {
    private String trigger =
            "CREATE OR REPLACE TRIGGER %s\n" +
            "    BEFORE %s (ON table | OF column ON table)\n" +
            "    FOR EACH ROW \n" +
            "BEGIN\n" +
            "    code\n" +
            "END";

    private String event;

    public TriggerBuilder newTrigger(String name) {
        trigger = String.format(trigger, name, "%s");
        return this;
    }

    public TriggerBuilder onEvent(boolean insert, boolean update, boolean delete) {
        String evnt = "";

        if(insert) {
            evnt += "INSERT";
        }
        if(update) {
            evnt += (!evnt.isEmpty() ? " OR " : "") + "UPDATE";
        }
        if(delete) {
            evnt += (!evnt.isEmpty() ? " OR " : "") + "DELETE";
        }

        trigger = String.format(trigger, evnt);
        return this;
    }






}
