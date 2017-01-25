package nl.hu.tosad.businessruleservice.generator.triggerbuilder;

public interface AddEvent {
    OnColumn addEvent(String table, String... columns);
}
