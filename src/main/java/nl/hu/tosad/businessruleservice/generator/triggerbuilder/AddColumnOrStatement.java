package nl.hu.tosad.businessruleservice.generator.triggerbuilder;

public interface AddColumnOrStatement {
    AddAttributes addColumn(String column);
    Build addStatement(String statement);
}
