package nl.hu.tosad;

import nl.hu.tosad.model.BusinessRuleData;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        RuleFactory factory = new RuleFactory();
        //haal br op
        String type = "ARNG";
        BusinessRuleData data = new BusinessRuleData("", "", "", "", "", "", "", "", "");
        factory.createRule(data);
    }
}
