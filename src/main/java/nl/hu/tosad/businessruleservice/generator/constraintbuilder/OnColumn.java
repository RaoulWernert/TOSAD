package nl.hu.tosad.businessruleservice.generator.constraintbuilder;

public interface OnColumn {
    AddAttributes onColumn(String column);
    AddOperator onColumns(String column1, String column2);
}
