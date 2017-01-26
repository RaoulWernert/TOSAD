package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

/**
 * Created by Raoul on 1/26/2017.
 */
public class InterEntityCompareRule extends BusinessRule {
    private String table2;
    private String column;
    private String column2;
    private ComparisonOperator operator;

    public InterEntityCompareRule(BusinessRuleData data) {
        super(data);
        table2 = data.getTable2();
        column = data.getColumn();
        column2 = data.getColumn2();
        operator = ComparisonOperator.valueOf(data.getC_operator());
    }

    @Override
    public String accept(IGenerator generator) {
        return generator.generateDDL(this);
    }

    public String getTable2() {
        return table2;
    }

    public String getColumn() {
        return column;
    }

    public String getColumn2() {
        return column2;
    }

    public ComparisonOperator getOperator() {
        return operator;
    }
}
