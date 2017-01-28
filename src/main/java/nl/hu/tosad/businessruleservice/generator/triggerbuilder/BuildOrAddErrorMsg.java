package nl.hu.tosad.businessruleservice.generator.triggerbuilder;

public interface BuildOrAddErrorMsg {
    Build addErrorMessage(String msg);
    String build();
}
