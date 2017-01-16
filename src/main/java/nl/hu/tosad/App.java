package nl.hu.tosad;

import nl.hu.tosad.controller.BusinessRuleService;

/**
 * Hello world!
 *
 */
public class App 
{
    private static BusinessRuleService businessRuleService = BusinessRuleService.getInstance();

    public static void main( String[] args )
    {
        businessRuleService.generate(1);
        businessRuleService.generate(2);
        businessRuleService.generate(3);
        businessRuleService.generate(4);
    }
}
