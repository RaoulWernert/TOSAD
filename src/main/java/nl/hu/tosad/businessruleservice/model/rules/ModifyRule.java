package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

import java.util.Objects;

/**
 * Created by Raoul on 1/26/2017.
 */
public class ModifyRule extends BusinessRule {
    private String table2;
    private String column;
    private ComparisonOperator operator;
    private String value;

    public ModifyRule(BusinessRuleData data) {
        super(data);
        try {
            table2 = Objects.requireNonNull(data.getTargettable2());
            column = Objects.requireNonNull(data.getTargetcolumn());
            operator = ComparisonOperator.valueOf(data.getC_operator());
            value = Objects.requireNonNull(data.getValue());
        } catch (NullPointerException | IllegalArgumentException e) {
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

    public ComparisonOperator getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }
}
