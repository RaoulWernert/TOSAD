package nl.hu.tosad.businessruleservice.generator.triggerbuilder;

public interface AddEvent {
    AddColumnOrStatement addEvent(String table, String... columns);
}
