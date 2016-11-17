package nl.hu.tosad.rules;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeRangeRule extends AttributeRule{
    private String min;
    private String max;

    public AttributeRangeRule(String tab, String atr, String mi, String ma){
        super(tab, atr);
        min = mi;
        max = ma;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }
}