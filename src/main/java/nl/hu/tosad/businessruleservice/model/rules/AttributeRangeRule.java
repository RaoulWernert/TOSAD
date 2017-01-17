package nl.hu.tosad.businessruleservice.model.rules;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeRangeRule extends AttributeRule{
    private String min;
    private String max;

    AttributeRangeRule(String name, String table, Implementation implementation, String attribute, String min, String max){
        super(name, table, implementation, attribute);
        this.min = min;
        this.max = max;
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