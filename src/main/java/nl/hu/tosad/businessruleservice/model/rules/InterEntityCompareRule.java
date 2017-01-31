package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

import java.util.Objects;

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
        try {
            table2 = Objects.requireNonNull(data.getTargettable2(), "InterEntityCompareRule Table2 cannot be null.");
            column = Objects.requireNonNull(data.getTargetcolumn(), "InterEntityCompareRule Column cannot be null.");
            column2 = Objects.requireNonNull(data.getTargetcolumn2(), "InterEntityCompareRule Column2 cannot be null.");
            operator = ComparisonOperator.valueOf(data.getC_operator());
        } catch(NullPointerException | IllegalArgumentException e) {
            throw new BusinessRuleServiceException(e);
        }
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
