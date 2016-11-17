package nl.hu.tosad.rules;

/**
 * Created by Raoul on 11/17/2016.
 */
public class AttributeRule extends BusinessRule{
    protected String attribute;

    public AttributeRule(String tab, String atr){
        super(tab);
        attribute = atr;
    }
    public String getAttribute(){
        return attribute;
    }
}