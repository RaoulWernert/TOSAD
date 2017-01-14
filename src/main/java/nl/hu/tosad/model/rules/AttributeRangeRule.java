package nl.hu.tosad.model.rules;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeRangeRule extends AttributeRule{
    private String min;
    private String max;

    public AttributeRangeRule(String name, String table, String attribute, String min, String max){
        super(name, table, attribute);
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
    public String accept(ISQLGenerator generator) {
        return generator.generateSQL(this);
    }
}