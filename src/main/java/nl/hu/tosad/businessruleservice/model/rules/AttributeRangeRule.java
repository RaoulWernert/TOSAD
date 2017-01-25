package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeRangeRule extends AttributeRule{
    private String min;
    private String max;

    public AttributeRangeRule(BusinessRuleData data) {
        super(data);
        min = data.getMin();
        max = data.getMax();
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