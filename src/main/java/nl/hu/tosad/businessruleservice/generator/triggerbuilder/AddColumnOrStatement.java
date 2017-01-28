package nl.hu.tosad.businessruleservice.generator.triggerbuilder;

public interface AddColumnOrStatement {
    AddAttributes addColumn(String column);
    BuildOrAddErrorMsg addStatement(String statement);
    AddAllColumns addCodeBlock(String code);
}
