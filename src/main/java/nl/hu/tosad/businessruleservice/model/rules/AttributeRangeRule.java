package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

import java.util.Objects;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeRangeRule extends AttributeRule{
    private String min;
    private String max;

    public AttributeRangeRule(BusinessRuleData data) {
        super(data);
        try {
            min = Objects.requireNonNull(data.getMin());
            max = Objects.requireNonNull(data.getMax());
        } catch(NullPointerException e) {
            throw new BusinessRuleServiceException(e);
        }
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }

    @Override
    public String accept(IGenerator generator) {
        return generator.generateDDL(this);
    }
}