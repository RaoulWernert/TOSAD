package nl.hu.tosad.businessruleservice.generator.triggerbuilder;

public interface AddValueOrColumn {
    BuildOrAddErrorMsg addValue(String value);
    BuildOrAddErrorMsg addSecondColumn(String column);
}
