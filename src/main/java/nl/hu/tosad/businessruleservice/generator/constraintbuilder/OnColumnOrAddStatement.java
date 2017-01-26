package nl.hu.tosad.businessruleservice.generator.constraintbuilder;

public interface OnColumnOrAddStatement {
    AddAttributes onColumn(String column);
    AddOperator onColumns(String column1, String column2);
    Build addStatement(String statement);
}
