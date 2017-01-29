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
        table2 = data.getTargettable2();
        column = data.getTargetcolumn();
        column2 = data.getTargetcolumn2();
        operator = ComparisonOperator.valueOf(data.getC_operator());
    }

    @Override
    public String accept(IGenerator generator) {
        return generator.generateDDL(this);
    }

    @Override
    public String getName() {
        return super.getName() + "_1";
    }

    @Override
    public String getSecondName() {
        return super.getSecondName() + "_2";
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
